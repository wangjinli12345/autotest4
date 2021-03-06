package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private String url;
    //用来读取配置文件的
    private ResourceBundle bundle;
    //用来存储cookies信息的变量
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url =bundle.getString("test.url");
    }
    @Test
    public  void testGetCookies() throws IOException {

        String result;
        //从配置文件中拼接测试的url
        String uri =bundle.getString("getCookies.uri");
        String testUrl =this.url+uri;

        //测试逻辑代码书写
        HttpGet get = new HttpGet(testUrl);
        //HttpClient不能获取cookies信息，所以要换成DefaultHttpClient
        DefaultHttpClient client =new DefaultHttpClient();
        HttpResponse response =client.execute(get);
        //把返回的结果转换成字符串形式
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息
        this.store=client.getCookieStore();
        //getCookies会返回一个cookielist,因为cookie有很多值
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie:cookieList){
            String name =cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name ="+ name+";cookie value="+ value);
        }
    }
    //依赖测试
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String uri =bundle.getString("test.get.with.cookies");
        String testUrl =this.url+uri;
        HttpGet get =new HttpGet(testUrl);
        DefaultHttpClient client=new DefaultHttpClient();


        //设置cookies信息
        client.setCookieStore(this.store);
        HttpResponse response=client.execute(get);
        //获取响应的状态码
        int statusCode =response.getStatusLine().getStatusCode();
        System.out.println("statuscode="+statusCode);
        if (statusCode==200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
