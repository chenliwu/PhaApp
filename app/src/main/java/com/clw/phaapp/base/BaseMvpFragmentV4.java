package com.clw.phaapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.clw.mysdk.base.fragment.BaseFragment_SupportV4;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.model.entity.UserEntity;

/**
 * MVP模式Fragment基类
 *
 * @author chenliwu
 * @create 2018-03-10 23:29
 **/
public abstract class BaseMvpFragmentV4<V,P extends BasePresenter<V>> extends BaseFragment_SupportV4
        implements IBaseView{


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化派生类可能用到的信息，抽象到这里，降低代码冗余
        mPresenter = initPresenter();
        mPhaApplication=(PhaApplication)getActivity().getApplication();
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
            mUserEntity=mPhaApplication.getUserEntity();
        }
    }


    ///////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////
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
        if (mProgressDialog == null && mContext!=null) {
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


    @Override
    public void onResume() {
        mPresenter.attachView((V) this);
        super.onResume();
        isActive = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isActive = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isActive = false;
        mPresenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        isActive = true;
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
