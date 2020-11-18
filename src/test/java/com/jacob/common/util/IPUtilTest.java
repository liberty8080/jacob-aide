package com.jacob.common.util;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class IPUtilTest {

    @Test
    void getPublicIP() throws IOException {
        System.out.println(IPUtil.getPublicIP());
    }
    //todo:测试魔术包唤醒
    @Test
    public static void magicPacketWakeUp() throws IOException {
        int port = 20105;
        String macAddress = "01-12-43-44-D5-56";
        // String destIP = "255.255.255.255";// 广播地址
        String destIP = "112.11.15.28";// 广播地址
        // 检测 mac 地址,并将其转换为二进制
        byte[] destMac = getMacBytes(macAddress);
        if (destMac == null)
            return;

        InetAddress destHost = InetAddress.getByName(destIP);
        // construct packet data
        byte[] magicBytes = new byte[102];
        // 将数据包的前6位放入0xFF即 "FF"的二进制
        for (int i = 0; i < 6; i++)
            magicBytes[i] = (byte) 0xFF;
        // 从第7个位置开始把mac地址放入16次
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < destMac.length; j++) {
                magicBytes[6 + destMac.length * i + j] = destMac[j];
            }
        }
        // create packet
        DatagramPacket dp;
        dp = new DatagramPacket(magicBytes, magicBytes.length, destHost, port);
        DatagramSocket ds = new DatagramSocket();
        ds.send(dp);
        ds.close();
        System.out.println("ok");
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("([:\\-])");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }

    @Test
    public void getMacBytesTest(){
        String mac1 = "a4:4b:d5:92:96:bb";
        assertEquals(6,getMacBytes(mac1).length);;
        assertEquals(6,getMacBytes("01-12-43-44-D5-56").length);
    }


}