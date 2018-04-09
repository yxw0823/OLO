package com.fh.controller.olo.olowebInterface.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

@Controller
@RequestMapping(value = "/appvideo")
public class VideoToken extends BaseController {
	  /**
     * 获取二维码
     */
    @RequestMapping(value = "/getVideoToken")
    @ResponseBody
    public void getVideoToken(HttpServletResponse response) {
    			String result1 = "00";
        		Map<String, Object> map = new HashMap<String, Object>();
        		 map.put("result", result1);
    			PageData pd = new PageData();
    	  		pd = this.getPageData();
    			String vid = pd.getString("vid");
    			if(StringUtils.isEmpty(vid)) {
    				map.put("msg", "vid是毕传字段！");
    				this.responseJson(response, map);
    			}
    		 	String polyvUserId = vid.substring(0, 10);	// polyv 提供的服务器间的通讯验证
    	    	String secretkey = "bH6gMpUM9t";	// polyv 提供的接口调用签名访问的key
    	    	String videoId = vid;						// 视频对应vid
    	    	long ts = System.currentTimeMillis();		// 时间戳
    	    	String viewerIp = "127.0.0.1";						// 用户 ip
    	    	String viewerId = "12.0.0.1";			// 自定义用户 id
    	    	String viewerName = "123";		// 用户昵称
    	    	String extraParams = "HTML5";					// 自定义参数

    	    	/* 将参数 $userId、$secretkey、$videoId、$ts、$viewerIp、$viewerIp、$viewerId、$viewerName、$extraParams
    	    	    按照ASCKII升序 key + value + key + value ... +value 拼接
    	    	*/

    	    	try {
    				viewerName = URLEncoder.encode(viewerName, "utf8");
    			} catch (UnsupportedEncodingException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}

    	    	String concated =  "extraParams" + extraParams + "ts" + ts + "userId" + polyvUserId + "videoId"
    	    			+ videoId + "viewerId" + viewerId + "viewerIp" + viewerIp + "viewerName" + viewerName;

    	    	// 再首尾加上 secretkey
    	    	String plain = secretkey + concated + secretkey;
    	    	System.out.println(plain);
    	    	// 取大写MD5
    	    	String sign =MD5.md5(plain).toUpperCase();
    	    	// 然后将下列参数用post请求  https://hls.videocc.net/service/v1/token 获取 token
    	    	String url = "https://hls.videocc.net/service/v1/token";
    	    	String param = "userId="+polyvUserId+"&videoId="+videoId+"&ts="+ts+"&viewerIp="+viewerIp+"&viewerName="+viewerName+"&extraParams="+extraParams+"&viewerId="+viewerId+"&sign="+sign;
    	    	//logger.info("{}", url + "?" + params);
    	    	System.out.println(	param);
    	    	String result ="";
    			try {
    				result = httpRequst(url, "POST", param);
    				ObjectMapper objectMapper = new ObjectMapper(); // jackson包
    		    	// 获取返回结果的 token, 再传入 playsafe 中播放加密视频
    		    	Map<String, Object> data = new HashMap<String, Object>();
    		    	String token = "";
    		    	data = objectMapper.readValue(result, Map.class);
    		    	result1 ="01";
    		    	token = ((Map) data.get("data")).get("token").toString();
    		    	map.put("data", result);
    		    	map.put("token", token);
    		    	this.responseJson(response, map);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} finally {
                    map.put("result", result);
                    logAfter(logger);
                }
    			this.responseJson(response, map);
    	//return token;
    }
    public static String httpRequst(String requestUrl,String requetMethod,String outputStr){
		String jsonobject=null;
		StringBuffer buffer=new StringBuffer();
		try
		{
			//创建SSLContext对象，并使用我们指定的新人管理器初始化
			TrustManager[] tm={new MyX509TrustManager()};
			SSLContext sslcontext=SSLContext.getInstance("SSL","SunJSSE");
			sslcontext.init(null, tm, new java.security.SecureRandom());
			//从上述SSLContext对象中得到SSLSocktFactory对象
			SSLSocketFactory ssf=sslcontext.getSocketFactory();
			
			URL url=new URL(requestUrl);
			HttpsURLConnection httpUrlConn=(HttpsURLConnection)url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			//设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requetMethod);
			
			if("GET".equalsIgnoreCase(requetMethod))
				httpUrlConn.connect();
			
			//当有数据需要提交时
			if(null!=outputStr)
			{
			OutputStream outputStream=httpUrlConn.getOutputStream();
			//注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
			}
			int code = httpUrlConn.getResponseCode();
			System.out.println(code);
			//将返回的输入流转换成字符串
			InputStream inputStream=httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			
			
			String str=null;
			while((str = bufferedReader.readLine()) !=null)
			{ 
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			httpUrlConn.disconnect();
			jsonobject=buffer.toString();
		}
		catch (ConnectException ce) {
			// TODO: handle exception
		}
		catch (Exception e) {  
		}
		return jsonobject;
	}
	//================================================获取access_token==============================================================
}


//================================================获取access_token==============================================================
 class MyX509TrustManager implements X509TrustManager
{

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
