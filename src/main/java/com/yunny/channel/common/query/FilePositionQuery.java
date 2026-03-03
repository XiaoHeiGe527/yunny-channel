package com.yunny.channel.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePositionQuery {


     /**
      * 柜子编号
      */
     private String cabinetNum;

     /**
      * 档案类型 1 技术文件；2 人事文件，3行政文件，4其他文件
      */
     private Integer fileType;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
