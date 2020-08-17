package com.jacob.bot.util;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TgUtil {

    public static User getUser(Update update) {
        var name = update.getMessage().getFrom().getUserName();
        var id = update.getMessage().getFrom().getId();
        return null;
    }
}
