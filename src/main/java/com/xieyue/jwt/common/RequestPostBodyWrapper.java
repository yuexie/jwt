package com.xieyue.jwt.common;

import com.xieyue.jwt.utils.HttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Slf4j
public class RequestPostBodyWrapper extends HttpServletRequestWrapper {

    private final byte[] requestBody;//用于将流保存下来


    public RequestPostBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String bodyString = HttpHelper.getBodyString(request);

        requestBody = bodyString.getBytes(Charset.forName("UTF-8"));

    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 创建字节数组输入流
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
