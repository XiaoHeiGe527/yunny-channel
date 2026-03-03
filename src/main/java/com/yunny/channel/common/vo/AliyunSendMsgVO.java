package com.yunny.channel.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AliyunSendMsgVO {

    private String resultBody;

    private String resultCode;

    private String requestId;

    private String bizId;

}
