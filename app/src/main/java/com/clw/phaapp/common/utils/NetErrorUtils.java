package com.clw.phaapp.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clw
 * @create 2018-02-27 22:00
 **/
public class NetErrorUtils {

    private static Map<String,String> map=new HashMap<>();

    static {
        map.put("connect timed out","服务器抽风了，请稍后再试");
    }

    /**
     * 获取网络错误信息
     * @param throwable
     * @return
     */
    public static String getErrorMessage(Throwable throwable){
        String msg=map.get(throwable.getMessage());
        if(msg!=null){
            return msg;
        }
        return throwable.getMessage();
    }

}
