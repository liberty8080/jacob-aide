package com.jacob.bot.commands;

import com.jacob.bot.annotation.NormalCommand;
import com.jacob.bot.entities.MessageCtx;
import com.jacob.common.util.IPUtil;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.jacob.common.util.HttpClientUtil.get;

/**
 * @author jacob
 */
public class SimpleCommand {
    private static final Logger log = LoggerFactory.getLogger(SimpleCommand.class);

    /**
     * @param ctx messageCtx
     * @return 返回给tg的
     */
    @NormalCommand(name = "json", description = "将msgContext转为json返回")
    public String json(MessageCtx ctx) {
        return new JSONObject(ctx).toString();
    }

    @NormalCommand(name = "ip", description = "返回公网ip")
    public String ip(MessageCtx ctx) {
        try {
            String ip = IPUtil.getPublicIP();
            return ip == null ? "ip获取失败!" : ip;
        } catch (IOException e) {
            log.error("ip获取失败!\n", e);
            return "ip获取失败!";
        }
    }


}
