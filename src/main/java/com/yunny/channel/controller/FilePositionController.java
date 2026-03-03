package com.yunny.channel.controller;

import com.yunny.channel.common.dto.FilePositionDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.FilePositionVO;
import com.yunny.channel.common.query.FilePositionQuery;
import com.yunny.channel.service.FilePositionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/filePosition")
public class FilePositionController {

    @Resource
    private FilePositionService filePositionService;


    /**
     * 查询档案室位置集合  redis 缓存策略，保质期1天
     * @param query
     * @return
     */
    @RequestMapping("/listByQuery")
    public BaseResult<List<FilePositionVO>> listByQuery(@RequestBody FilePositionQuery query) {
        List<FilePositionVO> positionVOList = filePositionService.listByQuery(query);
        return BaseResult.success(positionVOList);
    }


}
