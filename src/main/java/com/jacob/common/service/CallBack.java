package com.jacob.common.service;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface CallBack {


    void callback(HttpResponse response) throws IOException;
}
