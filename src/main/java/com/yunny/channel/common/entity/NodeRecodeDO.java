package com.yunny.channel.common.entity;

import com.yunny.channel.common.code.MobileMessageCode;
import com.yunny.channel.common.dto.NodeRecodeDTO;
import com.yunny.channel.common.dto.SendMsgDTO;
import com.yunny.channel.util.CodeUtils;
import lombok.Data;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/26 14:24
 * @motto The more learn, the more found his ignorance.
 */
@Data
public class NodeRecodeDO extends BaseDO{

    private Long id;

    private String userNo;

    private String mobile;

    private String code;

    private String content;

    private Integer type;

    private Integer platformNo;

    private Integer state;

    /**
     * 构建插入实体
     * @param nodeRecodeDTO
     * @return
     */
    public static NodeRecodeDO build(NodeRecodeDTO nodeRecodeDTO){

        NodeRecodeDO nodeRecodeDO=new NodeRecodeDO();

        String code= CodeUtils.getNumberRandom(MobileMessageCode.DEFAULT_SECURITY_CODE_LENGTH);
        nodeRecodeDO.code=code;
        nodeRecodeDO.content="您的手机验证码为"+code+",有效时间为5分钟,请妥善保存！";
        nodeRecodeDO.mobile=nodeRecodeDTO.getMobile();
        nodeRecodeDO.state=MobileMessageCode.DEFAULT_SHORT_MESSAGE_STATE;
        nodeRecodeDO.platformNo=MobileMessageCode.DEFAULT_SHORT_MESSAGE_PLATFORM;
        nodeRecodeDO.type=nodeRecodeDTO.getType();
        nodeRecodeDO.userNo=nodeRecodeDTO.getUserNo()==null?"":nodeRecodeDTO.getUserNo();

        return nodeRecodeDO;
    }

    public static NodeRecodeDO buildCallBack(SendMsgDTO sendMsgDTO){

        NodeRecodeDO nodeRecodeDO=new NodeRecodeDO();


        nodeRecodeDO.mobile=sendMsgDTO.getMobile();
        nodeRecodeDO.id=sendMsgDTO.getId();

        if(sendMsgDTO.getResponseCode()!=null){
            nodeRecodeDO.state=sendMsgDTO.getResponseCode()==0?0:2;
        }else {
            nodeRecodeDO.state=3;
        }

        nodeRecodeDO.platformNo=sendMsgDTO.getPlatformNo();

        return nodeRecodeDO;
    }
}

