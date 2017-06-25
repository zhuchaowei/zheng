package com.zheng.common.base;


import java.util.UUID;

/**
 * 异常响应
 * Created by dingr on 14-2-13.
 */
public class BaseResponse {

    private String requestId;

    private String code;
    private String message;

    private Object result;

    public BaseResponse(){
        ResponseCode responseCode = ResponseCode.toEnum(ResponseCode.SUCCESS.toString());
        this.code = responseCode.toString();
        this.message = responseCode.message;
        this.requestId =  UUID.randomUUID().toString();
    }
    public BaseResponse(String requestId){
        ResponseCode responseCode = ResponseCode.toEnum(ResponseCode.SUCCESS.toString());
        this.code = responseCode.toString();
        this.message = responseCode.message;
        this.requestId = requestId;
    }
    /**
     * 根据错误代码设置异常
     * @param code
     * @return
     */
    public static <T> T setResponse(String code){
        BaseResponse response = new BaseResponse();
        return (T)setResponse(response,code);
    }

    /**
     * 根据错误代码获取异常响应
     * @param code
     * @return
     */
    public static  <T> T setResponse(BaseResponse response,String code){
        ResponseCode error = ResponseCode.toEnum(code);
        response.setCode(error.code);
        response.setMessage(error.message);
        response.setRequestId(response.getRequestId());
        return (T)response;
    }
    /**
     * 根据错误代码设置异常 并自定义消息
     * @param code
     * @param message
     * @return
     */
    public static <T> T setResponse(String code,String message){
        BaseResponse response = new BaseResponse();
        return (T)setResponse(response,code,message);
    }
    /**
     * 根据错误代码设置异常 并自定义消息
     * @param code
     * @param message
     * @return
     */
    public static <T> T setResponse(BaseResponse response,String code,String message){
        ResponseCode error = ResponseCode.toEnum(code);
        response.setCode(error.code);
        response.setMessage(error.message.concat( message));
        response.setRequestId(response.getRequestId());
        return (T)response;
    }
    public  static <T> T  responseWithName(BaseResponse response,String code, Object msg){
        ResponseCode error = ResponseCode.toEnum(code);
        response.setCode(error.code);
        response.setMessage(msg.toString());
        response.setRequestId(response.getRequestId());
        return (T)response;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
