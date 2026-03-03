package com.yunny.channel.common.util;

import com.alibaba.fastjson.JSONObject;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Slf4j
public class AuthUtil {


    /**
     * get 请求 请求返回一个JSON
     * @param urlStr 参数 拼接好了在传进去 此方法不给拼接参数
     * @return
     */
    public static BaseResult<JSONObject> doGetJson(String urlStr){

        HttpURLConnection connection =null;
        StringBuffer sb = new StringBuffer(urlStr);
        try {
            URL url = new URL(sb.toString());
            connection= (HttpURLConnection) url.openConnection();
            // //设置连接属性
            connection.setRequestMethod("GET");// 设置URL请求方法，默认为“GET”
            connection.setDoOutput(false);// 禁止 URL 连接进行输出，默认为“false”
            connection.setDoInput(true);// 使用 URL 连接进行输入，默认为“true”
            connection.setUseCaches(false);// 忽略缓存
            // 设置 《请求头》信息
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/octet-stream"); //设置的文本类型,此字段必须和和服务器端处理请求流的编码一致,否则无法解析
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("BrokerID", URLEncoder.encode("MFTST0", "utf-8"));

            // 前面的操作只是将“请求头”和“正文”组装成request对象，最后真正以HTTP协议发送数据的是下面的getInputStream();
            // // 获取服务端响应，通过输入流来读取URL的响应
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            /**
             * 校验返回码
             */
            int responseCode = connection.getResponseCode();

//            if(!StringUtil.isEmpty(jsonObject.getString("privilege"))){
//                      数组解析的情况
//                JSONArray objects = JSONArray.parseArray(jsonObject.getString("privilege"));
//                List<String> categoryConstantInfos = objects.toJavaList(String.class);
//                weChatUserInfo.setPrivilege(categoryConstantInfos);
//            }

            if (responseCode!= ExceptionConstants.RESULT_CODE_SUCCESS){
                log.error("网络请求报错了错误码[{}],错误信息[{}]",responseCode,connection.getResponseMessage());
                return BaseResult.failure(responseCode,connection.getResponseMessage());
            }


            /**
             * 获取参数
             *
             */
            StringBuffer sbf= new StringBuffer();
            String strRead = null;
            while ((strRead = responseReader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }


            JSONObject  json = JSONObject.parseObject(sbf.toString());

            responseReader.close();

            return BaseResult.success(json);

        } catch (Exception e) {
            log.error("网络请求报错了：",e);
            e.printStackTrace();
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,e.getMessage());
        }
        finally {
            if (connection!=null) {
                connection.disconnect();
            }
        }

    }

    /**
     * POST 请求 请求返回一个JSON
     * @param urlStr 参数 拼接好了在传进去 此方法不给拼接参数
     * @return
     */
    public static BaseResult<JSONObject>  doPostJson(String urlStr){

        HttpURLConnection connection =null;
        StringBuffer sb = new StringBuffer(urlStr);

        try {

            URL url = new URL(sb.toString());
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(url.openStream()));


            /**
             * 校验返回码
             */
            int responseCode = connection.getResponseCode();

            if (responseCode!=ExceptionConstants.RESULT_CODE_SUCCESS){
                log.error("网络请求报错了错误码[{}],错误信息[{}]",responseCode,connection.getResponseMessage());
                return BaseResult.failure(responseCode,connection.getResponseMessage());
            }

            /**
             * 组装参数
             */
            StringBuffer sbf= new StringBuffer();

            String strRead = null;
            while ((strRead = responseReader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }

            JSONObject  json = JSONObject.parseObject(sbf.toString());
            responseReader.close();

            return BaseResult.success(json);

        }catch (Exception e) {

                log.error("网络请求报错了：",e);
                e.printStackTrace();
                return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,e.getMessage());
            }  finally {

                if (connection!=null) {
                    connection.disconnect();
                }
            }

    }


    /**
     * POST请求 返回一个JSON
     * @param urlStr
     * @param param
     * @return
     */
    public static BaseResult<JSONObject>  doPostJsonByBody(String urlStr,JSONObject param){
        HttpURLConnection connection =null;
        StringBuffer sb = new StringBuffer(urlStr);

        try {

            URL url = new URL(sb.toString());
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);  // Post请求不能使用缓存
            connection.setDoInput(true);       // 设置是否从HttpURLConnection输入，默认值为 true
            connection.setDoOutput(true);      // 设置是否使用HttpURLConnection进行输出，默认值为 false

            //设置header内的参数 connection.setRequestProperty("健, "值");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("isTree", "true");
            connection.setRequestProperty("isLastPage", "true");

            connection.connect();

            // 得到请求的输出流对象
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");

            writer.write(param.toString());
            writer.flush();

            // 获取服务端响应，通过输入流来读取URL的响应
            InputStream is = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            /**
             * 校验返回码
             */
            int responseCode = connection.getResponseCode();

            if (responseCode!=ExceptionConstants.RESULT_CODE_SUCCESS){
                log.error("网络请求报错了错误码[{}],错误信息[{}]",responseCode,connection.getResponseMessage());
                return BaseResult.failure(responseCode,connection.getResponseMessage());
            }

            /**
             * 组装参数
             */
            StringBuffer sbf= new StringBuffer();

            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }

            JSONObject  json = JSONObject.parseObject(sbf.toString());
            reader.close();

            return BaseResult.success(json);


        }catch (Exception e) {
            log.error("网络请求报错了：",e);
            e.printStackTrace();
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,e.getMessage());
        }  finally {
            if (connection!=null) {
                connection.disconnect();
            }
        }

    }

}
