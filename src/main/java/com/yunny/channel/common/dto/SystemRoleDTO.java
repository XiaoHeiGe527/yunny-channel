package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleDTO {
     /**
      * 
      */
     @NotNull(message = "ID不可为空", groups = {UpdateGroup.class})
     private Integer id;
     /**
      * 角色名
      */
     private String name;
     /**
      * 角色说明
      */
     private String content;
     /**
      * 创建时间
      */
     private LocalDateTime createTime;
     /**
      * 更新时间
      */
     private LocalDateTime updateTime;
     /**
      * 创建人
      */
     private String createMan;
     /**
      * 更新人
      */
     private String updateMan;
     /**
      * 0:管理员 1:普通
      */
     private Integer type;
     /**
      * 0:无效 1:有效
      */
     private Integer state;
}