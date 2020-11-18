package com.jacob.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jacob.common.entity.Configs;
import com.jacob.common.mapper.ConfigsMapper;
import com.jacob.common.service.IConfigsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-08
 */
@Service
public class ConfigsServiceImpl extends ServiceImpl<ConfigsMapper, Configs> implements IConfigsService {


    public String getChannelId() {
        QueryWrapper<Configs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "channelId");
        Configs config = getOne(queryWrapper);
        return config.getValue();
    }

    public String getSubscribeLink() {
        QueryWrapper<Configs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","v2Subscribe");
        Configs config = getOne(queryWrapper);
        return config.getValue();
    }
}
