package com.jacob.common.services;

import com.jacob.common.util.HttpClientUtil;
import com.jacob.common.util.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

public class TimingService {

    private static final Logger log = LoggerFactory.getLogger(TimingService.class);

    @Scheduled
    public void updateDDNS() {



    }
}
