package com.zheng.common.base;

import com.zheng.common.exception.UpmsSystemException;
import com.zheng.common.util.PropertiesFileUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器基类
 * Created by ZhangShuzheng on 2017/2/4.
 */
@RestControllerAdvice
public abstract class BaseController {

	private final static Logger _log = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 统一异常处理
	 * @param request
	 * @param response
	 * @param exception
	 */
	@ExceptionHandler
	@ResponseBody
	public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		BaseResponse baseResponse=new BaseResponse();
		_log.error("统一异常处理：", exception);
		request.setAttribute("ex", exception);
		if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			request.setAttribute("requestHeader", "ajax");
		}
		// shiro没有权限异常
		if (exception instanceof UnauthorizedException) {
			return BaseResponse.setResponse(baseResponse,ResponseCode.NO_AUTHORITY.toString());
		}
		if(exception instanceof UnauthenticatedException){
			return BaseResponse.setResponse(baseResponse,ResponseCode.UNAUTHENTICATED.toString());
		}
		if (exception instanceof UpmsSystemException) {
			return BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_SYSTEM.toString());
		}
		// shiro会话已过期异常
		if (exception instanceof InvalidSessionException) {
			return BaseResponse.setResponse(baseResponse,ResponseCode.SESSION_EXPIRED.toString());
		}
		//账号不存在
		if(exception instanceof UnknownAccountException){
			return BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_USERNAME.toString());
		}
		//密码 不正确
		if(exception instanceof IncorrectCredentialsException){
			return BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_PASSWORD.toString());

		}
		//被锁定
		if(exception instanceof LockedAccountException){
			return BaseResponse.setResponse(baseResponse,ResponseCode.INVALID_LOCK.toString());
		}

		return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
	}

	/**
	 * 返回jsp视图
	 * @param path
	 * @return
	 */
	public static String jsp(String path) {
		return path.concat(".jsp");
	}

	/**
	 * 返回thymeleaf视图
	 * @param path
	 * @return
	 */
	public static String thymeleaf(String path) {
		String folder = PropertiesFileUtil.getInstance().get("app.name");
		return "/".concat(folder).concat(path).concat(".html");
	}

}
