package com.zhu.controller;

import com.zheng.common.base.BaseController;
import com.zheng.common.base.BaseResponse;
import com.zheng.common.util.MD5Util;
import com.zheng.common.util.RedisUtil;
import com.zheng.common.util.key.SnowflakeIdWorker;
import com.zheng.upms.client.shiro.token.StatelessToken;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-06-29 11:17
 **/
@RestController
@RequestMapping("/test")
public class SessionController extends BaseController{
    @Autowired
    private UpmsApiService upmsApiService;
    @RequestMapping(value = "/testLogin",method = RequestMethod.GET)
    public BaseResponse testLogin(HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse=new BaseResponse();
        //1、客户端生成的消息摘要

        //2、客户端传入的用户身份
        String username = request.getParameter("username");
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        if(upmsUser==null){
            throw new UnknownAccountException("无效账号");
        }
        String password = request.getParameter("password");
        if(!upmsUser.getPassword().equals(MD5Util.MD5(password+upmsUser.getSalt()))){
            throw  new IncorrectCredentialsException("密码错误");
        }
        //3、客户端请求的参数列表
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        params.remove("digest");
        //4、生成无状态Token

        SnowflakeIdWorker snowflakeIdWorker=new SnowflakeIdWorker(0,0);
        String token = "TK" + MD5Util.MD5(username + snowflakeIdWorker).toUpperCase();
        RedisUtil.set("username_token_"+username,token);
        StatelessToken statelessToken = new StatelessToken(username, token);
        //5、委托给Realm进行登录
        Subject subject = getSubject(request, response);
        subject.login(statelessToken);
        return baseResponse;
    }
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }
}
