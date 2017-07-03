package com.zheng.upms.client.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResponse;
import com.zheng.common.base.ResponseCode;
import com.zheng.common.exception.UpmsSystemException;
import com.zheng.upms.client.shiro.token.StatelessToken;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * http://412887952-qq-com.iteye.com/blog/2359098
 * Created by zhu on 2017/6/27.
 */
public class StatelessAuthcFilter extends AuthenticationFilter {
    private final static Logger _log = LoggerFactory.getLogger(UpmsAuthenticationFilter.class);
    @Autowired
    private UpmsApiService upmsApiService;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            //1、客户端生成的消息摘要
            String clientDigest = request.getParameter("token");
            //2、客户端传入的用户身份
            String username = request.getParameter("username");
            UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
            if(upmsUser==null){
                throw new UnknownAccountException("无效账号");
            }
            //3、客户端请求的参数列表
            Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
            params.remove("digest");
            //4、生成无状态Token
            StatelessToken token = new StatelessToken(username, clientDigest);
            //5、委托给Realm进行登录
            Subject subject = getSubject(request, response);
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    //登录失败时默认返回401状态码
    private void onLoginFail(ServletRequest request,ServletResponse response,Exception exception) throws IOException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        JSONObject errorJsonObject = new JSONObject();
//        errorJsonObject.put("errno", HttpServletResponse.SC_UNAUTHORIZED+"");
//        errorJsonObject.put("error", errorString);
//        httpResponse.getWriter().write(errorJsonObject.toJSONString());

        BaseResponse baseResponse=new BaseResponse();
        if (exception instanceof UnauthorizedException) {
             BaseResponse.setResponse(baseResponse, ResponseCode.NO_AUTHORITY.toString());
        }
        if(exception instanceof UnauthenticatedException){
             BaseResponse.setResponse(baseResponse,ResponseCode.UNAUTHENTICATED.toString());
        }
        if (exception instanceof UpmsSystemException) {
             BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_SYSTEM.toString());
        }
        // shiro会话已过期异常
        if (exception instanceof InvalidSessionException) {
             BaseResponse.setResponse(baseResponse,ResponseCode.SESSION_EXPIRED.toString());
        }
        //账号不存在
        if(exception instanceof UnknownAccountException){
             BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_USERNAME.toString());
        }
        //密码 不正确
        if(exception instanceof IncorrectCredentialsException){
             BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_PASSWORD.toString());

        }
        //被锁定
        if(exception instanceof LockedAccountException){
             BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_LOCK.toString());
        }
        response.getWriter().write(JSONObject.toJSONString(baseResponse));
    }

}
