package com.jacob.bot.commands;

import com.jacob.bot.annotation.NormalCommand;
import com.jacob.bot.entities.MessageCtx;
import org.json.JSONObject;

/**
 * @author jacob
 */
public class SimpleCommand {

    /**
     *
     * @param ctx messageCtx
     * @return 返回给tg的
     */
    @NormalCommand(name = "json",description = "将msgContext转为json返回")
    public String json(MessageCtx ctx){
        return new JSONObject(ctx).toString();
    }

    @NormalCommand(name = "wdnmd", description = "wdnmd")
    public String wdnmd(MessageCtx ctx){
        return "wdnmd";
    }



}
