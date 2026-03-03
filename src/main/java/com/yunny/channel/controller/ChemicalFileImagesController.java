package com.yunny.channel.controller;


import com.yunny.channel.common.dto.ChemicalFileImagesDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.ChemicalFileImagesVO;
import com.yunny.channel.common.query.ChemicalFileImagesQuery;
import com.yunny.channel.service.ChemicalFileImagesService;
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
@RequestMapping("/chemicalFileImages")
public class ChemicalFileImagesController {

    @Resource
    private ChemicalFileImagesService chemicalFileImagesService;

    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<ChemicalFileImagesVO>> listByPage(@RequestBody ChemicalFileImagesQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
    	CommonPager<ChemicalFileImagesVO> commonPager = chemicalFileImagesService.listByPage(query);
    	return BaseResult.success(commonPager);
    }



    @RequestMapping("/listByChemicalFileId")
    public BaseResult<List<ChemicalFileImagesVO>> listByChemicalFileId(@RequestAttribute("userNo") String userNo,@RequestBody  @Validated({SearchGroup.class}) ChemicalFileImagesQuery query) {
        query.setUserNo(userNo);
        List<ChemicalFileImagesVO> positionVOList = chemicalFileImagesService.listByChemicalFileId(query);
        return BaseResult.success(positionVOList);
    }


    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(chemicalFileImagesService.getById(id));
    }

    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) ChemicalFileImagesDTO chemicalFileImagesDto) {
        chemicalFileImagesService.insertSelective(chemicalFileImagesDto);
        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) ChemicalFileImagesDTO chemicalFileImagesDto) {
        chemicalFileImagesService.updateSelective(chemicalFileImagesDto);
        return BaseResult.success();
    }
}
