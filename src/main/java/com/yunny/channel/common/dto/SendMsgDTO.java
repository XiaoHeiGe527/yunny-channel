package com.yunny.channel.common.dto;

import com.yunny.channel.common.entity.NodeRecodeDO;
import lombok.Data;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/26 15:04
 * @motto The more learn, the more found his ignorance.
 */
@Data
public class SendMsgDTO {

    private String mobile;

    private String content;

    private Long id;

    private Integer responseCode;

    private Integer platformNo;

    /**
     * 短信发送请求实体
     * @param nodeRecodeDO
     * @return
     */
    public static SendMsgDTO build(NodeRecodeDO nodeRecodeDO){

        SendMsgDTO sendMsgDTO=new SendMsgDTO();
        sendMsgDTO.content=nodeRecodeDO.getContent();
        sendMsgDTO.id=nodeRecodeDO.getId();
        sendMsgDTO.mobile=nodeRecodeDO.getMobile();

        return sendMsgDTO;
    }

    /**
     * 短信回调请求实体
     * @param sendMsgDTO
     * @return
     */
    public static SendMsgDTO buildCallBack(SendMsgDTO sendMsgDTO,Integer responseCode,Integer platformNo){

        sendMsgDTO.responseCode=responseCode;
        sendMsgDTO.platformNo=platformNo;

        return sendMsgDTO;
    }
}

