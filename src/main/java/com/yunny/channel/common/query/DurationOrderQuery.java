package com.yunny.channel.common.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DurationOrderQuery {

    private String dueTimeStr;

    List<Integer> stateList;
}
