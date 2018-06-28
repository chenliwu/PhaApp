package com.clw.phaapp.presenter.base;

import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.model.IAskModel;
import com.clw.phaapp.model.IUserModel;
import com.clw.phaapp.model.impl.AskModelImpl;
import com.clw.phaapp.model.impl.UserModelImpl;

/**
 *
 * @author clw
 * @create 2018-02-24 23:38
 **/
public abstract class BaseAskPresenter<V> extends BasePresenter<V> {

    /**
     * 健康问答业务接口，提供用户的业务方法调用
     */
    protected IAskModel mAskModel;

    public BaseAskPresenter(){
        mAskModel=new AskModelImpl();
    }

}
