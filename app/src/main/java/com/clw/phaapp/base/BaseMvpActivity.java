package com.clw.phaapp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.model.entity.UserEntity;

/**
 * MVP框架模式的View层基类，用于给Activity继承
 * presenter绑定到activity和View的绑定和解绑操作是每个Activity都会去做的，用父类通过泛型统一完成这个工作。
 * @param <V> View层接口
 * @param <P> Presenter层类
 */
public abstract class BaseMvpActivity<V,P extends BasePresenter<V>> extends BaseSlidingAppComatActivity
    implements IBaseView{

    final static String TAG="BaseMvpActivity";

    protected ProgressDialog mProgressDialog;

    /**
     * Presenter对象
     */
    protected P mPresenter;

    /**
     * 判断视图是否还在活动
     */
    protected boolean isActive;

    /**
     * 应用程序全局变量
     */
    protected PhaApplication mPhaApplication;

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     * @return
     */
    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPhaApplication=(PhaApplication)getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView((V) this);
        isActive = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    //////////////////////////////////////////////////
    /**
     * 记录用户信息
     */
    protected UserEntity mUserEntity;

    /**
     * 用户登录状态---true为登录，false为未登录
     */
    protected boolean isLogin;

    /**
     * 从全局应用变量中中读取用户信息，并初始化
     */
    protected void initUserInfoFromApplication(){
        if(mPhaApplication!=null){
            isLogin = mPhaApplication.isLogin();
            mUserEntity=mPhaApplication.getUserEntity();
        }
    }


    ///////////////////////////////////////////////////////////

    /**
     * 视图是否还在活动
     *
     * @return
     */
    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * 显示加载提示
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(msg);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭加载提示
     */
    @Override
    public void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示消息
     *
     * @param title
     * @param msg
     */
    @Override
    public void showMessage(String title, String msg) {
        showShortToast(msg);
    }

    /**
     * 显示成功信息
     * @param title
     * @param msg
     */
    @Override
    public void showSuccessMessage(String title,String msg){
        showShortToast(msg);
    }

    /**
     * 显示错误信息
     *
     * @param title
     * @param msg
     */
    @Override
    public void showErrorMessage(String title, String msg) {
        showShortToast(msg);
    }

    /**
     * 显示进度
     *
     * @param msg
     */
    @Override
    public void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(msg);
        }
        mProgressDialog.show();
    }

    /**
     * 隐藏进度
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 关闭视图
     */
    @Override
    public void closeView() {
        finish();
    }

    /**
     * 检查网络状态
     *
     * @return 返回true表示可用，false表示不可用
     */
    @Override
    public boolean checkNetworkState() {
        return NetworkUtils.isAvailable(mContext);
    }
}
