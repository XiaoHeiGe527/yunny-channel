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
public class ChemicalFileImagesQuery {

     private String userNo;

     private Long chemicalFileId;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
