package com.yunny.channel.sms.service.impl;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.AliyunSendMsgVO;
import com.yunny.channel.sms.config.AliyunSmsConfig;
import com.yunny.channel.sms.config.LeXinConfig;
import com.yunny.channel.sms.service.ShortMessageSend;
import com.yunny.channel.util.JsonStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr. Du
 * @explain 短信发送乐信的实现
 * @createTime 2019/12/20 16:36
 * @motto The more learn, the more found his ignorance.
 */
@Service
@Slf4j
public class ShortMessageSendImpl implements ShortMessageSend {


    @Autowired
    private AliyunSmsConfig aliyunSmsConfig;

    @Override
    public Map sendShortMessage(String mobile, String content) {

        //Map map = Maps.newHashMap();
        Map map = new HashMap();
        HttpURLConnection connection =null;
        StringBuffer sb = new StringBuffer("http://sdk.lx198.com/sdk/send?");
        try {
            content = content+"【翱游科技】";
            sb.append("&accName="+ LeXinConfig.build().getAccName());
            sb.append("&accPwd="+LeXinConfig.build().getAccPwd());
            sb.append("&aimcodes="+mobile);
            sb.append("&content="+ URLEncoder.encode(content,"UTF-8"));
            sb.append("&dataType=string");
            URL url = new URL(sb.toString());
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String result[] = in.readLine().split(";");

            if(result[0].equals("1")){
                map.put("code","0");
                map.put("msg","发送成功");
            }else{
                map.put("code","1");
                map.put("msg",result[1]);
                log.info(result[1]);
            }

            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("code","1");
            map.put("msg","发送失败");

            return map;
        }     finally {
            if (connection!=null) {
                connection.disconnect();
            }
        }

    }


    /**
     * sunfuwei
     * @param phone        需要发送的电话号码，支持多个电话号码 格式为"13600000000,15000000000"
     *                     支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
     * 文档地址 https://help.aliyun.com/document_detail/55289.html?spm=a2c4g.11186623.6.668.5549bc45bd7Igy
     * @param templateParam 模板内需要填充的字段及字段值 格式为("{\"name\":\"Tom\", \"code\":\"123\"}")

     * @return
     */
    @Override
    public BaseResult<AliyunSendMsgVO> aliyunSendMsg(String phone, String templateParam) {

        log.info("获取配置文件的:RegionId:[{}],AccessKeyId:[{}],AccessKeySecret:[{}],templateCode,[{}]",
                aliyunSmsConfig.getRegionId(), aliyunSmsConfig.getAccessKeyId(),aliyunSmsConfig.getAccessKeySecret(),aliyunSmsConfig.getTemplateCode());

        //初始化ascClient,暂时不支持多region（请勿修改）
        DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsConfig.getRegionId(), aliyunSmsConfig.getAccessKeyId(),aliyunSmsConfig.getAccessKeySecret());

        IAcsClient client =new DefaultAcsClient(profile);

        //参数初始化
        CommonRequest request = this.buildCommonRequest( phone,templateParam);

        try {

            log.info("调用阿里云短信服务请求 phone={}，templateCode={},templateParam={}", phone, templateParam);

            CommonResponse response = client.getCommonResponse(request);

            // 把String 转换为map
            Map<String, String> map = JsonStringUtil.toMapString(response.getData());
            AliyunSendMsgVO vo = new AliyunSendMsgVO();
            vo.setResultCode(map.get("Code"));
            vo.setResultBody(map.get("Message"));

            if ("OK".equals(map.get("Code"))) {
                vo.setBizId(map.get("BizId"));
                vo.setRequestId(map.get("RequestId"));
                return BaseResult.success(vo);
            }else{
                return  BaseResult.failure(10210000,"阿里云短信发送出现异常",vo);

            }

        }catch (ServerException e) {

            AliyunSendMsgVO vo = new AliyunSendMsgVO();
            vo.setResultCode(e.getMessage());
            vo.setResultBody("阿里云短信服务异常");
            log.error("阿里云短信服务异常:{}", e);
            return  BaseResult.failure(10210000,"阿里云短信服务异常",vo);


        }catch (ClientException e) {

            AliyunSendMsgVO vo = new AliyunSendMsgVO();
            vo.setResultCode(e.getMessage());
            vo.setResultBody("阿里云短信服务异常");
            log.error("连接阿里云短信异常:{}", e);

            return  BaseResult.failure(10210000,"连接阿里云短信异常",vo);

        }catch (Exception e) {

            AliyunSendMsgVO vo = new AliyunSendMsgVO();
            vo.setResultCode(e.getMessage());
            vo.setResultBody("阿里云短信服务异常");
            log.error("json转换异常:{}", e);
            return  BaseResult.failure(10210000,"阿里短信发送出现异常",vo);

        }


    }


     private CommonRequest buildCommonRequest(String phone,String templateParam){

         CommonRequest request =new CommonRequest();


         request.setSysMethod(MethodType.POST);
         request.setSysDomain(aliyunSmsConfig.getDomain());
         request.setSysVersion(aliyunSmsConfig.getVersion());// 版本信息  已经固定  不能进行更改
         request.setSysAction("SendSms");//系统规定参数 SendSms 发送短信动作


         request.putQueryParameter("RegionId", aliyunSmsConfig.getRegionId());
         request.putQueryParameter("SignName", aliyunSmsConfig.getSignName()); //阿里云控制台签名
         request.putQueryParameter("TemplateCode", aliyunSmsConfig.getTemplateCode()); //短信模板ID
         request.putQueryParameter("PhoneNumbers", phone);//用户手机号 多个逗号分隔批量上限为1000个手机号码,
         request.putQueryParameter("TemplateParam", templateParam); //短信内容 name code JSON字符串


          //配置连接超时时间
          //request.setSysConnectTimeout();

         log.info(request.toString());
         return request;

     }


}

