package com.clw.mysdk.base;

import com.clw.mysdk.utils.GsonUtils;

import java.io.Serializable;

public abstract class BaseBean implements Serializable{

    /**
     * 将对象转化成JSON字符串
     * @return
     */
    public String toJson(){
        return GsonUtils.parseObjectToJson(this);
    }

    /**
     * 将JSON字符串转化成Java对象
     * 使用方法：   User user=new User();  user=(User) user.fromJson(json);
     * @param json
     */
    public Object fromJson(String json){
        return GsonUtils.parseJsonToObject(json,this.getClass());
    }

}
