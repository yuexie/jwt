package com.xieyue.jwt.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * 封装了采用HttpClient发送HTTP请求的方法
 *
 * @author lilin
 * @create 2016年10月12日18:52:06
 * @see 本工具所采用的是HttpComponents-Client-4.3.2
 * @see ========================================================================
 * == =========================
 * @see sendPostSSLRequest 发送https post请求，  兼容 http post 请求
 * @see sendGetSSLRequest  发送https get请求，  兼容 http get 请求
 */

public class HttpClientUtil {

  /***
   *
   * 连接超时,建立链接超时时间,毫秒.
   */
  public static final int        connTimeout = 10000;
  /**
   * 响应超时,响应超时时间,毫秒.
   */
  public static final int        readTimeout = 300000;
  /**
   * 默认字符编码
   */
  public static final String     charset     = "UTF-8";
  private static CloseableHttpClient client      = null;

  static {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(128);
    cm.setDefaultMaxPerRoute(128);
    client = HttpClients.custom().setConnectionManager(cm).build();
  }

  public static String postFile(String url, File file) throws IOException {
    HttpPost               httpPost               = new HttpPost(url);
    FileBody               fileBody               = new FileBody(file);
    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder.addPart("file", fileBody);
    httpPost.setEntity(multipartEntityBuilder.build());
    HttpResponse execute = client.execute(httpPost);
    return IOUtils.toString(execute.getEntity().getContent(), "UTF-8");
  }

  public static String postParameters(String url, String parameterStr) {
    return sendPostSSLRequest(url, parameterStr, charset, "application/json");
  }

  public static String postParameters(String url, String parameterStr, String charset) {
    return sendPostSSLRequest(url, parameterStr, charset, "application/json");
  }

  public static String postParameters(String url, Map<String, Object> params) throws ConnectTimeoutException,
          SocketTimeoutException, Exception {
      Map<String,String> map = new HashMap<>();
      map.put("Content-Type", "application/json; charset=utf-8");
    return postForm(url, params, map, connTimeout, readTimeout);
  }

