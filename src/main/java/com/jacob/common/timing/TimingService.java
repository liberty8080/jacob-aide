package com.jacob.common.timing;

import com.jacob.bot.util.MessageSendService;
import com.jacob.common.service.impl.DdnsLogServiceImpl;
import com.jacob.common.service.impl.V2SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author jacob
 */
@Component
@Slf4j
public class TimingService {


    @Autowired
    public MessageSendService messageSendService;
    @Resource
    private DdnsLogServiceImpl ddnsLogService;
    @Resource
    private V2SubscribeService v2Service;

    /**
     * 定时更新ddns信息
     *
     * @throws IOException
     */
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void updateddns() throws IOException {
        ddnsLogService.updateddns();
    }

    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void remindV2ExpireDays() {
        //剩一周提示
        int remindDay = 7;
        try {
            int expireDays = v2Service.getExpireDays();
            if (expireDays != -1 && expireDays <= remindDay) {
                String message = MessageFormat.format("还有{0}天就过期辣,快tm续费啊", expireDays);
                messageSendService.sendToChannel(message);
            } else {
                log.info("还有{}天过期",expireDays);
            }
        } catch (IOException e) {
            log.error("v2信息获取错误", e);
            messageSendService.sendToChannel("v2信息获取错误");
        }
    }
}
