package com.yunny.channel.common.query;

import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVehiclesQuery {

     /**
      * 车主,所属公司
      */
     private String carOwner;

     /**
      * 车辆是否受车队管理： 0 不受车队管理车辆，1 受车队管理
      */
     private Integer isManage;

     private String carNumber;

     /**
      * 车型:1 特三,2丰田,3 希尔,4五菱,5春星,6货,7长城货,8长城,9 红宇,10 鸿星达,11 哈弗,12 霸道,13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多
      */
     private Integer carType;

     /**
      * 车辆使用状态 ：1 可用，2 外出中
      */
     private Integer activeState;

     /**
      * 车身状态： 0 报废车辆，1 正常使用，2 维修中 ，3 其他
      */
     private Integer state;


     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
