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
public class ChemicalFileImagesDTO {
     /**
      * 图片唯一标识（自增主键）
      */
     private Long id;
     /**
      * 关联的PDF文件ID（外键，关联chemical_file表的id）
      */
     private Long chemicalFileId;
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
     private LocalDateTime createTime;
}