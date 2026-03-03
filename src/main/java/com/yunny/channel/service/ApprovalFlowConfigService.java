package com.yunny.channel.service;

import com.yunny.channel.common.query.ApprovalFlowConfigQuery;
import com.yunny.channel.common.vo.ApprovalFlowConfigVO;

import java.util.List;

public interface ApprovalFlowConfigService {


    List<ApprovalFlowConfigVO> listByQuery(ApprovalFlowConfigQuery query);
}
