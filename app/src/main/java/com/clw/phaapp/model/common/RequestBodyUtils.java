package com.clw.phaapp.model.common;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author chenliwu
 * @create 2018-03-23 22:07
 **/
public class RequestBodyUtils {

    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }
}
