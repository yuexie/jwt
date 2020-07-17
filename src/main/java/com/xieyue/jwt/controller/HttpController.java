package com.xieyue.jwt.controller;

import com.alibaba.fastjson.JSON;
import com.xieyue.jwt.utils.HttpClientUtil;
import com.xieyue.jwt.utils.IpUtil;
import com.xieyue.jwt.utils.Result;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   HttpController
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date:         2020-07-17 21:47
 */
@RestController
@RequestMapping("http")
public class HttpController {

    @GetMapping("post")
    public String postTest() throws Exception{
        //String httpUrl = "http://192.168.60.50:8060/device/testPost";
        //Map<String, Object> postParam = new HashMap<>();
        //postParam.put("testKey", "test-post");
        //String data = JSON.toJSONString(postParam);

        String httpUrl = "http://www.hljcg.gov.cn/xwzs!queryXwxxSsqx.action";
        Map<String, String> postParam = new HashMap<>();
        postParam.put("xwzsPage.pageNo", "5");
        postParam.put("xwzsPage.pageSize", "20");
        postParam.put("xwzsPage.pageCount", "3788");
        postParam.put("lbbh", "");
        postParam.put("id", "110");
        postParam.put("xwzsPage.zlbh", "");
        postParam.put("xwzsPage.GJZ", "");
        String data = JSON.toJSONString(postParam);



        String result = HttpClientUtil.postParameters(httpUrl, data);
        return result;
    }

    @GetMapping("post1")
    public String postJson() throws Exception{
        String httpUrl = "http://192.168.60.50:8060/device/testPost";
        Map<String, Object> postParam = new HashMap<>();
        postParam.put("testKey", "objV");
        String data = JSON.toJSONString(postParam);

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        Request request = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();
        return execNewCall(request);

    }

    @GetMapping("ip")
    public Result getIp(HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);

        try{

        }catch (Exception ex){
            return Result.error();
        }

        return Result.result(Result.OK);
    }

    @GetMapping("post2")
    public String postHtml() throws Exception{
        String httpUrl = "http://www.hljcg.gov.cn/xwzs!queryOneXwxxqx.action?xwbh=A69C2FD7654500E8E053AC10FDFB3865";
        Map<String, String> postParam = new HashMap<>();
        postParam.put("xwzsPage.pageNo", "5");
        postParam.put("xwzsPage.pageSize", "20");
        postParam.put("xwzsPage.pageCount", "3788");
        postParam.put("lbbh", "");
        postParam.put("id", "110");
        postParam.put("xwzsPage.zlbh", "");
        postParam.put("xwzsPage.GJZ", "");
        String data = JSON.toJSONString(postParam);

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), data);
        Request request = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();
        return execNewCall(request);

    }



    private static String execNewCall(Request request){
        Response response = null;
        try {
            //OkHttpClient okHttpClient = SpringUtils.getBean(OkHttpClient.class);
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}
