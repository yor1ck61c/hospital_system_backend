package io.oicp.yorick61c.hospital_system.interceptor;

import io.jsonwebtoken.Claims;
import io.oicp.yorick61c.hospital_system.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURI = request.getRequestURI();

        //登录请求直接放行
        if ("/user/login".equals(requestURI) || "/user/register".equals(requestURI) || "/center/list".equals(requestURI)) {
            return true;
        }

        // 字符串名称与前端的config.Headers['Authorization'] = getToken()保持一致
        Claims claims = JwtUtil.parse(request.getHeader("token"));
        // 登录过就放行
        if (claims == null) {
            System.out.println("该请求已被拦截");
        }
        return claims != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //请求结束后删除信息，否则可能造成内存泄漏
    }
}
