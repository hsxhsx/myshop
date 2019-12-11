package com.hsx.myshop.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        String requestURI = request.getRequestURI();
        System.out.println("当前请求URI路径："+requestURI);

        //如果是swagger, 用户注册，登录，验证，同步操作则直接放行
//        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        if(requestURI.startsWith("/swagger-resources") || requestURI.startsWith("/webjars") || requestURI.startsWith("/v2")
        || requestURI.startsWith("/swagger-ui.html")) {
            chain.doFilter(req, res);
            return;
        }
        if(requestURI.startsWith("/exclusions") || requestURI.contains("/*.js") || requestURI.contains("/*.css")
        || requestURI.contains("/druid") || requestURI.startsWith("/favicon.ico")) {
            chain.doFilter(req, res);
            return;
        }
        if(requestURI.startsWith("/login") ){
            chain.doFilter(req, res);
            return;
        }

        //请求头中需要添加authorization字段
        final String authHeader = request.getHeader("authorization");

        // If the Http request is OPTIONS then just return the status code 200
        // which is HttpServletResponse.SC_OK in this code
        String method = request.getMethod();
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {
            //authorization 的 值，需要以Bearer 开头，否则认证不通过
            // Check the authorization, check if the token is started by "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            // Then get the JWT token from authorization
            final String token = authHeader.replaceFirst("Bearer", "").trim();

            try {
                // Use JWT parser to check if the signature is valid with the Key "secretkey"
                //final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
                final Claims claims = JwtUtil.parseJWT(token);
                // Add the claim to request header
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);
        }
    }

}
