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
 * 
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemLogVO {
     /**
      * 
      */
     private Long id;
     /**
      * 操作类型 增加 1 修改 2 删除3 下载 4
      */
     private Integer operationType;
     /**
      * 相关接口路径
      */
     private String url;
     /**
      * 日志详情
      */
     private String content;
     /**
      * 用户号
      */
     private String userNo;
     /**
      * 操作人名称
      */
     private String operatorName;
     /**
      * 备注
      */
     private String remarks;
     /**
      * ip
      */
     private String ip;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
     /**
      * 修改时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;
}