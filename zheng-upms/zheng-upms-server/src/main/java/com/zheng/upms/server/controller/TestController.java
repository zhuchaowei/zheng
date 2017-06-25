package com.zheng.upms.server.controller;

import com.zheng.common.base.BaseController;
import com.zheng.common.base.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhu on 2017/6/25.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController{
    @RequiresPermissions("upms:permissionqqq:read")
    @RequestMapping(value = "/te/tt", method = RequestMethod.GET)
    public BaseResponse test(){
        BaseResponse baseResponse=new BaseResponse();

        return baseResponse;
    }

}
