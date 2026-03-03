package com.yunny.channel.controller;

import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.query.ApprovalFlowConfigQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.ApprovalFlowConfigVO;
import com.yunny.channel.common.vo.DictionaryVO;
import com.yunny.channel.service.ApprovalFlowConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ApprovalFlowConfigController
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/1 16:43
 */
@Slf4j
@RestController
@RequestMapping("/approvalFlowConfig")
public class ApprovalFlowConfigController {

    @Resource
    ApprovalFlowConfigService approvalFlowConfigService;
    @RequestMapping("/listByQuery")
    public BaseResult<List<ApprovalFlowConfigVO>> listByQuery(@RequestBody @Validated({SearchGroup.class}) ApprovalFlowConfigQuery query) {
        List<ApprovalFlowConfigVO> approvalFlowConfigVOList = approvalFlowConfigService.listByQuery(query);
        return BaseResult.success(approvalFlowConfigVOList);
    }
}
