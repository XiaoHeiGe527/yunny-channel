package com.yunny.channel.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Face {

    String face_id;
    Rect rect;
    Integer age;
    Integer gender;
    Object[] landmarks21;
    Object pose;
}
