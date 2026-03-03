package com.yunny.channel.common.dto;


import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationSwitchDTO {

    @NotBlank(message = "订单编号不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String orderNo;

    @NotNull(message = "状态不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @Range(min = 1, max = 2, message = "状态只能1-2之间 (状态   1使用中 2停用中)", groups = {InsertGroup.class,UpdateGroup.class})
    private Integer state;
}
