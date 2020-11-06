package com.jacob.bot.entities;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class Command {

    private String name;
    private String description;
    private Consumer<MessageCtx> action;

    public Command(String name, String description, Consumer<MessageCtx> action) {
        this.name = name;
        this.description = description;
        this.action = action;
    }

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {
        private String name;
        private String description;
        private Consumer<MessageCtx> action;

        public CommandBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CommandBuilder Description(String description) {
            this.description = description;
            return this;
        }

        public CommandBuilder action(Consumer<MessageCtx> consumer) {
            this.action = consumer;
            return this;
        }

        public Command build() {
            return new Command(name, description, action);
        }
    }


}
