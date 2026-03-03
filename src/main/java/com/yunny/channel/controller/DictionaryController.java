package com.yunny.channel.controller;

import com.yunny.channel.common.dto.DictionaryDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.DictionaryVO;
import com.yunny.channel.common.query.DictionaryQuery;
import com.yunny.channel.service.DictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;


    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<DictionaryVO>> listByPage(@RequestBody DictionaryQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
        CommonPager<DictionaryVO> commonPager = dictionaryService.listByPage(query);
        return BaseResult.success(commonPager);
    }


    /**
     * 查询字典 后期使用redis 缓存策略
     * @param query
     * @return
     */
    @RequestMapping("/listByQuery")
    public BaseResult<List<DictionaryVO>> listByQuery(@RequestBody  @Validated({SearchGroup.class}) DictionaryQuery query) {
        List<DictionaryVO> positionVOList = dictionaryService.listByQuery(query);
        return BaseResult.success(positionVOList);
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) DictionaryDTO dictionaryDto) {
        dictionaryService.insertSelective(dictionaryDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) DictionaryDTO dictionaryDto) {
        dictionaryService.updateSelective(dictionaryDto);
        return BaseResult.success();
    }


    @PostMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(dictionaryService.getById(id));
    }
}
