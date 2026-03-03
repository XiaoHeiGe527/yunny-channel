package com.yunny.channel.common.query;

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.*;
import com.yunny.channel.common.page.PageParameter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFileQuery {


     /**
      * 员工编号
      */
     @NotBlank(message = "员工编号不能为空", groups = {InsertGroup.class, UpdateGroup.class})
     private String employeeCode;


     /**
      * 唯一查询，修改增加使用
      */
     private String employeeCodeOnly;


     /**
      * 员工姓名
      */
     @NotBlank(message = "员工姓名不能为空", groups = {InsertGroup.class})
     private String name;
     /**
      * 员工部门
      */
     @NotBlank(message = "部门不能为空", groups = {InsertGroup.class})
     private String department;
     /**
      * 员工职级
      */
     @NotBlank(message = "员工职级不能为空", groups = {InsertGroup.class})
     private String rank;
     /**
      * 员工岗位
      */
     @NotBlank(message = "员工岗位不能为空", groups = {InsertGroup.class})
     private String profession;
     /**
      * 存放位置编码
      */
     private String positionCode;
     /**
      * 状态 -0离职 -1在职
      */
     @NotNull(message = "状态不能为空", groups = {InsertGroup.class, UpdateGroup.class})
     @Range(min = 0, max = 1, message = "状态只能0-1之间 (状态   0离职 1在职)", groups = {InsertGroup.class})
     private Integer state;
     /**
      * 备注
      */
     private String remarks;
     /**
      * PDF附件地址
      */
     private String pdfFileUrl;

     /**
      * 排除的记录 ID（用于修改时校验唯一性，排除当前记录）
      */
     private Long excludeId;

     /**
      * 处理人
      */
     private String updateMan;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
