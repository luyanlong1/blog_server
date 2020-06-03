package com.example.springboot.es.controller;

import com.example.springboot.es.entity.mysql.MysqlBlog;
import com.example.springboot.es.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/9 16:16
 * @Version 1.0
 */

@Controller
public class IndexController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;


    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        System.out.println(all.size());
        return "page/show/index.html";
    }


    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        // 获取用户信息，如果已经登陆了则直接跳转
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            return "page/login/login.html";
        } else {
            System.out.println("已经登录过啦，用户信息为：" + session.getAttribute("userInfo"));
            response.sendRedirect(request.getContextPath()+"/home");
            return "";
        }
    }


    @RequestMapping("/home")
    public String home(){
        return "page/admin/home.html";
    }

}
