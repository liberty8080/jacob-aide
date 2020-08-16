package com.jacob.bot.Command;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class Command {

    private String name;
    private String description;
    private Consumer<MessageCtx> action;

}
