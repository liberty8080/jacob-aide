package com.jacob.bot;

import com.jacob.bot.entities.Command;
import com.jacob.bot.entities.MessageCtx;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.jacob.bot.entities.Command.builder;

/**
 * 机器人基类，两个子类用于根据配置切换longPolling与webhook模式
 */
@Component
@Slf4j
public abstract class BaseAssistantBot extends DefaultAbsSender {

    @Getter
    private final String botToken;
    @Getter
    private final String botUsername;

    @Setter
    private MessageSender sender;
    @Setter
    private SilentSender silent;

    //注册命令
    private final Map<String, Command> commands = new HashMap<>();


    /**
     * @param botToken    bot密钥
     * @param botUsername bot 用户名
     * @param options     是否使用代理
     */
    BaseAssistantBot(String botToken, String botUsername, DefaultBotOptions options) {
        super(options);
        this.botToken = botToken;
        this.botUsername = botUsername;
        sender = new DefaultSender(this);
        silent = new SilentSender(sender);
        registerCommands();
    }


    /**
     * @param update 处理子类传过来的update
     */
    public void onUpdateReceived(Update update) {
        // TODO: 2020/8/17 让机器人以回复的形式返回结果 
        Stream.of(update)
                .filter(this::isCommand)
                .map(this::getContext)
                .map(this::matchCommand)
                .filter(this::hasCommand)
                .forEach(ctx ->
                        ctx.getCmd().getAction().accept(ctx)
                );
    }

    private MessageCtx getContext(Update update) {
        // TODO: 2020/8/16 用户信息,参数待完善
        return new MessageCtx(null, update.getMessage().getChatId(), update, null);
    }

    public MessageCtx matchCommand(MessageCtx ctx) {
        var receivedMsg = ctx.getUpdate().getMessage().getText().substring(1);
        for (var cmd : commands.keySet()) {
            if (cmd.equals(receivedMsg)) {
                ctx.setCmd(commands.get(cmd));
            } else {
                log.info("no matching command");
            }
        }
        return ctx;
    }

    protected boolean hasCommand(MessageCtx ctx) {
        var chatId = ctx.getChatId();
        if (ctx.getCmd() != null) {
            return true;
        } else {
            silent.send("没有匹配的命令！", chatId);
        }
        return ctx.getCmd() != null;
    }


    /**
     * @param update 过滤非命令的update
     * @return boolean
     */
    private boolean isCommand(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().startsWith("/")) {
                return true;
            } else {
                silent.send("not command ", update.getMessage().getChatId());
                return false;
            }
        } else {
            silent.send("not text , can't recognize", update.getMessage().getChatId());
            return false;
        }
    }

    public Command json() {
        return builder()
                .name("json")
                .Description("将接收到的update转换为JSON对象返回")
                .action(ctx -> silent.send(new JSONObject(ctx).toString(), ctx.getChatId()))
                .build();
    }


    /**
     * 扫描返回值为的Command函数，,加入集合
     * todo: 增强扩展性，将Command集中到单独的类中
     */
    void registerCommands() {
        Stream.of(getClass().getMethods())
                .filter(checkReturnTypeCommand(Command.class))
                .map(invokeCommand(this))
                .forEach(command -> commands.put(command.getName(), command));
    }

    /**
     * Function 接受Method，返回Command
     *
     * @param obj this
     * @return 返回一个函数式接口的lambda方法
     */
    private Function<Method, Command> invokeCommand(Object obj) {

        return method -> {
            try {
                return (Command) method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("invoke Command failed！", e);
                throw new RuntimeException(e);
            }

        };
    }

    /**
     * @param clazz 待筛选的类
     * @return 如果clazz是method返回值的父类，返回true
     */
    public Predicate<Method> checkReturnTypeCommand(Class<?> clazz) {
        return method -> clazz.isAssignableFrom(method.getReturnType());
    }


}
