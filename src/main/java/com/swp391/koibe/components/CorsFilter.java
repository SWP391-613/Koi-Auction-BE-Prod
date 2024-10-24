//package com.swp391.koibe.components;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CorsFilter extends OncePerRequestFilter {
//
//    @Value("${front.end.url:http://localhost:3000}")  // Better to externalize this in
//    // application.properties
//    private String frontEndUrl;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//        throws ServletException, IOException {
//        String origin = request.getHeader("Origin");
//        log.info("Received request from origin: {}", origin);
//        log.info("Request method: {}", request.getMethod());
//        log.info("Request URI: {}", request.getRequestURI());
//
//        // Allow both localhost and localtunnel domains
//        if (origin != null && (origin.equals(frontEndUrl) || origin.endsWith(".loca.lt"))) {
//            response.setHeader("Access-Control-Allow-Origin", origin);  // Use the actual origin
//        }
//
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token, x-requested-with");
//        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        if ("OPTIONS".equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
//}
