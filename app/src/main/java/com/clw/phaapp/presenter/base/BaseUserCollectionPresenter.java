package com.clw.phaapp.presenter.base;

import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.model.IUserCollectionModel;
import com.clw.phaapp.model.IUserModel;
import com.clw.phaapp.model.impl.UserCollectionModelImpl;
import com.clw.phaapp.model.impl.UserModelImpl;

/**
 * 用户收藏Presenter
 * @author clw
 * @create 2018-02-24 23:38
 **/
public abstract class BaseUserCollectionPresenter<V> extends BasePresenter<V> {

    /**
     * 用户业务接口，提供用户的业务方法调用
     */
    protected IUserCollectionModel mIUserCollectionModel;

    public BaseUserCollectionPresenter(){
        mIUserCollectionModel=new UserCollectionModelImpl();
    }


}
