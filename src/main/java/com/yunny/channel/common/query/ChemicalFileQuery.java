package com.yunny.channel.common.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChemicalFileQuery {

     /**
      * 文件类型
      */
     private String fileType;
     /**
      * 文件类型编码
      */
     private String fileTypeCode;
     /**
      * 类型编号
      */
     private String typeCode;
     /**
      * 文件编号
      */
     private String documentCode;
     /**
      * 存放位置编码
      */
     private String positionCode;
     /**
      * 档案类型
      */
     private String documentType;
     /**
      * 文件标题
      */
     private String documentTitle;
     /**
      * 文件内容
      */
     private String documentContent;
     /**
      * 签字日期 精确到日
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime signatureTime;
     /**
      * 归档日期 精确到年 字典=签字 日期
      */
     private String placeTime;
     /**
      * 文档到期时间 选填
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime expirationDate;
     /**
      * 文档说明
      */
     private String documentDescribe;
     /**
      * 备注  系统填
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
     private LocalDateTime updateTime;
     /**
      * 创建时间
      */
     private LocalDateTime createTime;
     /**
      * 文件状态 1有效 0无效
      */
     private Integer state;


     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
