package com.jacob.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jacob.common.util.HttpClientUtil.get;
import static java.text.MessageFormat.*;

@Slf4j
public class IPUtil {

    public static String getPublicIP() throws IOException {
        String ip = EntityUtils.toString(get("http://ip.cip.cc").getEntity()).trim();
        Pattern pattern = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        Matcher matcher = pattern.matcher(ip);
        if (matcher.matches()) {
            return ip;
        } else {
            return null;
        }
    }

    public static Boolean updateDDNS(String hostname, String ip, String username, String password) {
        log.info("检测公网ip地址中...");
        try {
            if (ip != null) {
                String url = format("https://api.dynu.com/nic/update?hostname={0}&myip={1}&username={2}&password={3}",
                        hostname,
                        ip,
                        username,
                        password);

                var res = EntityUtils.toString(HttpClientUtil.get(url).getEntity());
                if (res.equals("nochg") || res.equals("good")) {
                    log.info("ddns更新成功! {}", res);
                } else {
                    log.error("ddns更新失败! {}", res);
                }
            } else {
                log.error("ip获取失败!");
            }
        } catch (IOException e) {
            log.error("获取ip失败!");
        }
        return true;
    }
}
