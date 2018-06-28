package com.clw.phaapp.app;

public class ServerInformation {

    /**
     * 服务器名称
     */
    public static final String SERVER_NAME="PHA_APP_Server";

    /**
     * 服务器IP地址
     */
    //public static final String SERVER_IP="10.0.2.2";
    public static final String SERVER_IP="120.79.55.65";

    /**
     * 服务器端口号
     */
    public static final String SERVER_PORT="8080";


    /**
     * 服务器URL
     */
    public static final String BASE_URL=
            String.format("http://%s:%s/%s/",SERVER_IP,SERVER_PORT,SERVER_NAME);


    ///////////////////   请求URL   ////////////////////////////////



}
