package com.zhu.controller;

import com.zheng.common.base.BaseController;
import com.zheng.common.base.BaseResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhu on 2017/6/25.
 */
@RestController
@RequestMapping("/api")
public class TestController extends BaseController{

    @RequestMapping(value = "/getTest",method = RequestMethod.GET)
    @RequiresPermissions("upms:22:11")
    public BaseResponse getTest(){
        BaseResponse baseResponse=new BaseResponse();
        return baseResponse;
    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public BaseResponse login(){
        BaseResponse baseResponse=new BaseResponse();

        return baseResponse;
    }


    @RequestMapping(value = "/noauth",method = RequestMethod.GET)
    public BaseResponse noauth(){
        BaseResponse baseResponse=new BaseResponse();
        return baseResponse;
    }


    @RequestMapping(value = "/testsession",method = RequestMethod.GET)
    public BaseResponse testsession(){
        BaseResponse baseResponse=new BaseResponse();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        System.out.println(session.getId());
        return baseResponse;
    }
}
