package com.yunny.channel.common.vo;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * Created by fe
 * 员工表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFileVO {
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
      * 修改时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;

     /**
      * 文件位置说明
      */
     private String positionCodeRemarks;
}