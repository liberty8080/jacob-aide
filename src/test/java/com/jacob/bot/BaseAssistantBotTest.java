package com.jacob.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.bot.config.BotConfig;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

//@SpringBootTest
class BaseAssistantBotTest {

    Logger log = LoggerFactory.getLogger(BaseAssistantBotTest.class);

    //    @Autowired
    BotConfig config;

    private BaseAssistantBot bot;
    private SilentSender silent;

    @BeforeEach
    public void setUp() {
        config = new BotConfig();
        config.setToken("1");
        config.setName("name");
        BotConfig.Proxy proxy = new BotConfig.Proxy();
        proxy.setHost("127.0.0.1");
        proxy.setPort(10808);
        proxy.setType(DefaultBotOptions.ProxyType.SOCKS5);
        config.setProxy(proxy);
        bot = new DevPollingBot(config);
        silent = mock(SilentSender.class);
        bot.setSilent(silent);
    }

    @Test
    void getProperty() {
        log.info("属性文件测试");
        assertNotNull(config.getToken(), "test");
        assertNotNull(config.getProxy(), "proxy");
        log.info("proxy: " + config.getProxy());
    }

    @Test
    void updateReceiveTest() throws JsonProcessingException {
        String s = "{\"updateId\":956769446,\"message\":{\"date\":1597586737,\"newChatMembers\":[],\"channelMessage\":false,\"chatId\":551322172,\"messageId\":60,\"command\":true,\"userMessage\":true,\"superGroupMessage\":false,\"entities\":[{\"offset\":0,\"length\":8,\"text\":\"/chat_id\",\"type\":\"bot_command\"}],\"chat\":{\"lastName\":\"Fyre\",\"firstName\":\"Jacob\",\"channelChat\":false,\"groupChat\":false,\"userChat\":true,\"id\":551322172,\"superGroupChat\":false,\"userName\":\"Jacobzjw\"},\"from\":{\"lastName\":\"Fyre\",\"firstName\":\"Jacob\",\"bot\":false,\"id\":551322172,\"userName\":\"Jacobzjw\",\"languageCode\":\"zh-hans\"},\"text\":\"/chat_id\",\"reply\":false,\"groupMessage\":false}}";

        JSONObject jsonObject = new JSONObject(s);
        ObjectMapper mapper = new ObjectMapper();
        Update update = mapper.readValue(s, Update.class);
        bot.onUpdateReceived(update);

    }

/*    @Test
    void initCommandTest(){
        bot.registerCommands();
    }*/


}