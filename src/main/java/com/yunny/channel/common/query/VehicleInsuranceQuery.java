package com.yunny.channel.common.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInsuranceQuery {

     @NotNull(message = "ID不能为空", groups = {UpdateGroup.class})
     private Long id;

     @NotBlank(message = "投保人不能为空", groups = {UpdateGroup.class})
     private String insured;

     @NotNull(message = "续保时间不能为空", groups = {UpdateGroup.class})
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime renewalDate;

     private String underwritingDateStart;
     private String underwritingDateEnd;

     private String expiryAlert;

     /**
      * 批次号
      */
     private String batchNo;

     /**
      * 车型
      */
     private Integer carType;


     /**
      * 车牌号
      */
     private String carNumber;

     /**
      * 精准查询 车牌号
      */
     private String carNumberPrecise;


     /**
      * 续保年数 默认是1
      */
     @NotNull(message = "续保年数不可为空", groups = {UpdateGroup.class})
     @Range(min = 1, max = 100, message = "续保年份在1~100之间", groups = {UpdateGroup.class})
     private Integer  renewalYears;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
