package com.yunny.channel.common.util.httpClient;

import com.alibaba.fastjson.JSONObject;
import com.yunny.channel.common.constant.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
* @Description ica服务工具类 prototype多例模式,这里不能作为单例，不可共享属性
* @Author hex
* @CreateDate 2019/11/28 16:14
*/
@Slf4j
@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode= ScopedProxyMode.TARGET_CLASS)
public class IcaServerUtil {

	/** csrf验证名 */
	private static String CSRF_TOKEN = "CsrfToken";

	/** 域名 作为配置项 */
	//@Value("${gvdi.host.path}")
	private String hostPath;

	/** 服务名 作为配置项 */
	//@Value("${gvdi.web.name}")
	private String webName;

	/** csrf验证 */
	private String token;

	/** 获取ica地址 */
	private String url;

	/** cookie值字符串 */
	private String cookies;

	/** 返回对象 */
	private Map resultMap = new HashMap(4);

	/** cookie键值对 */
	private Map<String,String> cookieMap = new HashMap<>();

	/** http客户端 */
	private CloseableHttpClient httpClient;

	{
		this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
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

	/** 登录路径 */
	private static final String LOGIN_PATH = "/cgi/login";

	/** 配置路径 */
	private static final String CONFIGURATION_PATH = "Home/Configuration";

	/** 获取权限路径 */
	private static final String AUTH_METHODS_PATH = "Authentication/GetAuthMethods";

	/** 网关登录路径 */
	private static final String GATEWAY_LOGIN_PATH = "GatewayAuth/Login";

	/** 资源列表路径 */
	private static final String RESOURCE_LIST_PATH = "Resources/List";

	private static final String FILE_SUFFIX = "ica";

	private static final String CONTENT_TYPE_ICA = "application/x-ica";

	/**
	 * 登录
	 * @param paramMap 主要是登录名和密码
	 * @return
	 */
	public Map login(Map<String,String> paramMap) {

		this.hostPath = paramMap.get("gvdiHostPath");
		this.webName = paramMap.get("gvdiWebName");

		//log.info("hostPath:[{}],webName[{}]",hostPath,webName);

		// 创建POST请求对象
		HttpPost httpPost = new HttpPost(hostPath + LOGIN_PATH);

		this.resultMap.put("url",hostPath + LOGIN_PATH);


		log.info("请求gvdi地址：[{}],hostPath:[{}],LOGIN_PATH:[{}]",hostPath + LOGIN_PATH,hostPath,LOGIN_PATH);
		/**
		 * 添加请求头信息
		 */
		httpPost.addHeader("cache-control", "no-cache");
		httpPost.addHeader("Referer", hostPath + LOGIN_PATH);
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept", "*/*");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		// 请求参数初始化
		List<NameValuePair> list = new LinkedList<>();
		// 参数添加到请求参数
		paramMap.forEach((k,v)-> list.add(new BasicNameValuePair(k,v)));
		CloseableHttpResponse response = null;
		try{
			// 使用URL实体转换工具
			if(list.size() != 0){
				UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
				httpPost.setEntity(entityParam);
			}
			// 执行请求
			response = this.httpClient.execute(httpPost);
			this.resultMap.put("message",response.getStatusLine().getReasonPhrase());

			// 校验返回状态
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY || response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				/** cookie处理 */
				getCookieFromResponse(response);
				response.getEntity().getContent().close();
				/** 重定向处理 */
				redirect(response);
				// 重定向失败返回
				if(HttpStatus.SC_OK == (int) this.resultMap.get("code")){
					/** 执行下一步配置 */
					configuration();
				}
			} else{
				log.error("模拟gvdi登录失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("message",response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (IOException e){
			log.error("模拟gvdi登录失败，错误原因为：",e);
			this.resultMap.put("message",e.getMessage());
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi登录response失败，错误原因为：",e);
				}
			}
		}
		return this.resultMap;
	}

	/**
	 * 配置
	 */
	public void configuration() {
		// 创建POST请求对象
		HttpPost httpPost = new HttpPost(hostPath + webName + CONFIGURATION_PATH);
		/**
		 * 添加请求头信息
		 */
		httpPost.addHeader("Cookie", this.cookies);
		httpPost.addHeader("cache-control", "no-cache");
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("X-Citrix-IsUsingHTTPS", "Yes");
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			/** cookie处理 */
			getCookieFromResponse(response);
			// 校验返回状态
			System.out.println(response.getStatusLine().getStatusCode()+"--------");
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				response.getEntity().getContent().close();
				/** 获取权限 */
				getAuthMethods();
			} else{
				log.error("模拟gvdi配置失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (IOException e){
			log.error("模拟gvdi配置失败，错误原因为：",e);
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi配置response失败，错误原因为：",e);
				}
			}
		}

	}

	/**
	 * 获取权限
	 */
	public void getAuthMethods() {
		// 创建POST请求对象
		HttpPost httpPost = new HttpPost(hostPath + webName + AUTH_METHODS_PATH);
		/** 获取cookie里的csrf token */
		this.cookieMap.forEach((k,v) -> {
			if(CSRF_TOKEN.equals(k)){
				this.token = v;
				log.info("token = [{}]", this.token);
			}
		});
		/**
		 * 添加请求头信息
		 */
		httpPost.addHeader("Cookie", this.cookies);
		httpPost.addHeader("cache-control", "no-cache");
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Csrf-Token", this.token);
		httpPost.addHeader("X-Citrix-IsUsingHTTPS", "Yes");
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			/** cookie处理 */
			getCookieFromResponse(response);
			// 校验返回状态
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				response.getEntity().getContent().close();
				/** 网关登录 */
				gatewayLogin();
			} else{
				log.error("模拟gvdi获取权限失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (IOException e){
			log.error("模拟gvdi获取权限失败，错误原因为：",e);
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi获取权限response失败，错误原因为：",e);
				}
			}
		}

	}

	/**
	 * 网关登录
	 */
	public void gatewayLogin() {
		// 创建POST请求对象
		HttpPost httpPost = new HttpPost(hostPath + webName + GATEWAY_LOGIN_PATH);
		/**
		 * 添加请求头信息
		 */
		httpPost.addHeader("Cookie", this.cookies);
		httpPost.addHeader("cache-control", "no-cache");
		httpPost.addHeader("Csrf-Token", this.token);
		httpPost.addHeader("X-Citrix-IsUsingHTTPS", "Yes");
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			/** cookie处理 */
			getCookieFromResponse(response);
			// 校验返回状态
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				response.getEntity().getContent().close();
				/** 资源列表 */
				resourcesList();
			} else{
				log.error("模拟gvdi网关登录失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (IOException e){
			log.error("模拟gvdi网关登录失败，错误原因为：",e);
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi网关登录response失败，错误原因为：",e);
				}
			}
		}

	}

	/**
	 * 资源列表
	 */
	public void resourcesList() {
		// 创建POST请求对象
		HttpPost httpPost = new HttpPost(hostPath + webName + RESOURCE_LIST_PATH);
		/**
		 * 添加请求头信息
		 */
		httpPost.addHeader("Cookie", this.cookies);
		httpPost.addHeader("cache-control", "no-cache");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.addHeader("Csrf-Token", this.token);
		httpPost.addHeader("X-Citrix-IsUsingHTTPS", "Yes");
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
		/**
		 * 添加请求参数
		 */
		List<NameValuePair> list = new LinkedList<>();
		list.add(new BasicNameValuePair("format","json"));
		list.add(new BasicNameValuePair("resourceDetails","Default"));
		CloseableHttpResponse response = null;
		try {
			// 使用URL实体转换工具
			if(list.size() != 0){
				UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
				httpPost.setEntity(entityParam);
			}
			// 执行请求
			response = httpClient.execute(httpPost);
			// cookie处理
			getCookieFromResponse(response);
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, "UTF-8");
					JSONObject jsonObject = JSONObject.parseObject(result);
					this.url = (String)((Map)((List)jsonObject.get("resources")).get(0)).get("launchurl");
					if(StringUtils.isBlank(this.url)){
						log.error("模拟gvdi获取资源列表失败，返回路径为空");
						this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
					} else{
						response.getEntity().getContent().close();
						/** 下载 */
						download();
					}
				} else{
					log.error("模拟gvdi获取资源列表失败，返回信息为空");
					this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
				}
			} else{
				log.error("模拟gvdi获取资源列表失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			log.error("模拟gvdi获取资源列表失败，错误原因为：",e);
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi获取资源列表response失败，错误原因为：",e);
				}
			}
		}
	}

	/**
	 * 下载打开
	 */
	public void download() {
		// 创建POST请求对象
		HttpGet httpGet = new HttpGet(hostPath + webName + this.url + "?CsrfToken=" + this.token + "&IsUsingHttps=Yes&displayNameDesktopTitle=mt&launchId=");
		/*
		 * 添加请求头信息
		 */
		httpGet.addHeader("Cookie", this.cookies);
		httpGet.addHeader("cache-control", "no-cache");
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpGet.addHeader("Csrf-Token", this.token);
		httpGet.addHeader("X-Citrix-IsUsingHTTPS", "Yes");
		httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);

			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					String fileName = this.url.split("/")[2];
					if(StringUtils.isBlank(fileName)){
						log.info("模拟gvdi下载ica失败，文件名为空");
						this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
					} else{
						if(!FILE_SUFFIX.equals(fileName.substring(fileName.lastIndexOf(".") + 1))){
							log.info("模拟gvdi下载ica失败，文件后缀有误");
							this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
						} else{
							Header[] contentTypeHeaders = response.getHeaders("content-type");
							if(null == contentTypeHeaders || contentTypeHeaders.length == 0){
								log.error("模拟gvdi下载ica失败，response返回类型为空");
								this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
							} else{
								Header contentTypeHeader = contentTypeHeaders[0];
								String value = contentTypeHeader.getValue();
								if(StringUtils.isBlank(value)){
									log.error("模拟gvdi下载ica失败，response返回类型值为空");
									this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
								} else{
									String[] valueArr = value.split(";");
									// 最前面为type
									String contentType = valueArr[0];
									String content = EntityUtils.toString(entity, "UTF-8");
									log.info("ica内容为：[{}]",content);
									if(CONTENT_TYPE_ICA.equals(contentType)){
										this.resultMap.put("code",HttpStatus.SC_OK);
										this.resultMap.put("message","true");
										this.resultMap.put("fileName",fileName);
										this.resultMap.put("content",content);
									}else{
										log.error("模拟gvdi下载ica失败，内容不合法");
										this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
									}
								}
							}
						}
					}
				} else{
					log.error("模拟gvdi下载ica失败，返回信息为空");
					this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
				}
			} else{
				log.error("模拟gvdi下载ica失败，返回码为[{}]，返回信息为[{}]",response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
				this.resultMap.put("code",response.getStatusLine().getStatusCode());
			}
		} catch (IOException e){
			log.error("模拟gvdi下载ica失败，错误原因为：",e);
			this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
		} finally {
			// 关闭连接
			if(null != response){
				try {
					response.close();
				} catch (IOException e){
					log.error("释放模拟gvdi下载ica的response失败，错误原因为：",e);
				}
			}
		}
	}

	/**
	 * 跳转
	 * @param response
	 */
	public void redirect(CloseableHttpResponse response) {
		// 获取重定向地址
		Header[] locationHeaders = response.getHeaders("location");
		if(null != locationHeaders && locationHeaders.length == 1){
			Header locationHeader = locationHeaders[0];
			String location = locationHeader.getValue();
			// 创建POST请求对象
			HttpPost httpPost = new HttpPost(hostPath + location);
			httpPost.addHeader("Cookie", this.cookies);
			httpPost.addHeader("cache-control", "no-cache");
			httpPost.addHeader("Accept-Encoding", "gzip, deflate");
			httpPost.addHeader("Accept", "*/*");
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			CloseableHttpResponse redirectResponse = null;
			try {
				// 执行请求
				redirectResponse = httpClient.execute(httpPost);
				// 是否成功
				if(redirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					/** 处理cookie */
					getCookieFromResponse(redirectResponse);
					this.resultMap.put("code",HttpStatus.SC_OK);
				} else{
					log.error("模拟gvdi登录重定向失败，返回码为[{}]，返回信息为[{}]",redirectResponse.getStatusLine().getStatusCode(),redirectResponse.getStatusLine().getReasonPhrase());
					this.resultMap.put("code",redirectResponse.getStatusLine().getStatusCode());
				}
			} catch (IOException e){
				log.error("模拟gvdi登录重定向失败，错误原因为：",e);
				this.resultMap.put("code", ExceptionConstants.ICA_SERVER_FAIL);
			} finally {
				// 关闭连接
				if(null != redirectResponse){
					try {
						redirectResponse.close();
					} catch (IOException e){
						log.error("释放模拟gvdi登录重定向response失败，错误原因为：",e);
					}
				}
			}
		}
	}

	/**
	 * 处理cookies
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
		cookieMap.forEach((k,v) -> this.cookies += k + "=" + v + ";");
		log.info("cookie = [{}]", this.cookies);
	}
}
