package com.yunny.channel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.entity.ApprovalFlowConfigDO;
import com.yunny.channel.common.query.ApprovalFlowConfigQuery;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.StringUtil;
import com.yunny.channel.common.vo.ApprovalFlowConfigVO;
import com.yunny.channel.mapper.ApprovalFlowConfigMapper;
import com.yunny.channel.service.ApprovalFlowConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName ApprovalFlowConfigServiceImpl
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/1 16:26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class ApprovalFlowConfigServiceImpl implements ApprovalFlowConfigService {

    // 用于 JSON 序列化和反序列化的对象
    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    ApprovalFlowConfigMapper approvalFlowConfigMapper;


    @Autowired
    RedisService redisService;

    @Override
    public List<ApprovalFlowConfigVO> listByQuery(ApprovalFlowConfigQuery query) {
            String redisKey = RedisKeyNameConstants.PLAN_TYPE;
            // 直接检查 Redis 键是否存在
            if (redisService.exists("", redisKey)) { // 假设 prefix 为空，根据实际调整
                String professionSetJson = redisService.getStr("", redisKey); // 使用 getStr 获取数据
                if (professionSetJson != null) {
                    try {
                        Set<String> professionSet = objectMapper.readValue(
                                professionSetJson,
                                objectMapper.getTypeFactory().constructCollectionType(LinkedHashSet.class, String.class)
                        );

                        //如果查询条件是空的就是查所有，否则就用Stream API把匹配的筛选出来
                        if(StringUtil.isEmpty(query.getPlanType())){
                            return professionSet.stream()
                                    .map(content -> {
                                        ApprovalFlowConfigVO vo = new ApprovalFlowConfigVO();
                                        vo.setPlanType(content);
                                        return vo;
                                    })
                                    .collect(Collectors.toList());

                        }else {

                            // 使用 Stream API professionSet，存储到新的集合中，同样使用 LinkedHashSet 保持顺序
                            Set<String> matchNameSet = professionSet.stream()
                                    .filter(explain -> explain.contains(query.getPlanType()))
                                    .collect(Collectors.toCollection(java.util.LinkedHashSet::new));


                            return matchNameSet.stream()
                                    .map(content -> {
                                        ApprovalFlowConfigVO vo = new ApprovalFlowConfigVO();
                                        vo.setPlanType(content);
                                        return vo;
                                    })
                                    .collect(Collectors.toList());

                        }

                    } catch (IOException e) {
                        log.error("解析岗位数据JSON失败，将从数据库重新获取", e);
                    }
                }
            }
            // 从数据库获取数据
            List<ApprovalFlowConfigDO> doList = approvalFlowConfigMapper.listByQuery(query);
            List<ApprovalFlowConfigVO> voList = doList.stream()
                    .map(this::convertToVO)
                    .collect(Collectors.toList());

            // 缓存到 Redis（无需额外设置存在标识）
            cacheProfessionData(voList);
            return voList;

}

    /**
     * 将岗位数据缓存到Redis
     */
    private void cacheProfessionData(List<ApprovalFlowConfigVO> voList) {
        try {
            Set<String> professionSet = voList.stream()
                    .map(ApprovalFlowConfigVO::getPlanType)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            String jsonData = objectMapper.writeValueAsString(professionSet);

            // 直接使用 setStr 存储数据并设置过期时间
            redisService.setStr(
                    "", // prefix（根据实际需求调整）
                    RedisKeyNameConstants.EMPLOYEE_PROFESSION_LIST,
                    jsonData,
                    24 * 3600 // 24小时（秒）
            );
            // 无需调用 hashPut 设置存在标识，键存在即表示有数据
        } catch (JsonProcessingException e) {
            log.error("缓存岗位数据失败", e);
        }
    }


    private ApprovalFlowConfigVO convertToVO(ApprovalFlowConfigDO approvalFlowConfigDO) {
        return ApprovalFlowConfigVO.builder()
                .flowId(approvalFlowConfigDO.getFlowId())
                .flowName(approvalFlowConfigDO.getFlowName())
                .planType(approvalFlowConfigDO.getPlanType())
                .flowDesc(approvalFlowConfigDO.getFlowDesc())
                .flowStatus(approvalFlowConfigDO.getFlowStatus())
                .createTime(approvalFlowConfigDO.getCreateTime())
                .build();
    }

}
