package com.clw.phaapp.presenter.common;


import android.util.Log;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.ICommonModel;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.impl.CommonModelImpl;
import io.reactivex.functions.Consumer;


/**
 * 日志Presenter
 *
 * @author chenliwu
 * @create 2018-03-20 23:37
 **/
public class LogPresenter extends BasePresenter{

    private static LogPresenter mLogPresenter=new LogPresenter();

    public static LogPresenter getInstance(){
        return mLogPresenter;
    }

    /**
     * 构造函数私有化
     */
    private LogPresenter(){
        mICommonModel = new CommonModelImpl();
    }


    private static String TAG="LogPresenter";

    private ICommonModel mICommonModel;


    /**
     * 插入一条日志
     * @param logEntity
     */
    public void insertLogRecord(LogEntity logEntity){
        mRxManager.register(mICommonModel.insertLogRecord(logEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                Log.d(TAG,resultEntity.toString());
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG,throwable.getMessage());
            }
        }));
    }

}
