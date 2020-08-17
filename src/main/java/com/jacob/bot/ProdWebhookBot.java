package com.jacob.bot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.WebhookBot;

@Component
@ConditionalOnProperty(prefix = "spring.profile",name = "active",havingValue = "prod")
public class ProdWebhookBot extends BaseAssistantBot implements WebhookBot {
    public ProdWebhookBot(String botToken, String botUsername, DefaultBotOptions options) {
        super(botToken, botUsername, options);
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {

    }

    @Override
    public String getBotPath() {
        return null;
    }
}
