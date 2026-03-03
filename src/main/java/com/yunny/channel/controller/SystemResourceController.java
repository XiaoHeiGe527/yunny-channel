package com.yunny.channel.controller;


import com.google.gson.Gson;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.dto.SystemResourceDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.query.SystemRoleResourceQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.SystemResourceVO;
import com.yunny.channel.common.query.SystemResourceQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemResourceService;
import com.yunny.channel.service.SystemRoleResourceService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/systemResource")
public class SystemResourceController {

    @Resource
    private SystemResourceService systemResourceService;


    @Resource
    private SystemRoleResourceService systemRoleResourceService;


    @Resource
    private SystemLogService systemLogService;

    @Resource
    private SystemUserService systemUserService;


    @PostMapping("/selectUserResourceList")
    public BaseResult selectUserResourceList(@RequestAttribute("userNo") String userNo) {
        List<String>  resourceList = systemResourceService.selectUserResourceList(userNo);
        return BaseResult.success(resourceList);
    }


    @RequestMapping("/listByQuery")
    public BaseResult<List<SystemResourceVO>> listByQuery(@RequestBody  @Validated({SearchGroup.class}) SystemResourceQuery query) {
        List<SystemResourceVO> positionVOList = systemResourceService.listByQuery(query);
        return BaseResult.success(positionVOList);
    }


    @RequestMapping("/listByRoleId")
    public BaseResult<List<SystemResourceVO>> listByRoleId(@RequestBody  @Validated({SearchGroup.class}) SystemResourceQuery query) {
        if(query.getRoleId()!=null){
            List<SystemResourceVO> positionVOList = systemResourceService.listByRoleId(query);
            return BaseResult.success(positionVOList);
        }
        return BaseResult.success();

    }



    @RequestMapping("/assignPermissions")
    public BaseResult<List<SystemResourceVO>> assignPermissions(@RequestAttribute("userNo") String userNo, HttpServletRequest httpServletRequest,
                                                                @RequestBody  @Validated({InsertGroup.class}) SystemRoleResourceQuery systemRoleResourceQuery) {
        BaseResult result =  systemRoleResourceService.assignPermissions(systemRoleResourceQuery);

        if(result.getCode()== ExceptionConstants.RESULT_CODE_SUCCESS){
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("分配权限:[" + this.toJSON(systemRoleResourceQuery) + "]")
                    .userNo(userNo)
                    .createTime(LocalDateTime.now())
                    .url("/systemResource/assignPermissions")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .remarks("分配权限")
                    .build();
            systemLogService.insertSelective(systemLogDTO);
        }

        return result;
    }


    /**
     * 获取用户导航权限（AOP排除这个校验）
     * @param userNo
     * @return
     */
    @PostMapping("/selectUserValid2LevelResourcesByUserNo")
    public BaseResult<List<SystemResourceVO>>  selectUserValid2LevelResourcesByUserNo(@RequestAttribute("userNo") String userNo) {
        return BaseResult.success(systemResourceService. selectUserValid2LevelResourcesByUserNo(userNo));

    }



    private String toJSON(SystemRoleResourceQuery systemRoleResourceQuery) {
        Gson gson = new Gson();
        return gson.toJson(systemRoleResourceQuery);
    }

}
