package com.yunny.channel.common.query;

import com.yunny.channel.common.dto.SystemRoleResourceDTO;
import com.yunny.channel.common.interfaces.InsertGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleResourceQuery {

     @NotNull(message = "角色ID不可为空", groups = {InsertGroup.class})
     private Integer roleId;
     /**
      *
      */
     private List<Long> resourceId;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;


}
