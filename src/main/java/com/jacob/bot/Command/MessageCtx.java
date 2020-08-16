package com.jacob.bot.Command;

import lombok.Data;
import org.apache.catalina.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

@Data
public class MessageCtx {

    private User user;
    private Long chatId;
    private Update update;
    private String[] args;
}
