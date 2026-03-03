package com.yunny.channel.common.util.httpClient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.StringUtil;
import com.yunny.channel.common.vo.LoginDataVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.yunny.channel.common.constant.ExceptionConstants.RESULT_CODE_SUCCESS;

/**
 * value属性，该属性就是我们常说的指定的作用域，比如在spring中默认有singleton，prototype两种，springmvc中默认有request，sessioon等作用域；
 * singleton 单实例的(单例)(默认)   ----全局有且仅有一个实例
 * prototype 多实例的(多例)   ---- 每次获取Bean的时候会有一个新的实例
 * reqeust    同一次请求 ----request：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
 * session    同一个会话级别 ---- session：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
 *
 * proxyMode属性，该属性就是是否指定作用域代理，它有4个可选的值
 * 其中DEFAULT和NO这两个值表示不使用作用域代理
 *     DEFAULT,
 *    NO,
 *    INTERFACES和TARGET_CLASS都表示使用作用域代理，
 *    INTERFACES, INTERFACES表示使用JDK动态代理模式
 *    使用cglib代理模式
 *    TARGET_CLASS
 *
 *   请求登录接口服务工具类 prototype多例模式,这里不能作为单例，不可共享属性
 *   ScopedProxyMode.TARGET_CLASS，来告诉spring这个bean需要创建动态代理。
 *   这样每次获取单例orderService的时候，UserService都重新生成。
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginServerUtil {

    /**
     * 域名 作为配置项
     */
    private String hostPath;

    /** cookie值字符串 */
    private String cookies;

    /**
     * 登录路径
     */
    private static final String LOGIN_PATH = "/login/yunny/login";

    /**
     * 返回对象
     */
    private Map resultMap = new HashMap(4);

    /**
     * cookie键值对
     */
    private Map<String, String> cookieMap = new HashMap<>();


    /**
     * http客户端
     */
    private CloseableHttpClient httpClient;
    //构造httpClient配置
    {
        this.resultMap.put("code", ExceptionConstants.HTTP_POST_FAIL);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        //客户端和服务器建立连接的timeout
        requestConfigBuilder.setConnectTimeout(30000);
        //从连接池获取连接的timeout
        requestConfigBuilder.setConnectionRequestTimeout(30000);
        //连接建立后，request没有回应的timeout
        requestConfigBuilder.setSocketTimeout(30000);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        //连接建立后，request没有回应的timeout
        clientBuilder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(30000).build());
        clientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClient = clientBuilder.build();
    }

    /**
     * 模拟调用登录接口
     * 用post body JSON方式传参
     * @param paramMap
     * @return
     */
    public BaseResult login(Map<String, String> paramMap) {
        this.hostPath = paramMap.get("loginHostPath");
        // 创建POST请求对象
        HttpPost httpPost = new HttpPost(hostPath + LOGIN_PATH);
        log.info("请求登录地址：[{}],hostPath:[{}],LOGIN_PATH:[{}]", hostPath + LOGIN_PATH, hostPath, LOGIN_PATH);
        /**
         * 添加请求头信息post body JSON方式传参
         */
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        CloseableHttpResponse response = null;
        try {

            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("id", "10");
            paraMap.put("user_no", "u20200427000001");
            paraMap.put("account", "13695650203");
            paraMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
            //json格式传参
            String param = JSON.toJSONString(paraMap);
            httpPost.setEntity(new StringEntity(param));
            // 执行请求
            response = this.httpClient.execute(httpPost);
            this.resultMap.put("message---getReasonPhrase=[{}]", response.getStatusLine().getReasonPhrase());

            // 取响应的结果
            int statusCode = response.getStatusLine().getStatusCode();

            // 校验返回状态
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                //获取请求参数 字符串形式
                String resp = EntityUtils.toString(response.getEntity(), "utf-8");

                System.out.println("status:" + statusCode);
                System.out.println("result:" + resp);
                /**
                 * 请求码判断
                 */
                JSONObject jsonObject = JSON.parseObject(resp);
                Integer respCode = Integer.valueOf(jsonObject.get("code").toString());

                /** cookie处理 */
                getCookieFromResponse(response);

                if (HttpStatus.SC_OK != respCode) {
                    return BaseResult.failure(respCode, jsonObject.get("message").toString());
                }
                return this.getLoginDataVOByEntityString(resp);

            } else {
                log.error("模拟登录失败，返回码为[{}]，返回信息为[{}]", response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                this.resultMap.put("message", response.getStatusLine().getReasonPhrase());
                this.resultMap.put("code", response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            log.error("模拟登录失败，错误原因为：", e);
            this.resultMap.put("message", e.getMessage());
            this.resultMap.put("code", ExceptionConstants.RESULT_FALSE);
        } finally {

            log.info("执行HTTP请求中的异常finally区域==============");
            // 关闭连接
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("释放模拟登录response失败，错误原因为：", e);
                }
            }
        }
        return BaseResult.success();
    }

    /**
     * 处理返回数据与登录接口/login/yunny/login返回数据一致
     * @param httpEntity
     * @return
     */
    private BaseResult getLoginDataVOByEntityString(String httpEntity) {

        BaseResult baseResultData = this.getBaseResultByHttpEntity(httpEntity);

        if (baseResultData.getData() != null && !StringUtil.isEmpty(baseResultData.getData() .toString())) {
            //得到BaseResult里的data的JSON字符串形式的数据
            JSONObject baseResultDataJson = JSON.parseObject(baseResultData.getData() .toString());
            LoginDataVO loginData = LoginDataVO.builder()
                    .token(baseResultDataJson.get("token").toString())
                    .userNo(baseResultDataJson.get("userNo").toString())
                    .build();

            return    BaseResult.success(loginData);
        }
        return    BaseResult.success();
    }


    /**
     * 处理cookies
     *
     * @param response
     * @return
     */
    public void getCookieFromResponse(CloseableHttpResponse response) {
        // 获取cookie
        Header[] responseHeaders = response.getHeaders("Set-Cookie");
        for (Header cookie : responseHeaders) {
            String value = cookie.getValue();
            String[] valueArr = value.split(";");
            // 最前面为cookie
            String cookieContent = valueArr[0];
            String[] cookieArr = cookieContent.split("=");
            // 存入map
            this.cookieMap.put(cookieArr[0], cookieArr[1]);
        }
        // 拼接cookie字符串
        this.cookies = "";
        cookieMap.forEach((k, v) -> this.cookies += k + "=" + v + ";");
        log.info("执行HTTP请求获取的cookie = [{}]", this.cookies);
    }

    /**
     *
     * 根据httpEntity的字符串获取BaseResult(dataJSON)数据
     * baseResultDataJson
     * @param httpEntity
     * @return
     */
    public BaseResult getBaseResultByHttpEntity(String httpEntity){



        JSONObject jsonObject = JSON.parseObject(httpEntity);

        //获取Entity 返回的data数据
        String respData = jsonObject.getString("data");

        if(StringUtil.isEmpty(respData)){

            return BaseResult.success();
        }

        //转换JSON对象获取里面的data字符串
        JSONObject jsonRespData = JSON.parseObject(respData);

        //得到BaseResult的JSON字符串形式的数据
        String resultData = jsonObject.getString("data");
        //获取转换JSON对象 将数据 存放到BaseResult中
        JSONObject baseResultJson = JSON.parseObject(resultData);

        //获取接口的返回code
        int baseResultCode = Integer.valueOf(baseResultJson.get("code").toString());


        if(baseResultCode!=RESULT_CODE_SUCCESS){
            String baseResultMessage = baseResultJson.get("message").toString();
            return BaseResult.failure(baseResultCode,baseResultMessage);
        }

        if (baseResultJson.get("data") != null && !StringUtil.isEmpty(baseResultJson.get("data").toString())) {
            //得到BaseResult里的data的JSON字符串形式的数据
            JSONObject baseResultDataJson = JSON.parseObject(baseResultJson.get("data").toString());

            return    BaseResult.success(JSON.toJSONString(baseResultDataJson));
        }

        return    BaseResult.success();

    }

}
