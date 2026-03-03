package com.yunny.channel.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVmQuery {

    private String userNo;
    private String machineKey;
    private String orderNo;


   public OrderVmQuery(String userNo, String machineKey){
       this.setUserNo(userNo);
       this.setMachineKey(machineKey);
   }

}
