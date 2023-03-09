package com.xuecheng.base.Exception;

/**
 * @Author Luffy5522
 * @date: 2023/3/9 10:47
 * @description: 异常处理器
 */
public class XuechengException extends RuntimeException {

    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public XuechengException() {
        super();
    }

    public XuechengException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {

        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new XuechengException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new XuechengException(errMessage);
    }
}
