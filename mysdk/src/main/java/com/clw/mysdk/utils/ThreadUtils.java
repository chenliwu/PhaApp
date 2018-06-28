package com.clw.mysdk.utils;

import android.os.Handler;

/**
 * 线程工具类
 * 
 * @version 1.0.0
 * 
 * @date 2017-7-14 下午4:42:02
 *
 * @author chenliwu
 */
public class ThreadUtils {

	private static Handler handler=new Handler();

	/**
	 * 启动一个子线程
	 * @param runnable
	 */
	public static void runInChildThread(Runnable runnable){
		new Thread(runnable).start();
	}
	

	/**
	 * 在子线程中执行UI操作
	 * @param runnable
	 */
	public static void runInUIThread(Runnable runnable){
		handler.post(runnable);
	}
	
}
