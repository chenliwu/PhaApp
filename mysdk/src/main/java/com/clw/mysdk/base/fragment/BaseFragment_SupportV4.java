package com.clw.mysdk.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.clw.mysdk.R;
import com.clw.mysdk.utils.SharedPreferencesUtils;

/**
 * Fragment基类
 * <p>
 * 注意：导入的包是support.v4.app.Fragment;
 * 
 * @version 1.0.0
 * 
 * @date 2017-7-13 下午12:47:48
 * 
 * @author chenliwu
 */
public abstract class BaseFragment_SupportV4 extends Fragment {
	
	private final String TAG="BaseFragment_SupportV4";

	/**
	 * 基础视图
	 */
	protected View mBaseView;
	private Toast mToast;

	/**
	 * 上下文
	 */
	protected Context mContext;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=getActivity();
	}
	
	/**
	 * 初始化Fragment
	 */
	protected abstract void initFragment();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 进入指定的Activity
	 * @param cls
	 */
	protected void startActivity(Class<?> cls){
		Intent intent=new Intent(mContext,cls);
		startActivity(intent);
	}

	/**
	 * 写入boolean变量至SharedPreferences中
	 * @param key
	 * @param value
	 */
	protected void putBooleanToSharedPreferences(String key, boolean value){
		if(mContext!=null){
			SharedPreferencesUtils.putBoolean(mContext,key,value);
		}
	}

	/**
	 * 读取boolean变量从SharedPreferences中
	 * @param key
	 * @param defValue
	 */
	protected boolean getBooleanFromSharedPreferences(String key, boolean defValue){
		if(mContext!=null){
			return SharedPreferencesUtils.getBoolean(mContext,key,defValue);
		}
		return defValue;
	}

	/**
	 * 获取String变量
	 * @param key
	 * @param defValue
	 * @return
	 */
	protected String getStringFromSharedPreferences(String key,String defValue){
		if(mContext!=null){
			return SharedPreferencesUtils.getString(mContext,key,defValue);
		}
		return defValue;
	}

	/**
	 * 写入String变量至SharedPreferences中
	 * @param key
	 * @param defValue
	 */
	protected void putStringToSharedPreferences(String key,String defValue){
		if(mContext!=null){
			SharedPreferencesUtils.putString(mContext,key,defValue);
		}
	}

	/**
	 * 读取int变量
	 * @param key
	 * @param defValue
	 * @return
	 */
	protected int getIntFromSharedPreferences(String key,int defValue){
		if(mContext!=null){
			return SharedPreferencesUtils.getInt(mContext,key,defValue);
		}
		return defValue;
	}

	/**
	 * 写入int变量至SharedPreferences中
	 * @param key
	 * @param defValue
	 */
	protected void putIntToSharedPreferences(String key,int defValue){
		if(mContext!=null){
			SharedPreferencesUtils.putInt(mContext,key,defValue);
		}
	}




	/**
	 * 顶部栏
	 */
	protected Toolbar mToolbar;
	/**
	 * 顶部栏居中标题
	 */
	protected TextView mToolbarCenterTitle;
	/**
	 * 顶部栏右边标题
	 */
	protected TextView mToolbarRightTitle;


	/**
	 * 初始化Toolbar控件
	 */
	protected void initToolbarView(){
		mToolbar=(Toolbar)mBaseView.findViewById(R.id.toolbar_back);
		mToolbarCenterTitle=(TextView)mBaseView.findViewById(R.id.toolbar_center_title);
		mToolbarRightTitle=(TextView)mBaseView.findViewById(R.id.toolbar_right_title);
		//调用etSupportActionBar后需要设置toolbar标题为空，不然就会显示app名称
		mToolbar.setTitle("");
		//设置成为顶部栏，菜单才会出现
		//setSupportActionBar(mToolbar);
	}

	/**
	 * 设置搜索式顶部栏
	 */
	protected void initSearchToolbar(){
		initToolbarView();
		if(mToolbar!=null){
			//设置顶部栏的导航图标
			mToolbar.setNavigationIcon(R.drawable.ic_search_white_24dp);
		}
	}


	/**
	 * 设置顶部栏的标题
	 * @param title 标题
	 */
	protected void setToolbarCenterTitle(CharSequence title){
		if(mToolbarCenterTitle!=null){
			mToolbarCenterTitle.setText(title);
		}else{
			initToolbarView();
			setToolbarCenterTitle(title);
		}
	}

	/**
	 * 设置顶部栏的标题
	 * @param resourceID 资源ID
	 */
	protected void setToolbarCenterTitle(int resourceID){
		if(mToolbarCenterTitle!=null){
			mToolbarCenterTitle.setText(resourceID);
		}else{
			initToolbarView();
			setToolbarCenterTitle(resourceID);
		}
	}

	/**
	 * 设置顶部栏标题的菜单监听器
	 * @param menuItemClickListener
	 */
	protected void setToolbarOnMenuItemClickListener(Toolbar.OnMenuItemClickListener menuItemClickListener){
		if(mToolbar!=null){
			mToolbar.setOnMenuItemClickListener(menuItemClickListener);
		}
	}

	/**
	 * 设置顶部栏的导航图标
	 * @param resourceID
	 */
	protected void setToolbarNavigationIcon(int resourceID){
		if(mToolbar!=null){
			mToolbar.setNavigationIcon(resourceID);
		}
	}



	/**
	 * 设置顶部栏导航的点击监听器
	 * @param clickListener
	 */
	protected void setToolbarNavigationOnClickListener(View.OnClickListener clickListener){
		if(mToolbar!=null){
			mToolbar.setNavigationOnClickListener(clickListener);
		}
	}

	/**
	 * 设置顶部栏的右边标题
	 * @param title 标题
	 */
	protected void setToolbarRightTitle(CharSequence title){
		if(mToolbarRightTitle!=null){
			mToolbarRightTitle.setText(title);
		}
	}

	/**
	 * 设置顶部导航栏的右边图标
	 * @param resourceID
	 */
	protected void setToolbarRightIcon(int resourceID){
		if(mToolbarRightTitle!=null){
			mToolbarRightTitle.setBackgroundResource(resourceID);
		}
	}

	/**
	 * 设置顶部导航栏的右边标题
	 * @param resourceID
	 */
	protected void setToolbarRightTitle(int resourceID){
		if(mToolbarRightTitle!=null){
			mToolbarRightTitle.setText(resourceID);
		}
	}
	/**
	 * 设置顶部栏导航右边标题的点击监听器
	 * @param clickListener
	 */
	protected void setToolbarRightTitleOnClickListener(View.OnClickListener clickListener){
		if(mToolbarRightTitle!=null){
			mToolbarRightTitle.setOnClickListener(clickListener);
		}
	}









	/**
	 * 显示短时间Toast
	 * @param content 显示文本内容
	 */
	protected void showShortToast(String content){
		if(content!=null && !content.isEmpty()){
			showToast(content, Toast.LENGTH_LONG);
		}
	}

	/**
	 * 显示长时间Toast
	 * @param content 显示文本内容
	 */
	protected void showLongToast(String content){
		if(content!=null && !content.isEmpty()){
			showToast(content, Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 非阻塞试显示Toast,防止出现连续点击Toast时的显示问题
	 * @param text 提示内容
	 * @param duration 显示时长
	 */
	private void showToast(CharSequence text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}

}
