package com.zheng.common.exception;

/**
 * Created by zhu on 2017/6/26.
 */
public class UpmsSystemException extends UpmsException{
    public UpmsSystemException() {
    }

    public UpmsSystemException(String message) {
        super(message);
    }

    public UpmsSystemException(Throwable cause) {
        super(cause);
    }

    public UpmsSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
