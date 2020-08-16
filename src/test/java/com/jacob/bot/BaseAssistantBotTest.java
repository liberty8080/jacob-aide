package com.jacob.bot;

import com.jacob.bot.config.BotConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BaseAssistantBotTest {

    Logger log = LoggerFactory.getLogger(BaseAssistantBotTest.class);

    @Autowired
    BotConfig config;

    @Test
    void getProperty() {
        log.info("属性文件测试");
        assertNotNull(config.getToken(), "test");
        assertNotNull(config.getProxy(), "proxy");
        log.info("proxy: " + config.getProxy());
    }

}