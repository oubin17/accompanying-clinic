package com.odk.baseweb.interceptor.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.vo.response.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler
 * 全局异常处理器，@ExceptionHandler注解来指定处理CustomValidationException异常的方法。
 * 在这个方法中，你可以定义如何处理校验失败的情况，例如返回特定的HTTP状态码和错误信息。
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ServiceResponse> handleValidationException(BizException e) {
        // 处理校验异常，可以根据需要返回适当的响应
        return new ResponseEntity<>(ServiceResponse.valueOfError(e.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理系统异常 ： SaToken
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<ServiceResponse> handleValidationException(NotLoginException e) {
        // 处理校验异常，可以根据需要返回适当的响应

        return new ResponseEntity<>(ServiceResponse.valueOfError(BizErrorCode.TOKEN_UNMATCHED, "token无效"), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理系统异常 ： SaToken
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotRoleException.class)
    public ResponseEntity<ServiceResponse> handleValidationException(NotRoleException e) {
        // 处理校验异常，可以根据需要返回适当的响应
        return new ResponseEntity<>(ServiceResponse.valueOfError(BizErrorCode.PERMISSION_DENY, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
