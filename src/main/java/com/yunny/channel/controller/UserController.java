package com.yunny.channel.controller;

import com.yunny.channel.common.entity.UserBaseInfoDO;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserBaseInfoService userBaseInfoService;

    /**
     * 正常条件查询
     * @param userBaseInfoQuery
     * @return
     */
    @PostMapping("/findAll")
    public BaseResult<UserBaseInfoVo> findAll(@RequestBody @Validated({UpdateGroup.class})UserBaseInfoQuery userBaseInfoQuery){

        List<UserBaseInfoVo> voList = userBaseInfoService.selectUserBaseInfoList(userBaseInfoQuery);
        return BaseResult.success(voList);
    }

    /**
     * 分页查询
     * @param userBaseInfoQuery
     * @return
     */
    @PostMapping("/listByQuery")
    public BaseResult listByQuery(@RequestBody UserBaseInfoQuery userBaseInfoQuery){
        userBaseInfoQuery.setPageParameter(new PageParameter(userBaseInfoQuery.getCurrentPage(), userBaseInfoQuery.getPageSize()));
        CommonPager<UserBaseInfoVo> commonPager = userBaseInfoService.listByQuery(userBaseInfoQuery);
        return BaseResult.success(commonPager);
    }


    @PostMapping("/testSendMq")
    public BaseResult<UserBaseInfoVo> testSendMq(@RequestParam("userNo") @NotNull String userNo){

        return userBaseInfoService.testSendMq(userNo);

    }
}
