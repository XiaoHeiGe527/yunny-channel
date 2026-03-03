package com.yunny.channel.controller;

//自定义引入包

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
//文件生成默认引入包
import com.yunny.channel.service.UserInfoService;
import com.yunny.channel.common.dto.UserInfoDTO;
import com.yunny.channel.common.vo.UserInfoVO;
import com.yunny.channel.common.query.UserInfoQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
//import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<UserInfoVO>> listByPage(@RequestBody UserInfoQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
        CommonPager<UserInfoVO> commonPager = userInfoService.listByPage(query);
        return BaseResult.success(commonPager);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResult getById(@RequestParam("id") Long id) {
        return BaseResult.success(userInfoService.getById(id));
    }


    /**
     * 根据ID查询 从URL路径中提取参数 字符串形式接受
     *
     * @param
     * @return
     */
    @GetMapping("/getByIdRequestPath/{id}")
    public BaseResult getByIdRequestPath(@PathVariable String id) {

        // 127.0.0.1:8081/userInfo/getByIdRequestPath/73  输出 id=[3]
        log.info("id=[{}]", id);
        return BaseResult.success(userInfoService.getById(Long.valueOf(id)));
    }


    /**
     * 新增数据
     *
     * @param userInfoDto
     * @return
     */
    @PostMapping("/create")
    public BaseResult create(@RequestBody @Validated({InsertGroup.class}) UserInfoDTO userInfoDto) {
        userInfoService.insertSelective(userInfoDto);
        return BaseResult.success();
    }

    /**
     * 根据ID修改数据 RequestBody 参数形式
     *
     * @param userInfoDto
     * @return
     */
    @PostMapping("/update")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) UserInfoDTO userInfoDto) {
        userInfoService.updateSelective(userInfoDto);
        return BaseResult.success();
    }

    /**
     * 根据ID修改数据 根据表单提交的形式
     *
     * @param userInfoDto
     * @return
     */
    @PostMapping("/updateByModelAttribute")
    public BaseResult updateByModelAttribute(@ModelAttribute UserInfoDTO userInfoDto) {
        /**
         * 值得注意的是，SpringBoot默认情况下只会绑定请求参数的值，
         * 如果表单中存在一个复杂类型的请求参数，SpringBoot就无法自动绑定。这时我们可以使用@RequestParam来手动绑定这些参数：
         * updateByModelAttribute(@ModelAttribute UserInfoDTO userInfoDto, @RequestParam List<String> interests)
         */
        return BaseResult.success(userInfoService.updateSelective(userInfoDto));
    }


    /**
     * 批量将userBaseInfo数据导入usreInfo中
     *
     * @return
     */
    @RequestMapping("/insertUserInfoData")
    public BaseResult insertUserInfoData() {
        return userInfoService.insertUserInfoData(0L);
    }

    /**
     * feign测试
     *
     * @return
     */
    @RequestMapping("/insertUserInfoDataTestFeign")
    public BaseResult insertUserInfoDataTestFeign(@RequestHeader("token") String token) {
        return userInfoService.insertUserInfoDataTestFeign(token);
    }


    @GetMapping("/testGet")
    public BaseResult testGet() {
        log.info("Get方法被调用");
        return BaseResult.success("get请求执行完毕");
    }

    @GetMapping("/testGetToken")
    public BaseResult testGetToken(@RequestHeader("token") String token) {
        log.info("Get方法被调用，token的值为：[{}]",token);
        return BaseResult.success("get请求执行完毕:"+token);
    }


}
