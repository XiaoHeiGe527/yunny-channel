package com.yunny.channel.common.query;

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserBaseInfoQuery extends PageQuery {

    private Long id;

    private String userNo;
    /**
     * todo
     */
    private String userName;

    //@NotBlank(message = "商品名称不能为空",groups={InsertGroup.class, UpdateGroup.class})
    private String mobile;

    private String userEmail;
}