  public static String postParameters(String url, Map<String, Object> params, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException,
          SocketTimeoutException, Exception {
    return postForm(url, params, null, connTimeout, readTimeout);
  }

  public static String get(String url) {
    return sendGetSSLRequest(url, charset);
  }

  public static String get(String url, String charset) {
    return sendGetSSLRequest(url, charset);
  }

  /**
   * 发送一个 Post 请求, 使用指定的字符集编码.
   *
   * @param url
   * @param body     RequestBody
   * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
   * @param charset  编码
   * @return ResponseBody, 使用指定的字符集编码.
   * @throws ConnectTimeoutException 建立链接超时异常
   * @throws SocketTimeoutException  响应超时
   * @throws Exception
   */
  public static String sendPostSSLRequest(String url, String body, String charset, String mimeType) {
    CloseableHttpClient client = null;
    HttpPost   post   = new HttpPost(url);
    String     result = "通信失败";
    try {
      if (StringUtils.isNotBlank(body)) {
        HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
        post.setEntity(entity);
      }
      // 设置参数
      Builder customReqConf = RequestConfig.custom();
      customReqConf.setConnectTimeout(connTimeout);
      customReqConf.setSocketTimeout(readTimeout);
      post.setConfig(customReqConf.build());

      HttpResponse res;
      if (url.startsWith("https")) {
        // 执行 Https 请求.
        client = createSSLInsecureClient();

        res = client.execute(post);

      } else {
        // 执行 Http 请求.
        client = HttpClientUtil.client;
        res = client.execute(post);
      }
      result = IOUtils.toString(res.getEntity().getContent(), charset);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      post.releaseConnection();
      if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
        try {
          ((CloseableHttpClient) client).close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  public static String sendPostSSLRequest(String url, Map<String, Object> body, Map<String, String> headers) {
    CloseableHttpClient client = null;
    HttpPost post = new HttpPost(url);
    String result = "通信失败";
    try {
      if (body != null && !body.isEmpty()) {
        List<NameValuePair> formParams = new ArrayList();
        Set<Entry<String, Object>> entrySet = body.entrySet();
        for (Entry<String, Object> entry : entrySet) {
          if (entry.getValue() instanceof String)
            formParams.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
          else
            formParams.add(new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue())));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        post.setEntity(entity);
      }

      if (headers != null && !headers.isEmpty()) {
        for (Entry<String, String> entry : headers.entrySet()) {
          post.addHeader(entry.getKey(), entry.getValue());
        }
      }
      // 设置参数
      Builder customReqConf = RequestConfig.custom();
      customReqConf.setConnectTimeout(connTimeout);
      customReqConf.setSocketTimeout(readTimeout);
      post.setConfig(customReqConf.build());

      HttpResponse res;
      if (url.startsWith("https")) {
        // 执行 Https 请求.
        client = createSSLInsecureClient();

        res = client.execute(post);

      } else {
        // 执行 Http 请求.
        client = HttpClientUtil.client;
        res = client.execute(post);
      }
      result = IOUtils.toString(res.getEntity().getContent(), charset);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      post.releaseConnection();
      if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
        try {
          ((CloseableHttpClient) client).close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }


  /**
   * 提交form表单
   *
   * @param url
   * @param params
   * @param connTimeout
   * @param readTimeout
   * @return
   * @throws ConnectTimeoutException
   * @throws SocketTimeoutException
   * @throws Exception
   */
  public static String postForm(String url, Map<String, Object> params, Map<String, String> headers, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException,
          SocketTimeoutException, Exception {

    CloseableHttpClient client = null;
    HttpPost   post   = new HttpPost(url);
    try {
      if (params != null && !params.isEmpty()) {
        List<NameValuePair>        formParams = new ArrayList<NameValuePair>();
        Set<Entry<String, Object>> entrySet   = params.entrySet();
        for (Entry<String, Object> entry : entrySet) {
          if (entry.getValue() instanceof String)
            formParams.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
          else
            formParams.add(new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue())));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        //HttpEntity entity = new StringEntity(params.toString(), ContentType.create("application/json", charset));
        post.setEntity(entity);
      }

      if (headers != null && !headers.isEmpty()) {
        for (Entry<String, String> entry : headers.entrySet()) {
          post.addHeader(entry.getKey(), entry.getValue());
        }
      }
      // 设置参数
      Builder customReqConf = RequestConfig.custom();
      if (connTimeout != null) {
        customReqConf.setConnectTimeout(connTimeout);
      }
      if (readTimeout != null) {
        customReqConf.setSocketTimeout(readTimeout);
      }
      post.setConfig(customReqConf.build());
      HttpResponse res = null;
      if (url.startsWith("https")) {
        // 执行 Https 请求.
        client = createSSLInsecureClient();
        res = client.execute(post);
      } else {
        // 执行 Http 请求.
        client = HttpClientUtil.client;
        res = client.execute(post);
      }
      return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
    } finally {
      post.releaseConnection();
      if (url.startsWith("https") && client != null
              && client instanceof CloseableHttpClient) {
        ((CloseableHttpClient) client).close();
      }
    }
  }


  /**
   * 发送一个 GET 请求
   *
   * @param url
   * @param charset
   * @return
   * @throws ConnectTimeoutException 建立链接超时
   * @throws SocketTimeoutException  响应超时
   * @throws Exception
   */
  public static String sendGetSSLRequest(String url, String charset) {

    CloseableHttpClient client = null;
    HttpGet    get    = new HttpGet(url);
    String     result = "通信失败";
    try {
      // 设置参数
      Builder customReqConf = RequestConfig.custom();
      customReqConf.setConnectTimeout(connTimeout);
      customReqConf.setSocketTimeout(readTimeout);
      get.setConfig(customReqConf.build());

      HttpResponse res = null;

      if (url.startsWith("https")) {
        // 执行 Https 请求.
        client = createSSLInsecureClient();
        res = client.execute(get);

      } else {
        // 执行 Http 请求.
        client = HttpClientUtil.client;
        res = client.execute(get);
      }

      result = IOUtils.toString(res.getEntity().getContent(), charset);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      get.releaseConnection();
      if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
        try {
          ((CloseableHttpClient) client).close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }


  /**
   * 从 response 里获取 charset
   *
   * @param ressponse
   * @return
   */
  @SuppressWarnings("unused")
  private static String getCharsetFromResponse(HttpResponse ressponse) {
    // Content-Type:text/html; charset=GBK
    if (ressponse.getEntity() != null && ressponse.getEntity().getContentType() != null && ressponse.getEntity().getContentType().getValue() != null) {
      String contentType = ressponse.getEntity().getContentType().getValue();
      if (contentType.contains("charset=")) {
        return contentType.substring(contentType.indexOf("charset=") + 8);
      }
    }
    return null;
  }


  /**
   * 创建 SSL连接
   *
   * @return
   * @throws GeneralSecurityException
   */
  private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
    try {
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          return true;
        }
      }).build();

      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

        @Override
        public boolean verify(String arg0, SSLSession arg1) {
          return true;
        }

        @Override
        public void verify(String host, SSLSocket ssl)
                throws IOException {
        }

        @Override
        public void verify(String host, X509Certificate cert)
                throws SSLException {
        }

        @Override
        public void verify(String host, String[] cns,
                           String[] subjectAlts) throws SSLException {
        }

      });

      return HttpClients.custom().setSSLSocketFactory(sslsf).build();

    } catch (GeneralSecurityException e) {
      throw e;
    }
  }

}
