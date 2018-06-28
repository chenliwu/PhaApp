package com.clw.phaapp.presenter.healthqa;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.IAskAnswerModel;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LikeEntity;
import com.clw.phaapp.model.impl.AskAnswerModelImpl;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 获取回答列表Presenter
 */
public class GetAskAnswerListPresenter extends BasePresenter<AskAnswerContract.IGetAskAnswerListView>
        implements AskAnswerContract.IGetAskAnswerListPresenter {

    private final static String TAG = "GetAskAnswerListPresenter";

    private IAskAnswerModel mIAskAnswerModel;

    public GetAskAnswerListPresenter(){
        mIAskAnswerModel = new AskAnswerModelImpl();
    }

    /**
     * 获取回答列表
     *
     * @param askAnswerEntity
     */
    @Override
    public void getAskAnswerList(final AskAnswerEntity askAnswerEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIAskAnswerModel.selectAskAnswerByPage(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (!mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            AskAnswerEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),AskAnswerEntity.class);
                            if(data.getTotal() > 0){
                                List<AskAnswerEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),AskAnswerEntity.class);
                                data.setRows(list);
                            }
                            mView.getAskAnswerListSuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getAskAnswerListFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getAskAnswerListFail();
                        Log.e(TAG,throwable.getMessage());
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }


    /**
     * 下拉刷新数据
     *
     * @param askAnswerEntity
     */
    @Override
    public void refreshData(final AskAnswerEntity askAnswerEntity) {
        if(mView == null || !mView.isActive()||!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIAskAnswerModel.selectAskAnswerByPage(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (!mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        Log.d(TAG,resultEntity.toString());
                        if (resultEntity.getState() == 200) {
                            AskAnswerEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),AskAnswerEntity.class);
                            if(data.getTotal() > 0){
                                List<AskAnswerEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),AskAnswerEntity.class);
                                data.setRows(list);
                            }
                            mView.refreshDataSuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getAskAnswerListFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getAskAnswerListFail();
                        Log.e(TAG,throwable.getMessage());
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }

    /**
     * 上拉加载更多
     *
     * @param askAnswerEntity
     */
    @Override
    public void loadMoreData(final AskAnswerEntity askAnswerEntity) {
        if(!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIAskAnswerModel.selectAskAnswerByPage(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (!mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            AskAnswerEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),AskAnswerEntity.class);
                            if(data.getTotal() > 0){
                                List<AskAnswerEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),AskAnswerEntity.class);
                                data.setRows(list);
                            }
                            mView.loadMoreDataSuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getAskAnswerListFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getAskAnswerListFail();
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }
}
