package com.jacob.bot;

import com.jacob.bot.Command.Command;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public abstract class BaseAssistantBot extends DefaultAbsSender {
    @Getter
    private final String botToken;
    @Getter
    private final String botUsername;

    //注册命令
    private final Map<String, Command> commands = new HashMap<>();


    BaseAssistantBot(String botToken, String botUsername, DefaultBotOptions options) {
        super(options);
        this.botToken = botToken;
        this.botUsername = botUsername;
    }


}
