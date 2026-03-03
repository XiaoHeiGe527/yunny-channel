package com.yunny.channel.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
     /**
      * 
      */
     private Long id;

     /**
      * 企业管理平台生成的用户唯一标识
      */
     private String cmUserNo;
     /**
      * 第三方平台用户唯一标示
      */
     private String userId;
     /**
      * 用户在服务商内唯一标示
      */
     private String unionId;
     /**
      * 用户名称，唯一性
      */
     private String name;
     /**
      * 公司编号，全局唯一
      */
     private String companyCode;

     /**
      * 是否为企业超级管理员
      */
     private Boolean superAdmin;

     private Boolean active;
     /**
      * 账号启用状态 0：不可用  1：可用，和active共同决定云之趣账号的激活状态
      * 只有active和status都是true时，云之趣账号才是启用状态
      */
     private Integer status;
     /**
      * 员工所在部门列表
      */
     private List<Long> departmentIds;

     /**
      * 管理的部门列表
      */
     private List<Long> leaderInDeptIds;

     /**
      * 员工角色id
      */
     private List<Long> roleIds;

     @Override
     public boolean equals(Object o) {
          if (this == o) {
               return true;
          }
          if (o == null || getClass() != o.getClass()) {
               return false;
          }
          EmployeeDTO that = (EmployeeDTO) o;
          return Objects.equals(id, that.id) &&
                  Objects.equals(userId, that.userId) &&
                  Objects.equals(unionId, that.unionId) &&
                  Objects.equals(name, that.name) &&
                  Objects.equals(companyCode, that.companyCode) &&
                  Objects.equals(superAdmin, that.superAdmin) &&
                  Objects.equals(active, that.active) &&
                  Objects.equals(departmentIds, that.departmentIds) &&
                  Objects.equals(leaderInDeptIds, that.leaderInDeptIds);
     }

     @Override
     public int hashCode() {
          return Objects.hash(id, userId, unionId, name, companyCode, superAdmin, active, departmentIds, leaderInDeptIds);
     }

}