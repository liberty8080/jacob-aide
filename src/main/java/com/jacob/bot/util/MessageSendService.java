package com.jacob.bot.util;

import com.jacob.bot.DevPollingBot;
import com.jacob.common.service.impl.ConfigsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

/**
 * @author jacob
 */
@Component
public class MessageSendService {

    @Autowired
    ConfigsServiceImpl configs;
    @Autowired
    private DevPollingBot bot;

    public Optional<Message> sendToChannel(String message) {
        return bot.silent.send(message, getChannelId());
    }

    private long getChannelId() {
        return Long.parseLong(configs.getChannelId());
    }

}
