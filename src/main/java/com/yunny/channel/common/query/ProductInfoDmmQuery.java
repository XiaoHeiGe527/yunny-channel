package com.yunny.channel.common.query;


import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/3/30 14:27
 * @motto The more learn, the more found his ignorance.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDmmQuery {

    private Integer channelNo;

    /**
     * 商品名称模糊查询 todo
     */
    private String productName;

    private Integer currentPage;

    private Integer pageSize;

    private PageParameter pageParameter;

    /**
     * 盒子端参数
     */
    private String deviceNo;

    /**
     * 订单状态
     */
    private String userNo;

    private Integer state;

    private List<String> productNoList;

    private String white;
}

