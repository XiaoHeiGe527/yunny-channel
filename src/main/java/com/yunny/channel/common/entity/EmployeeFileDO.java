package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 员工表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFileDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 员工编号
      */
     private String employeeCode;
     /**
      * 员工姓名
      */
     private String name;
     /**
      * 员工部门
      */
     private String department;
     /**
      * 员工职级
      */
     private String rank;
     /**
      * 员工岗位
      */
     private String profession;
     /**
      * 存放位置编码
      */
     private String positionCode;
     /**
      * 状态 -0离职 -1在职
      */
     private Integer state;
     /**
      * 备注
      */
     private String remarks;
     /**
      * PDF附件地址
      */
     private String pdfFileUrl;
     /**
      * 处理人
      */
     private String updateMan;

     /**
      * 文件位置说明
      */
     private String positionCodeRemarks;
}