package com.wdd.studentmanager.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

@Slf4j
public class BindingResultUtil {

    private BindingResultUtil() {
    }

    public static void validate(BindingResult result) {
        if (result.hasErrors()) {
            if (result.getFieldError() == null) {
                throw new RuntimeException("请求参数错误");
            }

            String errMsg = result.getFieldError().getDefaultMessage();
            String field = result.getFieldError().getField();
            Object rejectedValue = result.getFieldError().getRejectedValue();
            String value = rejectedValue == null ? null : rejectedValue.toString();

            log.error("校验失败 errMsg : {} field : {} value : {}", errMsg, field, value);
            throw new RuntimeException(errMsg);
        }
    }
}