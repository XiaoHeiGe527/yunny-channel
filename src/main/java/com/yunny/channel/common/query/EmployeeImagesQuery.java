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
public class EmployeeImagesQuery {


     private String userNo;

     /**
      * 关联的员工的PDF文件的员工编号
      */
     private String employeeCode;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
