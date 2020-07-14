package com.xieyue.jwt.utils;


import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class OkHttpUtil {
  private static OkHttpClient client = new OkHttpClient();

  public static String get(String url) throws IOException {
    Request request = new Request.Builder().url(url).build();
    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public static void postAsync(String url, Map map, final Method method) throws IOException {
    FormBody.Builder builder = new FormBody.Builder();
    for (Object o : map.entrySet()) {
      Map.Entry entry = (Map.Entry) o;
      builder.add((String) entry.getKey(), (String) entry.getValue());
    }
    Request request = new Request.Builder().url(url).post(builder.build()).build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        try {
          method.invoke(method.getDeclaringClass(), call);
        } catch (IllegalAccessException e1) {
          e1.printStackTrace();
        } catch (InvocationTargetException e1) {
          e1.printStackTrace();
        }
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {

      }
    });
  }

  public static String post(String url, Map map) throws IOException {
    FormBody.Builder builder = new FormBody.Builder();
    for (Object o : map.entrySet()) {
      Map.Entry entry = (Map.Entry) o;
      builder.add((String) entry.getKey(), (String) entry.getValue());
    }
    Request request = new Request.Builder().url(url).post(builder.build()).build();
    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public static String post(String url, String json) throws IOException {
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    Request request = new Request.Builder().url(url).post(requestBody).build();
    Response execute = client.newCall(request).execute();
    return execute.body().string();
  }

  public static String post(String url, File file) throws IOException {
    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", file.getName(), fileBody)
            .build();
    Request request = new Request.Builder().url(url).post(requestBody).build();
    return client.newCall(request).execute().body().string();
  }
}
