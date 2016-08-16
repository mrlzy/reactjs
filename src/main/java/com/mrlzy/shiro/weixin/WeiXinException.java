package com.mrlzy.shiro.weixin;


public class WeiXinException extends  Exception{
    public WeiXinException() {
        super();
    }

    public WeiXinException(String message) {
        super(message);
    }

    public WeiXinException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeiXinException(Throwable cause) {
        super(cause);
    }

    protected WeiXinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
