package com.yunny.channel.common.vo;

import lombok.Data;

@Data
public class UserBaseInfoVo {

    private Long id;

    private String userNo;

    private String userName;

    private String userNickName;

    private String password;

    private String mobile;

    private String userPicture;

    private String userEmail;

    private Integer state;
}
