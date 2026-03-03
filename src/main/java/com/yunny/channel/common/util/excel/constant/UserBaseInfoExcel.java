package com.yunny.channel.common.util.excel.constant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yunny.channel.common.entity.UserBaseInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseInfoExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ExcelProperty(value = "列名称", index = 列索引)
    @ExcelProperty(value = "用户编号", index = 0)
    private String userNo;

    @ExcelProperty(value = "用户名称", index = 1)
    private String userName;

    @ExcelProperty(value = "用户昵称", index = 2)
    private String userNickName;

    @ExcelProperty(value = "用户手机号", index = 3)
    private String mobile;

    @ExcelProperty(value = "用户邮箱", index = 4)
    private String userEmail;

    public static UserBaseInfoExcel builder(UserBaseInfoDO e) {
        UserBaseInfoExcel userBaseInfoExcel = new UserBaseInfoExcel();
        userBaseInfoExcel.setUserNo(e.getUserNo());
        userBaseInfoExcel.setUserName(e.getUserName());
        userBaseInfoExcel.setUserNickName(e.getUserNickName());
        userBaseInfoExcel.setMobile(e.getMobile());
        userBaseInfoExcel.setUserEmail(e.getUserEmail());
        return userBaseInfoExcel;
    }

}
