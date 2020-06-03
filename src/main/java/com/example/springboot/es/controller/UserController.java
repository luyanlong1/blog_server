package com.example.springboot.es.controller;

import com.example.springboot.es.entity.mysql.UserInfoEntity;
import com.example.springboot.es.repository.mysql.UserInfoRepository;
import com.example.springboot.es.utils.MD5Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/12 15:50
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserInfoRepository userInfoRepository;


    @PostMapping("/userLogin")
    public Object login(@RequestBody String jsonString, HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String account = jsonObject.getString("account");
        String passWordParam = MD5Util.md5(jsonObject.getString("passWord"));
        List<UserInfoEntity> list = userInfoRepository.checkAccount(account);
        if (null != list && list.size()>0){
            String passWordTrue = list.get(0).getPassWord();
            if (passWordParam.equalsIgnoreCase(passWordTrue)){
                request.getSession().setAttribute("userInfo",account+"##"+passWordTrue);
                System.out.println("登录成功,用户信息为="+request.getSession().getAttribute("userInfo"));
                resultJson.put("code","200");
                resultJson.put("userInfo",list.get(0));
                resultJson.put("msg","登录成功!");
            }else {
                System.out.println("密码不正确！");
                resultJson.put("code","300");
                resultJson.put("msg","密码不正确!");
            }
        }else {
            System.out.println("账号不存在！");
            resultJson.put("code","300");
            resultJson.put("msg","账号不存在!");
        }
        return resultJson;
    }

}
