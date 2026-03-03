package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * PDF转换的图片关系表（1个PDF对应多个图片）
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeImagesDO extends BaseDO{
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
}