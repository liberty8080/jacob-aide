package com.jacob.bot;

import com.jacob.bot.annotation.NormalCommand;
import com.jacob.bot.config.BotConfig;
import com.jacob.bot.entities.Command;
import com.jacob.bot.entities.MessageCtx;
import com.jacob.bot.util.AnnotationUtil;
import com.jacob.bot.util.Const;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.jacob.bot.entities.Command.builder;
import static com.jacob.bot.util.Const.COMMAND_FLAG;

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
    /**
     * 反射获得命令
     */
    private final Map<Class<? extends Annotation>, Set<Method>> commandsMap = registerCommandsByAnnotation();
    @Setter
    private MessageSender sender;
    @Setter
    private SilentSender silent;

    @Autowired
    ApplicationContext context;


    BaseAssistantBot(BotConfig config){
        this(config.getToken(),config.getName(),config.getOption());
    }
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
        // TODO: 2020/11/09 参数待完善
        return new MessageCtx(update.getMessage().getFrom(), update.getMessage().getChatId(), update, null);
    }

    public MessageCtx matchCommand(MessageCtx ctx) {
        var receivedMsg = ctx.getUpdate().getMessage().getText().substring(1);
        for (Method method : commandsMap.get(NormalCommand.class)) {
            String name = method.getAnnotation(NormalCommand.class).name();
            if (receivedMsg.equals(name)) {
                String desc = method.getAnnotation(NormalCommand.class).description();
                if (method.getReturnType().equals(String.class)) {
                    Command command = new Command(name, desc,
                            messageCtx -> {
                                try {
                                    //通过反射调用方法,拿取返回的信息,发送给用户
                                    Object obj = context.getBean(method.getDeclaringClass());
                                    silent.send((String)method.invoke(obj, messageCtx), messageCtx.getChatId());
                                    log.info("\n用户: {} id:{} \n命令: {}",ctx.getUser().getUserName(),ctx.getUser().getId(),name);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    log.error("命令调用失败! class:{}, method:{},command:{}", method.getDeclaringClass().getSimpleName(),
                                            method.getName(), receivedMsg,e);
                                }
                            });
                    ctx.setCmd(command);
                } else {
                    // TODO: 2020/11/6 更多形式的返回
                    log.warn("暂不支持");
                }
                break;
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
            if (update.getMessage().getText().startsWith(COMMAND_FLAG)) {
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




    protected Map<Class<? extends Annotation>, Set<Method>> registerCommandsByAnnotation() {
        String pkgName = Const.COMMAND_SCAN_PKG;
        Set<Class<?>> classes = AnnotationUtil.scanClasses(pkgName, AnnotationUtil.getPkgPath(pkgName), true);
        return AnnotationUtil.scanMethodsByAnnotations(classes, Collections.singletonList(NormalCommand.class));
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




}
