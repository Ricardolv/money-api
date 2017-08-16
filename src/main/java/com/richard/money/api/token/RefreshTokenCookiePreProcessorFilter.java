package com.richard.money.api.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Profile("oauth-security")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
                && "refresh_token".equals(req.getParameter("grant_type"))
                && null != req.getCookies()) {

            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    req = new MyServleRequestWrapper(req, refreshToken);
                }
            }
        }

        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    static class MyServleRequestWrapper extends HttpServletRequestWrapper {

        private String refresToken;

        public MyServleRequestWrapper(HttpServletRequest request, String refresToken) {
            super(request);
            this.refresToken = refresToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] {refresToken});
            map.setLocked(true);
            return map;
        }
    }
}
