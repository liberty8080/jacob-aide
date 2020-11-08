package com.jacob.common.timing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jacob.bot.util.Const;
import com.jacob.common.entity.Configs;
import com.jacob.common.entity.DdnsLog;
import com.jacob.common.service.IConfigsService;
import com.jacob.common.service.IDdnsLogService;
import com.jacob.common.service.impl.DdnsLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

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

    /**
     * 定时更新ddns信息
     * @throws IOException
     */
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void updateddns() throws IOException {
        ddnsLogService.updateddns();
    }
}
