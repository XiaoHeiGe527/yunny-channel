package com.yunny.channel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.entity.DictionaryDO;

import com.yunny.channel.common.dto.DictionaryDTO;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.vo.DictionaryVO;
import com.yunny.channel.common.query.DictionaryQuery;

import com.yunny.channel.mapper.DictionaryMapper;
import com.yunny.channel.service.DictionaryService;
import com.yunny.channel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.concurrent.TimeUnit;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;


    @Autowired
    RedisService redisService;

    // 用于 JSON 序列化和反序列化的对象
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<DictionaryVO> listByPage(DictionaryQuery dictionaryQuery) {
        PageParameter pageParameter = dictionaryQuery.getPageParameter();
        return new CommonPager<DictionaryVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        dictionaryMapper.countByQuery(dictionaryQuery)),
                dictionaryMapper.listByQuery(dictionaryQuery).stream()
                        .map(item -> {
                            DictionaryVO dictionaryVo = new DictionaryVO();
                            BeanUtils.copyProperties(item, dictionaryVo);
                            return dictionaryVo;
                        }).collect(Collectors.toList()));
    }



    @Override
    public List<DictionaryVO> listByQuery(DictionaryQuery query) {
        if (query.getCategory().equals("岗位")) {
            String redisKey = RedisKeyNameConstants.EMPLOYEE_PROFESSION_LIST;

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
                        if(StringUtil.isEmpty(query.getExplain())){
                            return professionSet.stream()
                                    .map(content -> {
                                        DictionaryVO vo = new DictionaryVO();
                                        vo.setContent(content);
                                        return vo;
                                    })
                                    .collect(Collectors.toList());


                        }else {

                            // 使用 Stream API professionSet，存储到新的集合中，同样使用 LinkedHashSet 保持顺序
                            Set<String> matchNameSet = professionSet.stream()
                                    .filter(explain -> explain.contains(query.getExplain()))
                                    .collect(Collectors.toCollection(java.util.LinkedHashSet::new));


                            return matchNameSet.stream()
                                    .map(content -> {
                                        DictionaryVO vo = new DictionaryVO();
                                        vo.setContent(content);
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
            List<DictionaryDO> doList = dictionaryMapper.listByQuery(query);
            List<DictionaryVO> voList = doList.stream()
                    .map(this::convertToVO)
                    .collect(Collectors.toList());

            // 缓存到 Redis（无需额外设置存在标识）
            cacheProfessionData(voList);

            return voList;
        } else {
            // 非岗位数据直接从数据库获取
            List<DictionaryDO> doList = dictionaryMapper.listByQuery(query);
            return doList.stream()
                    .map(this::convertToVO)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 将岗位数据缓存到Redis
     */
    private void cacheProfessionData(List<DictionaryVO> voList) {
        try {
            Set<String> professionSet = voList.stream()
                    .map(DictionaryVO::getContent)
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

    private DictionaryVO convertToVO(DictionaryDO professionDO) {
        return DictionaryVO.builder()
                .id(professionDO.getId())
                .category(professionDO.getCategory())
                .codeNum(professionDO.getCodeNum())
                .content(professionDO.getContent())
                .remarks(professionDO.getRemarks())
                .build();
    }
    /**
    * 通过id获取
    * @return
    */
    @Override
    public DictionaryVO getById(Long id) {
    	DictionaryDO dictionaryDo = dictionaryMapper.getById(id);
    	if(null == dictionaryDo){
    		return null;
    	}
    	DictionaryVO dictionaryVo = new DictionaryVO();
    	BeanUtils.copyProperties(dictionaryDo, dictionaryVo);
    	return dictionaryVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(DictionaryDTO dictionaryDto) {

     Long count = dictionaryMapper.countByQuery(DictionaryQuery.builder()
        .category(dictionaryDto.getCategory())
        .codeNum(dictionaryDto.getCodeNum()).build());
        if(count>0){
            throw new ServiceException("该员类别的字典编码已存在，不可以" + (dictionaryDto.getCodeNum() == null ? "添加" : "修改"));
        }

        DictionaryDO dictionaryDo = new DictionaryDO();
        BeanUtils.copyProperties(dictionaryDto, dictionaryDo);
        return dictionaryMapper.insertSelective(dictionaryDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(DictionaryDTO dictionaryDto) {

        Long count = dictionaryMapper.countByQuery(DictionaryQuery.builder()
                .notId(dictionaryDto.getId())
                .category(dictionaryDto.getCategory())
                .codeNum(dictionaryDto.getCodeNum()).build());
        if(count>0){
            throw new ServiceException("该员类别的字典编码已存在，不可以" + (dictionaryDto.getCodeNum() == null ? "添加" : "修改"));
        }

        DictionaryDO dictionaryDo = new DictionaryDO();
        BeanUtils.copyProperties(dictionaryDto, dictionaryDo);
        return dictionaryMapper.updateSelective(dictionaryDo);
    }

}
