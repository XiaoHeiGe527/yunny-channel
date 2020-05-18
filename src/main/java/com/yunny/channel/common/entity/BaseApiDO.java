package com.yunny.channel.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiDO extends BaseDO {


    private Integer id;

    private Integer vmType;

    private Integer configId;

    private Integer sort;

    private String explain;

}
