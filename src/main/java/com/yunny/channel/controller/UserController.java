package com.yunny.channel.controller;

import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.common.util.excel.constant.UserBaseInfoExcel;
import com.yunny.channel.common.util.excel.util.EasyExcelUtil;
import com.yunny.channel.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
     * {
     *   "currentPage": 3, 当前页面从1开始 下一页时 +1
     *   "pageSize": 2  //页面显示多少条
     *
     * }
     * @return
     */
    @PostMapping("/listByQuery")
    public BaseResult listByQuery(@RequestBody UserBaseInfoQuery userBaseInfoQuery){
        userBaseInfoQuery.setPageParameter(new PageParameter(userBaseInfoQuery.getCurrentPage(), userBaseInfoQuery.getPageSize()));
        CommonPager<UserBaseInfoVo> commonPager = userBaseInfoService.listByQuery(userBaseInfoQuery);
        return BaseResult.success(commonPager);
    }


    /**
     * MQ测试
     * @param userNo
     * @return
     */
    @PostMapping("/testSendMq")
    public BaseResult<UserBaseInfoVo> testSendMq(@RequestParam("userNo") @NotNull String userNo){
        //(@RequestAttribute("userNo") String companyCode, @RequestParam("userNo") @RequestHeader("userNo")
        return userBaseInfoService.testSendMq(userNo);

    }

    /**
     * 多线程处理数据
     * @param count
     * @return
     */
    @PostMapping("/testExecutorStr")
    public BaseResult<UserBaseInfoVo> testExecutorStr(@RequestParam("count") @NotNull Integer count){
        return userBaseInfoService.testExecutorStr(count);

    }

    /**
     * 报表导出
     * @param userBaseInfoQuery
     * @param response
     * @throws Exception
     */
    @PostMapping("/export")
    public void export(@RequestBody UserBaseInfoQuery userBaseInfoQuery, HttpServletResponse response) throws Exception {
        userBaseInfoQuery.setPageParameter(null);

        List<UserBaseInfoExcel>  excelList = userBaseInfoService.findList(userBaseInfoQuery);
        EasyExcelUtil.exprotExcel(response, excelList, "用户统计表", "用户统计", UserBaseInfoExcel.class);
    }
}
