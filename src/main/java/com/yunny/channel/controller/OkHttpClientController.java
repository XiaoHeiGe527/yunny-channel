package com.yunny.channel.controller;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.dto.UserInfoDTO;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.InitResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j @RestController @RequestMapping("/okHttp") public class OkHttpClientController {

    private ObjectMapper mapper = new ObjectMapper();

    public String sendRequest(String url) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Objects.requireNonNull(response.body()).string();
        }
    }

    /**
     * 使用 okHttpClient 方法执行Get请求服务接口
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/testOkHttpClientGet") public String testOkHttpClientGet() throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        String param = "?id=62";
        String uri = "http://192.168.0.9:8081/userInfo/getById";
        Request request = new Request.Builder().url(uri + param).addHeader("token",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiJ1MjAyMDA0MjcwMDAwMDEiLCJ1c2VySWQiOiIxMCIsInNhbHQiOiJ6aG9uZ2hvbmciLCJleHAiOjE2OTk1NDA4MzB9.CowniqXwBCdaJN5P9lh4UIO9Su9Gi_rUknk5uu5RMg0")
            .get().build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 使用OKhttp请求json body形式
     * 添加用户信息.
     *
     * @return 是否成功
     */
    @PostMapping("/userInfoCreate") public BaseResult userInfoCreate() {

        String url = "http://192.168.0.9:8081" + "/userInfo/create";
        UserInfoDTO userInfoDto =
            UserInfoDTO.builder().userNo("1128").userName("test123").userNickName("test456").password("123456")
                .mobile("18158516826").userPicture("hehehe").userEmail("sunfuwei@521.com").build();
        //使用OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
            .build();

        //请求方式为 json body形式
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        //添加RequestBody JSON参数
        String userInfoDtoStr = JSONObject.toJSONString(userInfoDto);
        RequestBody requestBody = RequestBody.create(mediaType, userInfoDtoStr);

        try {
            Request request = new Request.Builder().url(url).post(requestBody) //post请求
                .addHeader("token",
                    "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiJ1MjAyMDA0MjcwMDAwMDEiLCJ1c2VySWQiOiIxMCIsInNhbHQiOiJ6aG9uZ2hvbmciLCJleHAiOjE2OTk1NDA4MzB9.CowniqXwBCdaJN5P9lh4UIO9Su9Gi_rUknk5uu5RMg0")
                .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();

                JSONObject responseStrJson = JSON.parseObject(responseStr);

                BaseResult baseResult = JSON.parseObject(JSONObject.toJSONString(responseStrJson), BaseResult.class);

                log.info("服务返回的baseResult的内容是：[{}]", baseResult);

                return baseResult;
            }

        } catch (IOException e) {
            log.debug("处理添加用户信息失败");
            e.printStackTrace();
        }

        return BaseResult.success();
    }

    /**
     * 实现文件上传
     *
     * @throws IOException
     */
    public void testUpload() throws IOException {
        //使用OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
            .build();

        String hostStr = "http://192.168.0.9:8081";
        String api = "/api/files/1";
        String url = String.format("%s%s", hostStr, api);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", "docker_practice.pdf", RequestBody.create(MediaType.parse("multipart/form-data"),
                new File("C:/Users/hetiantian/Desktop/学习/docker_practice.pdf"))).build();
        Request request = new Request.Builder().url(url).post(requestBody)  //默认为GET请求，可以不写
            .build();
        final Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

    /**
     * 处理图片请求
     *
     * @param photo
     * @return
     */
    public String getFaceId(byte[] photo) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // MediaType.parse() 里面是上传的文件类型。
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), photo);
        // 参数分别为， 请求key ，文件名称 ， RequestBody
        requestBody.addFormDataPart("file", "123.bmp", body);
        Request request = new Request.Builder().url("127.0.0.1" + "/faces/detection").post(requestBody.build())
            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
            .addHeader("Cache-Control", "no-cache").addHeader("Postman-Token", "eab19d6a-bc6e-3137-930e-919913e3b4b1")
            .build();

        Response response;
        InitResponse param = null;
        String saveFileName = Long.toString(new Date().getTime());
        try {
            response = client.newCall(request).execute();
            param = mapper.readValue(response.body().string(), InitResponse.class);

            if (param.getStatus() instanceof String && "ok"
                .equals(param.getStatus() == null ? "" : param.getStatus().toString())) {
                return param.getFaces()[0].getFace_id();
            } else {
                log.debug("处理图片失败" + param.getStatus());
            }
        } catch (IOException e) {
            log.debug("处理图片失败");
            e.printStackTrace();
        }
        return null;
    }

}
