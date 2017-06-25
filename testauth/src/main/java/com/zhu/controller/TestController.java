package com.zhu.controller;

import com.zheng.common.base.BaseController;
import com.zheng.common.base.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhu on 2017/6/25.
 */
@RestController
@RequestMapping("/manage")
public class TestController extends BaseController{
    @RequestMapping(value = "/getTest",method = RequestMethod.GET)
    @RequiresPermissions("upms:22:11")
    public BaseResponse getTest(){
        BaseResponse baseResponse=new BaseResponse();
        return baseResponse;
    }
}
