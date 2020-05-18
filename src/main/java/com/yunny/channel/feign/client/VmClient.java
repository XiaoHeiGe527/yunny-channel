package com.yunny.channel.feign.client;




import com.yunny.channel.feign.parameter.VmClientReq;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Map;

@Headers({"Content-Type: application/json","Accept: application/json"})
public interface VmClient {

    /**
     * 判断虚拟机是否还有库存
     * @param vmClientReq
     * @return
     */
    @RequestLine("POST /vm_is_exist")
    public Map vm_is_exist(URI uri, @RequestBody VmClientReq vmClientReq);
}
