package com.yunny.channel.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Builder 通过Builder方法写入值
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDataVO {

    /**
     * 数据中心ID
     */
    private String gvdicrewId;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 如 multipleCompany = false，则该字段表示该账号对应的token
     */
    private String token;

}
