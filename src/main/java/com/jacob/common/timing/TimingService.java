package com.jacob.common.timing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jacob.bot.util.Const;
import com.jacob.common.entity.Configs;
import com.jacob.common.entity.DdnsLog;
import com.jacob.common.service.IConfigsService;
import com.jacob.common.service.IDdnsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.jacob.common.util.IPUtil.getPublicIP;
import static com.jacob.common.util.IPUtil.updateDDNS;

/**
 * @author jacob
 */
@Component
@Slf4j
public class TimingService {


    private IConfigsService configsService;
    private IDdnsLogService ddnsLogService;

    TimingService(IConfigsService configsService, IDdnsLogService ddnsLogService) {
        this.configsService = configsService;
        this.ddnsLogService = ddnsLogService;
    }

//    @Scheduled(cron = "0 0 0 1/1 * ? ")
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateddns() throws IOException {
        QueryWrapper<Configs> wrapper = new QueryWrapper<>();
        wrapper.eq("`group`", Const.DDNS_GROUP);
        var list = configsService.list(wrapper);
        String username = "", password = "", hostname = "", ip = getPublicIP();
        for (Configs configs : list) {
            switch (configs.getName()) {
                case "username":
                    username = configs.getValue();
                    break;
                case "password":
                    password = configs.getValue();
                    break;
                case "hostname":
                    hostname = configs.getValue();
                default:
                    break;
            }
        }
        String res = updateDDNS(hostname, ip, username, password);
        if (res != null) {
            ddnsLogService.save(new DdnsLog(ip, hostname, res));
            log.info("ip: {},域名:{},更新结果:{}",ip,hostname,res);
        }
    }
}
