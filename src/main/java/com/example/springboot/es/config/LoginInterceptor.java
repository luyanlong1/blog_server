package com.example.springboot.es.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/12 16:53
 * @Version 1.0
 * @desc 登录状态拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("==========登录状态拦截");
        HttpSession session = request.getSession();
        System.out.println("sessionId为：" + session.getId());

        // 获取用户信息，如果没有用户信息直接返回提示信息
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            System.out.println("没有登录");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        } else {
            System.out.println("已经登录过啦，用户信息为：" + session.getAttribute("userInfo"));
        }
        return true;
    }
}
