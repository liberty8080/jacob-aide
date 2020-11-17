package com.jacob.bot.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author jacob
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value =Exception.class)
    public String exceptionHandler(Exception e){
        System.out.println("未知异常！原因是:"+e);
        return e.getMessage();
    }
}
