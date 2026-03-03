package com.yunny.channel.common.dto;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Mr. Du
 * @explain 手机发送验证码
 * @createTime 2019/12/26 14:42
 * @motto The more learn, the more found his ignorance.
 */
@Data
public class NodeRecodeDTO implements Serializable {

    private String userNo;
    @NotBlank(message = "手机号码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    //@Pattern(message = "手机号码不正确", groups = {InsertGroup.class, UpdateGroup.class}, regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$")
    @Size(min = 11, max = 11, message = "手机号码不正确")
    private String mobile;
    @NotNull(message = "验证码发送方式不存在", groups = {InsertGroup.class, UpdateGroup.class})
    @Max(value = 6, message = "验证码发送方式不存在", groups = {InsertGroup.class})
    @Min(value = 1, message = "验证码发送方式不存在", groups = {InsertGroup.class})
    private Integer type;

    private String code;

    private String content;

    private Integer platformNo;

    private Integer state;

}

