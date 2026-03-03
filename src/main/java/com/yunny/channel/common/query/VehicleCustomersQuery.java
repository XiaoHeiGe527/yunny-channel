package com.yunny.channel.common.query;

import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCustomersQuery {

     private String name;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
