package com.clw.phaapp.common.entity;

import com.clw.mysdk.base.BaseBean;

/**
 * 接口返回的result类（普通json响应实体）<p>
 * 一般作为响应主体（ @ResponseBody）返回给浏览器客户端进行处理，客户端使用Ajax进行处理<p>
 * <p>客户使用方法</p>
 * 通过“对象名.属性名”来获得相应数据，根据数据进行处理
 */
public class ResultEntity extends BaseBean {
    /**
     * 状态码，推荐参考http响应码进行设置，以下是HTTP常见错误状态代码<p>
     * 200：响应成功。<p>
     * 400：错误的请求。客户端发送的HTTP请求不正确。<p>
     * 404：文件不存在，在服务器上没有客户端请求访问的文档。<p>
     * 405：服务器不支持客户端的请求方式。<p>
     * 500：服务器内部错误。<p>
     */
    private int state;

    /**
     * message为接口返回的个人编的提示消息；状态信息，返回错误的时候进行设置以方便调试。
     */
    private String message;

    /**
     * 具体数据
     */
    private Object data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
