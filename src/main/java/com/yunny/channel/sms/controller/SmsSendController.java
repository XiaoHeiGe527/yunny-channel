package com.yunny.channel.sms.controller;


import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.SendMsgDTO;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.AliyunSendMsgVO;
import com.yunny.channel.sms.service.ShortMessageSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.yunny.channel.common.code.SmsCodeEnum.FAILED_TO_SEND_VERIFICATION_CODE;
import static com.yunny.channel.common.code.SmsCodeEnum.UNKNOWN_VERIFICATION_CODE_ERROR;


/**
 * 短信发送平台
 *
 * @explain
 * @createTime 2019/12/20 15:38
 * @motto The more learn, the more found his ignorance.
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    private ShortMessageSend shortMessageSend;



    @PostMapping("/send")
    public BaseResult smsSendMessage(@RequestBody SendMsgDTO sendMsgDTO) {

        Map responseMap = shortMessageSend.sendShortMessage(sendMsgDTO.getMobile(), sendMsgDTO.getContent());

        //短信发送完毕结果处理 回调业务逻辑  （更新短信记录的 状态 发送成功 还是失败）
        //smsInfoFeign.sendCallBack(SendMsgDTO.buildCallBack(sendMsgDTO, Integer.parseInt(responseMap.get("code").toString()), MobileMessageCode.LEXIN_SHORT_MESSAGE_PLATFORM));

        switch (responseMap.get("code").toString()) {
            case "0":
                return BaseResult.success();
            case "1":
                return BaseResult.failure(FAILED_TO_SEND_VERIFICATION_CODE.getCode(), FAILED_TO_SEND_VERIFICATION_CODE.getMessage());
            default:
                return BaseResult.failure(UNKNOWN_VERIFICATION_CODE_ERROR.getCode(), UNKNOWN_VERIFICATION_CODE_ERROR.getMessage());
        }

        //todo ip调用

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //RestTemplate带参传的时候要用HttpEntity<?>对象传递
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("mobile", sendMessageDto.getMobile());
//        map.put("id",sendMessageDto.getId());
//        map.put("platformNo",1);
//        map.put("responseCode",Integer.parseInt((String) responseMap.get("code")));
//        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);
//
//        String url = String.format(sendMessageDto.getCallBackUrl());
//
//        restTemplate.postForEntity(url, request,  String.class);

    }


    /**
     * sunfuwei
     * @param sendMsgDTO
     * @return
     */
    @PostMapping("/aliyunSendMsg")
    public BaseResult<AliyunSendMsgVO> aliyunSendMsg(@RequestBody SendMsgDTO sendMsgDTO) {

        /**
         * 调用阿里云第三方发送短信
         */
       BaseResult<AliyunSendMsgVO>  result =  shortMessageSend.aliyunSendMsg(sendMsgDTO.getMobile(),sendMsgDTO.getContent());

        /**
         * 处理回调业务逻辑 更新短信状态
         */
      //  smsInfoFeign.aliyunSendCallbackMsg(SendMsgDTO.buildCallBack(sendMsgDTO, result.getCode(), MobileMessageCode.ALIYUN_SHORT_MESSAGE_PLATFORM));

        AliyunSendMsgVO asvo =  result.getData();

        if( result.getCode()!= ExceptionConstants.RESULT_CODE_SUCCESS){
            log.error("虚机提醒短信发送失败！返回信息:[{}],[{}];第三方返回的错误信息: 错误代码:[{}],错误信息:[{}]",
                    result.getCode(),result.getMessage(),asvo.getResultCode(),asvo.getResultBody());
        }

     return result;

    }

}

