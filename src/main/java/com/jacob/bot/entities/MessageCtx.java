package com.jacob.bot.entities;

import lombok.Data;
import org.apache.catalina.User;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
public class MessageCtx {

    private User user;
    private Long chatId;
    private Update update;
    private String[] args;

    private Command cmd;

    public MessageCtx(User user, Long chatId, Update update, String[] args) {
        this.user = user;
        this.chatId = chatId;
        this.update = update;
        this.args = args;
    }


}
