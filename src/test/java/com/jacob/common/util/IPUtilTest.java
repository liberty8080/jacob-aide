package com.jacob.common.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IPUtilTest {

    @Test
    void getPublicIP() throws IOException {
        System.out.println(IPUtil.getPublicIP());
    }


}