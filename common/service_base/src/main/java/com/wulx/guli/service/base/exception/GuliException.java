package com.wulx.guli.service.base.exception;

import com.wulx.guli.service.base.result.ResultCodeEnum;
import lombok.Data;

@Data
public class GuliException extends RuntimeException {
    private Integer code;


    public GuliException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
