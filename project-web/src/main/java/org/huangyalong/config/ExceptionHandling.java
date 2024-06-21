package org.huangyalong.config;

import cn.dev33.satoken.exception.*;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelExecutionException;
import org.huangyalong.core.commons.exception.InternalServerErrorException;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.commons.info.ShowType;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final Exception e) {
        return ApiResponse.fail(e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NoHandlerFoundException e) {
        return ApiResponse.fail("请检查请求路径或者类别是否正确",
                HttpStatus.NOT_FOUND.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final BindException e) {
        return ApiResponse.fail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final MethodArgumentNotValidException e) {
        return ApiResponse.fail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(DisableServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final DisableServiceException e) {
        return ApiResponse.fail(StrUtil.format("您的帐号被封禁，请在 {} 秒后重新登陆", e.getDisableTime()),
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NotLoginException e) {
        return ApiResponse.fail("您的帐号信息已过期，请重新登陆",
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName(),
                ShowType.REDIRECT.getShowType());
    }

    @ExceptionHandler(NotSafeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NotSafeException e) {
        return ApiResponse.fail("您的帐号核验未通过，请重新核验",
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(NotBasicAuthException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NotBasicAuthException e) {
        return ApiResponse.fail("您的帐号核验未通过，请重新核验",
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NotPermissionException e) {
        return ApiResponse.fail("您没有足够的权限执行该操作",
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(NotRoleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final NotRoleException e) {
        return ApiResponse.fail("您没有足够的权限执行该操作",
                HttpStatus.UNAUTHORIZED.value(),
                e.getClass().getName());
    }

    @ExceptionHandler(CamelExecutionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final CamelExecutionException e) {
        Throwable throwable = ExceptionUtil.getRootCause(e);
        if (ExceptionUtil.isFromOrSuppressedThrowable(throwable, InternalServerErrorException.class)) {
            return handleException(ExceptionUtil.wrap(throwable, InternalServerErrorException.class));
        } else return handleException(ExceptionUtil.wrapRuntime(throwable));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(final InternalServerErrorException e) {
        return ApiResponse.fail(e.getMessage(),
                e.getErrorCode(),
                e.getClass().getName(),
                e.getShowType().getShowType(),
                e.getTraceId(),
                e.getHost());
    }
}
