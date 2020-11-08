package com.jacob.common.util;

import com.jacob.common.CallBack;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    //域名,参数,成功回调函数
    public static HttpResponse get(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;

            if (httpClient.execute(get).getStatusLine().getStatusCode() == 200) {
                response = httpClient.execute(get);
            } else {
                log.error("get请求失败! url:{}", url);
            }
        return response;
    }

    public static void post(String url, Map<String, Object> param, CallBack callBack) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        Map<String, Object> map = null;
    }


}
