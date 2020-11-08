package com.jacob.common;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface CallBack {


    void callback(HttpResponse response) throws IOException;
}
