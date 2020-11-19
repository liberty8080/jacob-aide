package com.jacob.bot.core;

import com.jacob.bot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

/**
 * @author Administrator
 */
@Component
//@ConditionalOnProperty(prefix = "spring.profile",name = "active",havingValue = "dev")
public class DevPollingBot extends BaseAssistantBot implements LongPollingBot {

    private final BotConfig config;

    public DevPollingBot(BotConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(update);
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }


}
