package com.xuecheng.base.Exception;

import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Luffy5522
 * @date: 2023/3/9 11:05
 * @description: 响应用户的统一类型
 */
@NoArgsConstructor
public class RestErrorResponse implements Serializable {

    private String errMessage;


    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
