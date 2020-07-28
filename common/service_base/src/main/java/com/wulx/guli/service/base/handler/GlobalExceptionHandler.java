package com.wulx.guli.service.base.handler;


import com.baomidou.mybatisplus.annotation.TableField;
import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice  //标注该注解为统一异常类
public class GlobalExceptionHandler {

    @TableField(value = "is_deleted")
    private Boolean deleted;
    /**
     * 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(ExceptionUtils.getStackTrace(e));
        return R.error();
    }
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        log.error(ExceptionUtils.getStackTrace(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public R error(ArrayIndexOutOfBoundsException e){
        e.getStackTrace();
        return R.error();
    }




}
