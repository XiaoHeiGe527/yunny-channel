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
 * PDF转换的图片关系表（1个PDF对应多个图片）
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeImagesVO {
     /**
      * 图片唯一标识（自增主键）
      */
     private Long id;
     /**
      * 关联的员工的PDF文件的员工编号
      */
     private String employeeCode;
     /**
      * 文件访问链接
      */
     private String imageUrl;
     /**
      * 图片在服务器上的存储路径（例如：/upload/pdf_images/123/1.png）
      */
     private String imagePath;
     /**
      * 对应PDF的页码（从1开始，确保顺序）
      */
     private Integer pageNum;
     /**
      * 图片格式（如png、jpg、jpeg）
      */
     private String imageFormat;
     /**
      * 图片生成时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
}