package com.yunny.channel.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数;
@AllArgsConstructor
//作用于类，生成一个无参构造方法
@NoArgsConstructor
public class InitResponse {

    private Object status;
    private String image_id;
    private String message;
    private Integer width;
    private Integer height;
    private Face[] faces;
    private String error;
}
