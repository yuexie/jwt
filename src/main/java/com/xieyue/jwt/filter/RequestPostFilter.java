package com.xieyue.jwt.filter;


import com.xieyue.jwt.common.RequestPostBodyWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName="requestPostFilter",urlPatterns="/*")
public class RequestPostFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // 遇到post方法才对request进行包装
            String methodType = httpRequest.getMethod();
            // 上传文件时同样不进行包装
            String servletPath = httpRequest.getRequestURI();
            if ("POST".equals(methodType) && !servletPath.contains("/material/upload")) {
                requestWrapper = new RequestPostBodyWrapper(
                        (HttpServletRequest) request);
            }
        }

        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {

    }

}