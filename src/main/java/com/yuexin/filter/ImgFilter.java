package com.yuexin.filter;

import com.yuexin.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: LiWenC
 * Date: 16-9-23
 */
@WebFilter(urlPatterns = {"*.jpg", "*.png"})
public class ImgFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contentSecret = request.getHeader("Content-Secret");
        if (Constants.imgSecret.equals(contentSecret)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(403);
        }
    }

    @Override
    public void destroy() {
    }
}
