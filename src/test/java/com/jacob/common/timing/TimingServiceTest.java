package com.jacob.common.timing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TimingServiceTest {

    @Autowired
    TimingService service;

    @Test
    void updateddns() throws IOException {
        service.updateddns();
    }
}