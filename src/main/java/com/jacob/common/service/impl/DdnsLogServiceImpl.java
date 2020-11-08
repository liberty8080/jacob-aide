package com.jacob.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jacob.bot.util.Const;
import com.jacob.common.entity.Configs;
import com.jacob.common.entity.DdnsLog;
import com.jacob.common.mapper.DdnsLogMapper;
import com.jacob.common.service.IConfigsService;
import com.jacob.common.service.IDdnsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.jacob.common.util.IPUtil.getPublicIP;
import static com.jacob.common.util.IPUtil.updateDDNS;

/**
 * <p>
 * ddns更新日志 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-08
 */
@Service
@Slf4j
public class DdnsLogServiceImpl extends ServiceImpl<DdnsLogMapper, DdnsLog> implements IDdnsLogService {

    private IConfigsService configsService;

    @Autowired
    DdnsLogServiceImpl(IConfigsService configsService) {
        this.configsService = configsService;
    }


    public String updateddns() throws IOException {
        QueryWrapper<Configs> wrapper = new QueryWrapper<>();
        wrapper.eq("`group`", Const.DDNS_GROUP);
        var list = configsService.list(wrapper);
        String username = "", password = "", hostname = "", res = "", ip = getPublicIP();
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
        res = updateDDNS(hostname, ip, username, password);
        if (res != null) {
            save(new DdnsLog(ip, hostname, res));
            log.info("ip: {},域名:{},更新结果:{}", ip, hostname, res);
        }
        return res;
    }
}
