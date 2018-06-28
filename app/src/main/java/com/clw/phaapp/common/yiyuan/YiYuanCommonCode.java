package com.clw.phaapp.common.yiyuan;

import java.util.HashMap;
import java.util.Map;

/**
 * 易源数据，返回标志,0为成功，其他为失败。
 *
 * @author chenliwu
 * @create 2018-03-17 8:38
 **/
public class YiYuanCommonCode {
    /**
     * 易源返回标志,0为成功，其他为失败。
     0成功
     -1，系统调用错误
     -2，可调用次数或金额为0
     -3，读取超时
     -4，服务端返回数据解析错误
     -5，后端服务器DNS解析错误
     -6，服务不存在或未上线
     -1000，系统维护
     -1002，showapi_appid字段必传
     -1003，showapi_sign字段必传
     -1004，签名sign验证有误
     -1005，showapi_timestamp无效
     -1006，app无权限调用接口
     -1007，没有订购套餐
     -1008，服务商关闭对您的调用权限
     -1009，调用频率受限
     -1010，找不到您的应用
     -1011，子授权app_child_id无效
     -1012，子授权已过期或失效
     -1013，子授权ip受限
     */

    private static Map<Integer,String> map=new HashMap<>();
    static {
        map.put(1,"1，系统调用错误");
        map.put(2,"2，可调用次数或金额为0");
        map.put(3,"3，读取超时");
        map.put(4,"4，服务端返回数据解析错误");
        map.put(5,"5，后端服务器DNS解析错误");
        map.put(6,"6，服务不存在或未上线");
        map.put(1000,"1000，系统维护");
        map.put(1002,"1002，showapi_appid字段必传");
        map.put(1003,"1003，showapi_sign字段必传");
        map.put(1004,"1004，签名sign验证有误");
        map.put(1005,"1005，showapi_timestamp无效");
        map.put(1006,"1006，app无权限调用接口");
        map.put(1007,"1007，没有订购套餐");
        map.put(1008,"1008，服务商关闭对您的调用权限");
        map.put(1009,"1009，调用频率受限");
        map.put(1010,"1010，找不到您的应用");
        map.put(1011,"1011，子授权app_child_id无效");
        map.put(1012,"1012，子授权已过期或失效");
        map.put(1013,"1013，子授权ip受限");
    }

    /**
     * 获取错误信息
     * @param code
     * @return
     */
    public static String getErrorMessage(int code){
        return map.get(code);
    }


}
