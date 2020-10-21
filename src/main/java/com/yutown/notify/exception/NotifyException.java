package com.yutown.notify.exception;

import lombok.Getter;

/**
 * @author dong jing xi
 * @date 2020/10/22 2:05
 **/
public class NotifyException extends RuntimeException {

    @Getter
    private String message;

    public NotifyException(String message) {
        super(message);
        this.message = message;
    }
}
