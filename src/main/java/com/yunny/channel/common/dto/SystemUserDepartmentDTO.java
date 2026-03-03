package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDepartmentDTO {
     /**
      * 主键ID
      */
     private Long id;
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
     private Integer isMain;
     /**
      * 绑定时间
      */
     private LocalDateTime createTime;
     /**
      * 更新时间
      */
     private LocalDateTime updateTime;
}