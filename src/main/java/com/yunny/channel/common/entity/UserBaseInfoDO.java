package com.yunny.channel.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBaseInfoDO  extends BaseDO {

    private Long id;

    private String userNo;

    private String userName;

    private String userNickName;

    private String password;

    private String mobile;

    private String userPicture;

    private String userEmail;

    private Integer state;

    private Integer limitType;
}
