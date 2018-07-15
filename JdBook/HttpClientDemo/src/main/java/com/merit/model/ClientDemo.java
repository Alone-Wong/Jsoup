package com.merit.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class ClientDemo {
	
	@Test
	public static String send(String url,String encode,Map<String, String> map) throws ClientProtocolException, IOException{
		String body="";
		//创建httpclient
		DefaultHttpClient client = new DefaultHttpClient();
		//创建post请求方式
		HttpPost post = new HttpPost(url);
	    //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        } 
	
        //设置参数到请求对象中
        post.setEntity(new UrlEncodedFormEntity(nvps, encode));
        
        System.out.println("请求地址："+url);  
        System.out.println("请求参数："+nvps.toString());  
        
        //设置报文参数
        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】  
        post.setHeader("Content-type", "application/x-www-form-urlencoded");  
        post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //执行post请求
        CloseableHttpResponse response = client.execute(post);
        //获取结果
        //获取结果实体  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encode);  
        }  
        EntityUtils.consume(entity);  
        //释放链接  
        response.close();  
        return body; 
	}

	  public static void main(String[] args) throws ParseException, IOException {  
        String url="http://php.weather.sina.com.cn/iframe/index/w_cl.php";  
        Map<String, String> map = new HashMap<String, String>();  
        map.put("code", "js");  
        map.put("day", "0");  
        map.put("city", "上海");  
        map.put("dfc", "1");  
        map.put("charset", "utf-8");  
        String body = send(url, "utf-8",map);  
        System.out.println("交易响应结果：");  
        System.out.println(body);  
  
        System.out.println("-----------------------------------");  
  
        map.put("city", "北京");  
        body = send(url, "UTF-8",map);  
        System.out.println("交易响应结果：");  
        System.out.println(body);  
    }
	
	
}
