package com.jacob.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jacob.common.util.Base64Util;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.jacob.common.util.HttpClientUtil.get;

/**
 * @author jacob
 */
@Service
public class V2SubscribeService {

    @Autowired
    private ConfigsServiceImpl configs;



    /**
     * 检测订阅过期时间
     *
     * @return 距离过期的天数
     * @throws IOException e
     */
    public int getExpireDays() throws IOException {
        int expireInfo = -1;
        String link = configs.getSubscribeLink();
        String result = EntityUtils.toString(get(link).getEntity());
        String decodedResult = Base64Util.decode(result);
        List<String> vmess = getVmess(decodedResult);
        for (String s : vmess) {
            JSONObject object = JSONObject.parseObject(s);
            String ps = object.getString("ps");
            if (ps.contains("过期时间")) {
                ps = ps.replace("过期时间：", "");
                LocalDate now = LocalDate.now();
                LocalDate expire = LocalDate.parse(ps);
                Period period = Period.between(now, expire);
                expireInfo = period.getDays();
                break;
            }
        }

        return expireInfo;
    }


    /**
     * 解码vmess
     *
     * @param encodedVmess 未解码的
     * @return 已解码的
     */
    private List<String> getVmess(String encodedVmess) {
        String[] tmp = encodedVmess.split("\n");
        List<String> decodedVmess = new ArrayList<>();
        for (String s : tmp) {
            s = s.replace("vmess://", "");
            s = Base64Util.decode(s);
            decodedVmess.add(s);
        }
        return decodedVmess;
    }


}
