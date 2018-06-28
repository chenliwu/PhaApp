package com.clw.mysdk.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Gson工具类<p>
 * 提供java对象与json格式数据互相转化的功能
 * 
 * @version 1.0.0
 * 
 * @date 2017年7月11日
 *
 * @author chenliwu
 */
public class GsonUtils {
	
	private static Gson gson = new Gson();

	/**
	 * 将java类对象转化成json字符串
	 * 
	 * @param object 转化的java类对象
	 * @return json字符串
	 */
	public static String parseObjectToJson(Object object) {
		return gson.toJson(object);
	}
	
	/**
	 * 将json字符串转化成java对象
	 * 
	 * @param json json字符串
	 * @param clazz 对象的类类型
	 * @return
	 */
	public static <T> T parseJsonToObject(String json, Class<T> clazz) {
		T result = gson.fromJson(json, clazz);
		return result;
	}

	 /**
	  * 将Json数组解析成相应的对象列表
	  * @param json
	  * @param clazz
	  * @return
	  */
	 public static <T> ArrayList<T> parseJsonToArrayList(String json, Class<T> clazz){
	     Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
	     ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
	     ArrayList<T> arrayList = new ArrayList<>();
	     for (JsonObject jsonObject : jsonObjects){
	         arrayList.add(new Gson().fromJson(jsonObject, clazz));
	     }
	     return arrayList;
	}
}
