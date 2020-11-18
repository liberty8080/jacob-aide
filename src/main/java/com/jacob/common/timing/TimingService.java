package com.jacob.common.timing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jacob.bot.util.Const;
import com.jacob.bot.util.MessageSendService;
import com.jacob.common.entity.Configs;
import com.jacob.common.entity.DdnsLog;
import com.jacob.common.service.IConfigsService;
import com.jacob.common.service.IDdnsLogService;
import com.jacob.common.service.impl.DdnsLogServiceImpl;
import com.jacob.common.service.impl.V2SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;

import static com.jacob.common.util.IPUtil.getPublicIP;
import static com.jacob.common.util.IPUtil.updateDDNS;

/**
 * @author jacob
 */
@Component
@Slf4j
public class TimingService {


    @Resource
    private DdnsLogServiceImpl ddnsLogService;

    @Resource
    private V2SubscribeService v2Service;

    @Autowired
    public MessageSendService messageSendService;

    /**
     * 定时更新ddns信息
     * @throws IOException
     */
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void updateddns() throws IOException {
        ddnsLogService.updateddns();
    }

    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void remindV2ExpireDays(){
        //剩一周提示
        int remindDay = 7;
        try {
           int expireDays =  v2Service.getExpireDays();
            if(expireDays<=remindDay&&expireDays!=-1){
                String message = MessageFormat.format("还有{0}天就过期辣,快tm续费啊", expireDays);
                messageSendService.sendToChannel(message);
            }else {
                log.error("v2信息获取失败");
                messageSendService.sendToChannel("v2信息获取失败");
            }
        } catch (IOException e) {
            log.error("v2信息获取出错",e);
            messageSendService.sendToChannel("v2信息获取错误");
        }
    }
}
