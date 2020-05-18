package com.yunny.channel.common.query;

import com.yunny.channel.common.page.PageParameter;
import lombok.Data;

@Data
public class PageQuery {

    private Integer currentPage;

    private Integer pageSize;

    private PageParameter pageParameter;
}
