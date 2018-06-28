package com.clw.phaapp.model.healthinfo;

/**
 * 易源数据，健康资讯分类，返回结果实体类
 *
 * @author chenliwu
 * @create 2018-03-14 20:57
 **/
public class YiYuanHealthInfoResultEntity {

    /**
     * 消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
     */
    private String showapi_res_body;

    /**
     * 易源返回标志,0为成功，其他为失败。
     0成功
     -1，系统调用错误
     -2，可调用次数或金额为0
     -3，读取超时
     -4，服务端返回数据解析错误
     -5，后端服务器DNS解析错误
     -6，服务不存在或未上线
     */
    private int showapi_res_code;

    /**
     * 错误信息的展示
     */
    private String showapi_res_error;

    public String getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(String showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    @Override
    public String toString() {
        return "YiYuanHealthInfoResultEntity{" +
                "showapi_res_body='" + showapi_res_body + '\'' +
                ", showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                '}';
    }
}
