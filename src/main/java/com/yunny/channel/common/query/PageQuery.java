package com.yunny.channel.common.query;

import com.yunny.channel.common.page.PageParameter;
import lombok.Builder;
import lombok.Data;

@Data
public class PageQuery {

    //currentPage 当前页 查询下一页+1以此类推
    private Integer currentPage;

    //pageSize每页显示多少条 当currentPage增加1页则查下一页数据
    private Integer pageSize;

    private PageParameter pageParameter;
}
