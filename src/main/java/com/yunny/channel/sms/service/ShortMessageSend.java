package com.yunny.channel.sms.service;

import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.AliyunSendMsgVO;

import java.util.Map;

/**
 * @author Mr. Du
 * @explain 短信发送统一接口
 * @createTime 2019/12/20 16:01
 * @motto If you would have leisure, do not waste it.
 */

public interface ShortMessageSend {

    /**
     * 短信发送业务接口
     * @param mobile
     * @param content
     * @return
     */
    Map sendShortMessage(String mobile, String content);


    /**

     * 阿里短信发送接口信息  支持批量发送 ps--目前签名信息仅设置一个

     *

     * @param phone        需要发送的电话号码，支持多个电话号码 格式为"13600000000,15000000000"
     *                     支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,

     * @param templateParam 模板内需要填充的字段及字段值 格式为("{\"name\":\"Tom\", \"code\":\"123\"}")

     * @Return true 代表发送成功  false 代表发送失败

     */
    BaseResult<AliyunSendMsgVO> aliyunSendMsg(String phone, String templateParam);
}
