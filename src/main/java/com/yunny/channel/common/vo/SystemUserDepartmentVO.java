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
 * 用户-部门关联表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDepartmentVO {

     /**
      * 用户ID（关联system_user.id）
      */
     private String userNo;
     /**
      * 部门编码（关联dictionary.code_num，且dictionary.category="部门"）
      */
     private String deptCode;
     /**
      * 是否主部门：1-是，0-否（解决用户多部门时的“主归属”问题）
      */
     private Integer isMainDept;

     private String loginName;
     /**
      *
      */
     private String userName;


     private String deptName;

     /**
      * 绑定时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
     /**
      * 更新时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;
}