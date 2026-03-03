package com.yunny.channel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yunny.channel.common.dto.MaterialPlanDetailDTO;
import com.yunny.channel.common.dto.MaterialPlanMainDTO;
import com.yunny.channel.common.dto.MaterialPlanSubmitDTO;
import com.yunny.channel.common.entity.*;

import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.ApprovalFlowConfigQuery;
import com.yunny.channel.common.query.ApprovalNodeConfigQuery;

import com.yunny.channel.common.query.MaterialPlanMainQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.MaterialPlanDetailVO;
import com.yunny.channel.common.vo.MaterialPlanMainVO;
import com.yunny.channel.mapper.*;
import com.yunny.channel.service.MaterialPlanMainService;
import com.yunny.channel.util.OrderInfoUtil;
import com.yunny.channel.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class MaterialPlanMainServiceImpl implements MaterialPlanMainService {



    @Autowired
    OrderInfoUtil orderInfoUtil;

    @Resource
    private MaterialPlanMainMapper materialPlanMainMapper;

    @Resource
    private MaterialPlanDetailMapper detailMapper;
    @Resource
    private ApprovalFlowConfigMapper flowMapper;
    @Resource
    private ApprovalNodeConfigMapper nodeMapper;
    @Resource
    private MaterialPlanApprovalRecordMapper recordMapper;
    @Resource
    private SequenceGenerator sequenceGenerator; // 单号生成工具类


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResult submitPlan(MaterialPlanSubmitDTO submitDTO, String applicantNo) {
        // 1. 参数校验
        if (CollectionUtils.isEmpty(submitDTO.getDetailList())) {
            log.error("提交失败：明细列表为空，申请人：{}", applicantNo);
            return BaseResult.failure(500, "明细列表不能为空");
        }

        // 2. 生成计划单号（如WHJH20251101001）
        String planNo;
        try {
            planNo =  orderInfoUtil.getPlanNo();
        } catch (Exception e) {
            log.error("生成单号失败：{}", e.getMessage());
            return BaseResult.failure(500, "生成计划编号失败，请重试");
        }
        log.info("生成计划单号：{}，申请人：{}", planNo, applicantNo);

        // 3. 保存主表
        MaterialPlanMainDO mainDO = new MaterialPlanMainDO();
        BeanUtils.copyProperties(submitDTO, mainDO);
        mainDO.setPlanNo(planNo);
        mainDO.setApplicantNo(applicantNo);
        mainDO.setPlanStatus(1); // 1-待审批
        mainDO.setCreateTime(LocalDateTime.now());
        mainDO.setUpdateTime(LocalDateTime.now());
        int mainRows = materialPlanMainMapper.insertSelective(mainDO);
        if (mainRows != 1) {
            return BaseResult.failure(500, "保存计划主表失败");
        }

        // 4. 保存明细（铅笔2盒、胶棒5个、剪刀1个）
        LocalDateTime now = LocalDateTime.now();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (MaterialPlanDetailDTO dto : submitDTO.getDetailList()) {
            // 明细校验
            if (dto.getMaterialName() == null || dto.getMaterialName().trim().isEmpty()) {
                return BaseResult.failure(500, "存在未填写名称的物资明细");
            }
            if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
                return BaseResult.failure(500, "物资【" + dto.getMaterialName() + "】数量必须大于0");
            }
            if (dto.getUnit() == null || dto.getUnit().trim().isEmpty()) {
                return BaseResult.failure(500, "物资【" + dto.getMaterialName() + "】未填写单位");
            }

            // 转换并保存明细
            MaterialPlanDetailDO detailDO = new MaterialPlanDetailDO();
            BeanUtils.copyProperties(dto, detailDO);
            detailDO.setPlanNo(planNo);
            detailDO.setCreateTime(now);
            detailDO.setUpdateTime(now);



            if (dto.getUnitPrice() != null) {
                // 明细金额 = 单价 × 数量
                BigDecimal detailAmount = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
                totalAmount = totalAmount.add(detailAmount); // 累加总金额
            }


            // 计算金额（修复BigDecimal与Integer相乘问题）
            // 原金额计算逻辑调整：只在 unitPrice 不为 null 时计算
            if (dto.getUnitPrice() != null) {
                detailDO.setAmount(dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));

            } else {
                detailDO.setAmount(new BigDecimal("0")); // 不填单价，金额为0
            }





            int detailRows = detailMapper.insertSelective(detailDO);
            if (detailRows != 1) {
                return BaseResult.failure(500, "保存【" + dto.getMaterialName() + "】明细失败");
            }
        }



        // 给主表总金额赋值
        mainDO.setTotalAmount(totalAmount); // 如果所有明细都没有单价，totalAmount 为 0

        // 5. 查询审批流程（按计划类型）
        ApprovalFlowConfigQuery flowQuery = new ApprovalFlowConfigQuery();
        flowQuery.setPlanType(submitDTO.getPlanType());
        flowQuery.setFlowStatus(1); // 启用状态
        List<ApprovalFlowConfigDO> flowList = flowMapper.listByQuery(flowQuery);
        if (CollectionUtils.isEmpty(flowList)) {
            return BaseResult.failure(500, "未配置【" + submitDTO.getPlanType() + "】的审批流程");
        }
        ApprovalFlowConfigDO flowDO = flowList.get(0);

        // 6. 查询审批节点（使用修复后的nodeMapper，返回正确的节点类型）
        ApprovalNodeConfigQuery nodeQuery = new ApprovalNodeConfigQuery();
        nodeQuery.setFlowId(flowDO.getFlowId());
        nodeQuery.setNodeStatus(1); // 启用状态
        List<ApprovalNodeConfigDO> nodeList = nodeMapper.listByQuery(nodeQuery); // 修复后：返回List<ApprovalNodeConfigDO>
        if (CollectionUtils.isEmpty(nodeList)) {
            return BaseResult.failure(500, "审批流程未配置节点，请联系管理员");
        }

        // 7. 初始化审批记录
        for (ApprovalNodeConfigDO node : nodeList) {
            MaterialPlanApprovalRecordDO record = new MaterialPlanApprovalRecordDO();
            record.setPlanNo(planNo);
            record.setNodeId(node.getNodeId());
            record.setApproverNo(node.getApproverNo());
            record.setCreateTime(now);

            // 申请人为当前节点审批人时自动跳过
            if (applicantNo.equals(node.getApproverNo())) {
                record.setSkipFlag(1);
                record.setSkipReason("申请人为当前节点审批人，自动跳过");
                record.setApprovalResult(2); // 2-已跳过
            } else {
                record.setSkipFlag(0);
                record.setApprovalResult(0); // 0-待审批
            }

            int recordRows = recordMapper.insertSelective(record);
            if (recordRows != 1) {
                return BaseResult.failure(500, "初始化【" + node.getNodeName() + "】审批记录失败");
            }
        }

        // 8. 提交成功
        return BaseResult.success("计划提交成功，单号：" + planNo);
    }

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<MaterialPlanMainVO> listByPage(MaterialPlanMainQuery query) {
        // 1. 原有分页逻辑：查主表数据和总条数
        PageParameter pageParameter = query.getPageParameter();
        Long totalCount = materialPlanMainMapper.countByQuery(query);
        List<MaterialPlanMainDO> mainList = materialPlanMainMapper.listByQuery(query);

        // 2. 批量查询明细（如果主表有数据）
        // 1. 初始化空 map（引用不变）
        Map<String, List<MaterialPlanDetailVO>> detailMap = new HashMap<>();

        if (!mainList.isEmpty()) {
            // 提取所有计划单号
            List<String> planNos = mainList.stream()
                    .map(MaterialPlanMainDO::getPlanNo)
                    .collect(Collectors.toList());

            // 查明细并转换为 VO
            List<MaterialPlanDetailDO> detailDOList = detailMapper.listByPlanNos(planNos);
            if (!detailDOList.isEmpty()) {
                // 2. 用临时 map 接收转换结果
                Map<String, List<MaterialPlanDetailVO>> tempMap = detailDOList.stream()
                        .map(detailDO -> {
                            MaterialPlanDetailVO detailVO = new MaterialPlanDetailVO();
                            BeanUtils.copyProperties(detailDO, detailVO);
                            return detailVO;
                        })
                        .collect(Collectors.groupingBy(MaterialPlanDetailVO::getPlanNo));

                // 3. 合并到 detailMap（不修改原引用）
                detailMap.putAll(tempMap);
            }
        }

// 后续 lambda 中使用 detailMap 时，它是 effectively final 的
        List<MaterialPlanMainVO> voList = mainList.stream()
                .map(mainDO -> {
                    MaterialPlanMainVO vo = new MaterialPlanMainVO();
                    BeanUtils.copyProperties(mainDO, vo);
                    // 这里使用 detailMap 不会报错了
                    vo.setDetailList(detailMap.getOrDefault(mainDO.getPlanNo(), Collections.emptyList()));
                    return vo;
                })
                .collect(Collectors.toList());

        // 4. 原有分页封装
        return new CommonPager<>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(), totalCount),
                voList
        );
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public MaterialPlanMainVO getById(Long id) {
    	MaterialPlanMainDO materialPlanMainDo = materialPlanMainMapper.getById(id);
    	if(null == materialPlanMainDo){
    		return null;
    	}
    	MaterialPlanMainVO materialPlanMainVo = new MaterialPlanMainVO();
    	BeanUtils.copyProperties(materialPlanMainDo, materialPlanMainVo);
    	return materialPlanMainVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(MaterialPlanMainDTO materialPlanMainDto) {
        MaterialPlanMainDO materialPlanMainDo = new MaterialPlanMainDO();
        BeanUtils.copyProperties(materialPlanMainDto, materialPlanMainDo);
        return materialPlanMainMapper.insertSelective(materialPlanMainDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(MaterialPlanMainDTO materialPlanMainDto) {
        MaterialPlanMainDO materialPlanMainDo = new MaterialPlanMainDO();
        BeanUtils.copyProperties(materialPlanMainDto, materialPlanMainDo);
        return materialPlanMainMapper.updateSelective(materialPlanMainDo);
    }

}
