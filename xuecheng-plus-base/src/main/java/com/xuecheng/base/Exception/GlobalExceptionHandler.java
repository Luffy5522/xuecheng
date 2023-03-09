package com.xuecheng.base.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/3/8 20:15
 * @description: 全局的异常处理器
 */
// 控制器增强
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // 对可预知的异常进行处理
    @ExceptionHandler(MethodArgumentNotValidException.class)//处理XuechengException这个类
    @ResponseBody//统一以json的形式返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回异常状态码500
    public RestErrorResponse doXuechengException(XuechengException e) {
        log.error("【系统异常】{}", e.getErrMessage(), e);
        return new RestErrorResponse(e.getErrMessage());
    }

    // 对可预知的异常进行处理
    @ExceptionHandler(MethodArgumentNotValidException.class)//处理XuechengException这个类
    @ResponseBody//统一以json的形式返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回异常状态码500
    public RestErrorResponse doValidException(MethodArgumentNotValidException argumentNotValidException) {

        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        StringBuffer errMsg = new StringBuffer();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> errMsg.append(error.getDefaultMessage()).append(","));
        log.error(errMsg.toString());
        return new RestErrorResponse(errMsg.toString());
    }

    // 对异常进行处理
    @ExceptionHandler(Exception.class)//处理Exception这个类
    @ResponseBody//统一以json的形式返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回异常状态码500
    public RestErrorResponse doException(Exception e) {
        log.info("捕获异常:{}", e.getMessage());
        e.printStackTrace();
        // 返回json字符串
        return new RestErrorResponse(CommonError.UN_KNOW_ERROR.getErrMessage());
    }


}
