package cn.soft.common.exception;

import cn.soft.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName RainBootExceptionHandler
 * @Description controller层的全局异常处理器
 * @Author ycl
 * @Date 2022/3/11 12:44
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class RainBootExceptionHandler {

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return 返回处理结果
     */
    @ExceptionHandler(RainBootException.class)
    public Result<?> handleRainBootException(RainBootException e) {
        return Result.error(e.getMessage());
    }

    /**
     * 处理401异常
     *
     * @param e 异常
     * @return 返回异常信息
     */
    @ExceptionHandler(RainBoot401Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handlerRainBoot401Exception(RainBoot401Exception e) {
        return new Result<>(401, e.getMessage());
    }
}
