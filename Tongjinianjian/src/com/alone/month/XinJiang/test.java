package com.alone.month.XinJiang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class test {

	@Test
    public String testHttpClientPost() throws IOException {
        //定义uri
        String uri="http://www.xjtj.gov.cn/sjcx/ydsj_3329/ydsj.html";
        //需要传入的参数
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "js");
        map.put("day", "0");
        map.put("city", "上海");
        map.put("dfc", "1");
        map.put("charset", "utf-8");
        String encoding = "utf-8";
        //创建默认的httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post请求对象
        HttpPost httpPost = new HttpPost(uri);
        //装填请求参数
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
//        }
        //设置参数到请求对象中
       // httpPost.setEntity(new UrlEncodedFormEntity(list,encoding));

        //System.out.println("请求地址："+uri);
  //      System.out.println("请求参数："+list.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取所有的请求头信息
        Header[] allHeaders = response.getAllHeaders();
        for (Header allHeader : allHeaders) {
            System.out.println(allHeader.toString());
        }
        //获取结果实体
        HttpEntity entity = response.getEntity();
        
        String responseBody="";
        
        if (entity != null) {
            System.out.println(EntityUtils.toString(entity,encoding));
             responseBody = EntityUtils.toString(entity,encoding);
        }
        //关流
        EntityUtils.consume(entity);
        response.close();
        return responseBody;
    }
	
	
}
