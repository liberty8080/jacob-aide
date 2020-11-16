package com.jacob.bot;

import com.jacob.bot.config.BotConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootTest
public class EncyptTest {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    BotConfig config;

    @BeforeAll
    public static void bef(){
        ApiContextInitializer.init();
    }

    @Value("${jasypt.encryptor.password}")
    String salt;

    @Test
    void encrypt() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(salt);

        log.error("\ntoken: \n原: {}\n现: {}", config.getToken(), config.getToken());
        log.error("\nusername 原: {}\n现: {}", config.getName(), textEncryptor.encrypt(config.getName()));
    }
}
