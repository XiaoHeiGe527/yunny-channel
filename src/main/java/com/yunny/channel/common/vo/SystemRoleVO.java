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
 * 系统角色表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleVO {
     /**
      * 
      */
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