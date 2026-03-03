package com.yunny.channel.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.httpClient.LoginServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yunny.channel.common.constant.ExceptionConstants.RESULT_CODE_ERROR;

@Slf4j
@RestController
@RequestMapping("/httpClient")
public class HttpClientController {

    @Autowired
    LoginServerUtil loginServerUtil;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 使用 httpClient 方法执行PostJson请求服务接口
     * @return
     */
    @PostMapping("/testHttpClientPostJsonBody")
    public BaseResult testHttpClientPostJsonBody() {
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("loginHostPath", "http://192.168.0.9:8081");
        return loginServerUtil.login(paraMap);
    }

    /**
     * 使用 httpclient 请求表单传参形式
     * 添加用户信息.
     * @return BaseResult
     */
    @PostMapping("/updateByModelAttribute")
    public BaseResult updateByModelAttribute() {

        HttpClient httpclient = HttpClientBuilder.create().build();

        RequestConfig requestConfig =  RequestConfig.custom()
                .setSocketTimeout(60*1000)//设置数据传输的最长时间，单位是毫秒；
                .setConnectTimeout(60*1000)//设置创建连接的最长时间，单位是毫秒；
                .build();

        String hostIp ="http://192.168.0.9:8081";
        String url =  String.format("%s%s",hostIp,"/userInfo/updateByModelAttribute");
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);  //设置配置

        httppost.addHeader("token","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiJ1MjAyMDA0MjcwMDAwMDEiLCJ1c2VySWQiOiIxMCIsInNhbHQiOiJ6aG9uZ2hvbmciLCJleHAiOjE2OTk1NDA4MzB9.CowniqXwBCdaJN5P9lh4UIO9Su9Gi_rUknk5uu5RMg0");

        //使用NameValuePair集合传递参数 这个是表单传递对象
        List<NameValuePair> nameValuePairs = new ArrayList<>(8);
        nameValuePairs.add(new BasicNameValuePair("id", "74"));
        nameValuePairs.add(new BasicNameValuePair("userNo", "1128"));
        nameValuePairs.add(new BasicNameValuePair("userName", "测试名称123"));
        nameValuePairs.add(new BasicNameValuePair("userNickName", "测试昵称456"));
        nameValuePairs.add(new BasicNameValuePair("password", "123456"));
        nameValuePairs.add(new BasicNameValuePair("mobile", "18158516826"));
        nameValuePairs.add(new BasicNameValuePair("userPicture", "hehehe"));
        nameValuePairs.add(new BasicNameValuePair("userEmail", "sunfuwei@521.com"));

        try {
            //写入表单参数值 编码"utf-8" 防止中文乱码
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            //执行请求
            HttpResponse response = httpclient.execute(httppost);
            //获取返回值
            BaseResult result =  loginServerUtil.getBaseResultByHttpEntity(EntityUtils.toString(response.getEntity()));
            log.info("result===========[{}]",result);
            return result;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return BaseResult.failure(RESULT_CODE_ERROR,"使用 httpclient 请求表单传参形式失败");
    }


    /**
     * 使用 HttpURLConnection 方法执行PostJson请求服务接口
     * @return
     */
    @PostMapping("/testHttpURLConnection")
    public BaseResult testHttpURLConnection() {
        // request
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        DataOutputStream dataOutputStream = null;
        try {

            URL url = new URL("http://192.168.0.9:8081/user/listByQuery");

            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            //是否使用缓存
            //connection.setUseCaches(true);
            connection.setRequestMethod("POST");//请求方式POST
            //JSON body传参
            connection.setRequestProperty("Content-Type", "application/json");
            //headers token参数
            connection.setRequestProperty("token","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiJ1MjAyMDA0MjcwMDAwMDEiLCJ1c2VySWQiOiIxMCIsInNhbHQiOiJ6aG9uZ2hvbmciLCJleHAiOjE2OTk1NDA4MzB9.CowniqXwBCdaJN5P9lh4UIO9Su9Gi_rUknk5uu5RMg0");

            //body json 参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currentPage","1");
            jsonObject.put("pageSize","2");
            String param =  jsonObject.toJSONString();

            //执行链接
            connection.connect();

            // 传递参数  流的方式
            dataOutputStream= new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(param.getBytes("UTF-8"));
            dataOutputStream.flush();
            dataOutputStream.close();

            int statusCode = connection.getResponseCode();
            // 读取数据
            InputStream inputStream = statusCode == 200 ? connection.getInputStream() : connection.getErrorStream();
            reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            inputStream.close();
            connection.disconnect();

            log.info("请求返回的response----信息:[{}]",response.toString());
            BaseResult result = JSON.parseObject(response.toString(),BaseResult.class);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭所有通道
        this.closeThoroughfare(connection,reader,dataOutputStream);

        }
            return BaseResult.success();
    }


    /**
     * 关闭所有通道
     * @param connection
     * @param reader
     * @param dataOutputStream
     */
    private void closeThoroughfare(HttpURLConnection connection,BufferedReader reader ,DataOutputStream dataOutputStream){

        // 关闭所有通道
        try{
            if(reader!=null){
                reader.close();
            }
            if(dataOutputStream!=null){
                dataOutputStream.close();
            }
            if(connection!=null){
                connection.disconnect();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();

        }
    }


}
