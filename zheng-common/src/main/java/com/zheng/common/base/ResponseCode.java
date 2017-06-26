package com.zheng.common.base;

/**
 * Author: dingran
 * Date: 2015/10/21
 * Description:返回状态码
 */
public enum ResponseCode {


    /* (code,message,httpCode)
    *
    * pay attention to :
    * the message must  end with '.'
    * */


    SUCCESS("200", "Success"),
    EMPTY_USERNAME("10101", "Username cannot be empty"),
    EMPTY_PASSWORD("10102", "Password cannot be empty"),
    INVALID_USERNAME("10103", "Account does not exist"),
    INVALID_PASSWORD("10104", "Password error"),
    INVALID_ACCOUNT("10105", "Invalid account"),
    //service error , 500 begin
    SERVICE_ERROR( "500", "The request service process error."),
    //server end
    LOGIN_FAILD("20002", "username or password is incorrect"),

    //client error, 400 begin
    UNKNOWN_OPERATION("40001","The request operation is unsupported."),
    PARAMETER_MISS( "40002","The request parameter is miss."),
    PARAMETER_INVALID("40003", "The request parameter is invalid." ),
    STATE_INCORRECT( "40004", "The request resource in an incorrect state for the request."),
    RESOURCE_NOTFOUND( "40005", "The request resource is not exist."),
    RESOURCE_INUSE( "40006", "The request resource is in use."),
    RESOURCE_DUPLICATE( "40007", "The request resource is duplicate."),
    RESOURCE_LIMITEXCEEDED( "40008", "The request resource is limitExceeded."),
    RESOURCE_NOTBOND( "40102", "The resource is not bound."),
    TOKEN_INVALID( "40009", "The token is invalid."),
    RESOURCE_IMPERFECT("40010", "Resource is imperfect."),//资源不完整
    FORBIDDEN_AUTHFAILURE( "40301", "The request credentials is unauthorized，please check your credentials."),

    FORBIDDEN_NOPERMISSION( "40302", "The request resource has no permissions."),
    FORBIDDEN_SIGNATURE_DOESNOT_MATCH( "40303", "The request signature does not match,please refer to the api reference."),

    FORBIDDEN_TOKENFAILURE( "40304", "The request token is unauthorized，please check your token."),
    FORBIDDEN_TOKENTIMEOUT( "40305", "The request token is timeout."),

    FORBIDDEN_LOCKED( "40306", "The request account is locked."),
    REPEAT_LOGIN("40101", "The user is logged in."),

    FORBIDDEN_NOSCAN( "40307", "The request resource has no scan."),

    ALREADY_FRIEND("40308", "You are already friends."),
    INTEGRAL_INSUFFICIENT( "40309", "Deficiency of integral."),//积分不足
    STAMINA_INSUFFICIENT( "40310", "Lack of physical strength."),//体力不足
    TASK_ALREADY_RECEIVE( "40311", "Task has been received."),//任务已领取
  
    USER_FIGHT( "40312", "User Fighting."),//用户战斗中
    USER_OFF_LINE( "40313", "User offLine."),//用户已离线

    NOT_LOGIN( "20001", "The user is not login."),

    NO_AUTHORITY("9000001","无资源访问权限"),
    SESSION_EXPIRED("9000002","会话过期"),
    UNAUTHENTICATED("9000003","未经身份验证"),
    INVALID_SYSTEM("9000004","无效系统，请注册到权限系统！"),

    //client end
    THE_GOODS_ALREADY_OVERDUE("20010","the goods already overdue"),//该商品已经过期
    NOTMARKFINISH("90001","mark not  finished");//卷子未批完无法生成综合报,
    //client end
    public final String code;
    public final String message;
    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return  code;
    }

    public static ResponseCode toEnum(String name) {
        for (ResponseCode res : ResponseCode.values()) {
            if (res.toString().equalsIgnoreCase(name)) {
                return res;
            }
        }
        return null;
    }
}
