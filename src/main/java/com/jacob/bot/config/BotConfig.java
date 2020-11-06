package com.jacob.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Data
@Component
@ConfigurationProperties(prefix = "telegram.bot")
public class BotConfig {

    private String token;

    private String name;

    private Proxy proxy;

    @Data

    public static class Proxy {
        private DefaultBotOptions.ProxyType type;
        private String host;
        private Integer port;
    }

    public DefaultBotOptions getOption() {
        DefaultBotOptions options = new DefaultBotOptions();
        options.setProxyType(proxy.type);
        options.setProxyPort(proxy.port);
        options.setProxyHost(proxy.host);
        return options;
    }


}
