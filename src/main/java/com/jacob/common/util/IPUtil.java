package com.jacob.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jacob.common.util.HttpClientUtil.get;
import static java.text.MessageFormat.format;

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

    public static String updateDDNS(String hostname,String ip, String username, String password) {
        try {
            if (ip != null) {
                String url = format("https://api.dynu.com/nic/update?hostname={0}&myip={1}&username={2}&password={3}",
                        hostname,
                        ip,
                        username,
                        password);

                return EntityUtils.toString(HttpClientUtil.get(url).getEntity());

            }else {
                return null;
            }
        } catch (IOException e) {
            log.error("获取ip失败!");
            return null;
        }
    }


}
