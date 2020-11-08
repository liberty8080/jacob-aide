package com.jacob.common.service.impl;

import com.jacob.common.entity.Configs;
import com.jacob.common.mapper.ConfigsMapper;
import com.jacob.common.service.IConfigsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-08
 */
@Service
public class ConfigsServiceImpl extends ServiceImpl<ConfigsMapper, Configs> implements IConfigsService {

}
