package com.jacob.bot;

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
public class DevPollingBot extends BaseAssistantBot implements LongPollingBot {

    private final String name;
    private final String token;

    public DevPollingBot(BotConfig config) {
        super(config.getToken(), config.getName(), config.getOption());
        name = config.getName();
        token = config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(update);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }



}
