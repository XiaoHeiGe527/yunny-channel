package com.yunny.channel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.entity.FilePositionDO;

import com.yunny.channel.common.dto.FilePositionDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.vo.FilePositionVO;
import com.yunny.channel.common.query.FilePositionQuery;
import com.yunny.channel.mapper.FilePositionMapper;
import com.yunny.channel.service.FilePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class FilePositionServiceImpl implements FilePositionService {

    @Resource
    private FilePositionMapper filePositionMapper;

    @Autowired
    RedisService redisService;

    // 用于 JSON 序列化和反序列化的对象
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<FilePositionVO> listByPage(FilePositionQuery filePositionQuery) {
        PageParameter pageParameter = filePositionQuery.getPageParameter();
        return new CommonPager<FilePositionVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        filePositionMapper.countByQuery(filePositionQuery)),
                filePositionMapper.listByQuery(filePositionQuery).stream()
                        .map(item -> {
                            FilePositionVO filePositionVo = new FilePositionVO();
                            BeanUtils.copyProperties(item, filePositionVo);
                            return filePositionVo;
                        }).collect(Collectors.toList()));
    }

     @Override
    public List<FilePositionVO> listByQuery(FilePositionQuery query) {
         List<FilePositionDO> doList = filePositionMapper.listByQuery(query);
         List<FilePositionVO> voList = doList.stream().map(this::convertToVO) .collect(Collectors.toList());
        return voList;
    }

    private FilePositionVO convertToVO(FilePositionDO filePositionDO) {
        return FilePositionVO.builder()
                .id(filePositionDO.getId())
                .positionCode(filePositionDO.getPositionCode())
                .fileType(filePositionDO.getFileType())
                .region(filePositionDO.getRegion())
                .cabinetNum(filePositionDO.getCabinetNum())
                .cabinetLevel(filePositionDO.getCabinetLevel())
                .remarks(filePositionDO.getRemarks())
                .createTime(filePositionDO.getCreateTime())
                .updateTime(filePositionDO.getUpdateTime())
                .build();
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public FilePositionVO getById(Long id) {
    	FilePositionDO filePositionDo = filePositionMapper.getById(id);
    	if(null == filePositionDo){
    		return null;
    	}
    	FilePositionVO filePositionVo = new FilePositionVO();
    	BeanUtils.copyProperties(filePositionDo, filePositionVo);
    	return filePositionVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(FilePositionDTO filePositionDto) {
        FilePositionDO filePositionDo = new FilePositionDO();
        BeanUtils.copyProperties(filePositionDto, filePositionDo);
        return filePositionMapper.insertSelective(filePositionDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(FilePositionDTO filePositionDto) {
        FilePositionDO filePositionDo = new FilePositionDO();
        BeanUtils.copyProperties(filePositionDto, filePositionDo);
        return filePositionMapper.updateSelective(filePositionDo);
    }

}
