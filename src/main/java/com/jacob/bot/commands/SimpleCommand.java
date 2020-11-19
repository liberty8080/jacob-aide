package com.jacob.bot.commands;

import com.jacob.bot.core.BaseAssistantBot;
import com.jacob.bot.annotation.Commands;
import com.jacob.bot.annotation.NormalCommand;
import com.jacob.bot.entities.MessageCtx;
import com.jacob.bot.util.MessageSendService;
import com.jacob.common.service.impl.DdnsLogServiceImpl;
import com.jacob.common.service.impl.V2SubscribeService;
import com.jacob.common.util.IPUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author jacob
 */
@Component
@Commands
public class SimpleCommand {
    private static final Logger log = LoggerFactory.getLogger(SimpleCommand.class);
    public static SimpleCommand command;
    private final DdnsLogServiceImpl ddnsLogService;
    @Autowired
    public MessageSendService messageSendService;
    @Autowired
    private V2SubscribeService v2Service;

    @Autowired
    SimpleCommand(DdnsLogServiceImpl ddnsLogService) {
        this.ddnsLogService = ddnsLogService;
    }

    @PostConstruct
    public void init() {
        command = this;
    }

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

    @NormalCommand(name = "ddns", description = "更新ddns")
    public String ddns(MessageCtx ctx) {
        try {
            return ddnsLogService.updateddns();

        } catch (IOException e) {
            return "ddns更新失败!";
        }
    }

    @NormalCommand(name = "help", description = "使用帮助")
    public String help(MessageCtx ctx) {

        return BaseAssistantBot.HELP_INFO.toString();
    }

    @NormalCommand(name="expire",description = "过期时间检测")
    public String expire(MessageCtx ctx){
        String message ;
        try {
            int expireDays = v2Service.getExpireDays();
            message = MessageFormat.format("还有{0}天就过期辣", expireDays);
            return message;
        } catch (IOException e) {
            message  = "v2信息获取错误";
            log.error(message,e);
            return message;
        }
    }





}
