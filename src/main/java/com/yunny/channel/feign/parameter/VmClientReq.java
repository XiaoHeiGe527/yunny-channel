package com.yunny.channel.feign.parameter;

/**
 * @author: zp
 * @date:2019/8/19 13:54
 * @description:调用api参数
 */


public class VmClientReq {

    public int vm_type;
    public int vm_config;
    public String user_name;
    public String date;
    public String vm_id;
    public String user_pwd;
    public String opr_mode;//hard:硬重启，soft:软重启

    public VmClientReq()
    {
        this.user_pwd = "123.com";
        this.vm_config = 100; //1:低端 2:中端 3:高端 100:翱游公司
        this.opr_mode = "hard";
    }

    public VmClientReq(int type, String user_name)
    {
        this.user_pwd = "123.com";
        this.vm_config = 100; //1:低端 2:中端 3:高端 100:翱游公司
        this.user_name = user_name;
        this.vm_type = type;
        System.out.println(this.user_name);
    }

    public VmClientReq(String date, int type){
        this.vm_type = type;
        this.date = date;
    }

    public VmClientReq(String date){
        this.date = date;
    }
}
