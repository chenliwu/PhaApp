package com.clw.phaapp.presenter.user;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.contract.MessageContract;
import com.clw.phaapp.model.IAskAnswerModel;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.model.impl.AskAnswerModelImpl;
import com.clw.phaapp.presenter.base.BaseMessagePresenter;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 获取消息列表Presenter
 */
public class GetMessageListPresenter extends BaseMessagePresenter<MessageContract.IGetMessageListView>
        implements MessageContract.IGetMessageListPresenter {

    private final static String TAG = "GetMessageListPresenter";


    /**
     * 下拉刷新数据
     *
     * @param messageEntity
     */
    @Override
    public void refreshData(final MessageEntity messageEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIMessageModel.selectRecordsListByPage(messageEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            MessageEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),MessageEntity.class);
                            if(data.getTotal() > 0){
                                List<MessageEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),MessageEntity.class);
                                data.setRows(list);

                                for(MessageEntity entity:list){
                                    Log.e(TAG,entity.toString());
                                }

                            }
                            mView.refreshDataSuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getMessageListFail(resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getMessageListFail(NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }

    /**
     * 上拉加载更多
     *
     * @param messageEntity
     */
    @Override
    public void loadMoreData(final MessageEntity messageEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIMessageModel.selectRecordsListByPage(messageEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            MessageEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),MessageEntity.class);
                            if(data.getTotal() > 0){
                                List<MessageEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),MessageEntity.class);
                                data.setRows(list);
                            }
                            mView.loadMoreDataSuccess(data);
                        } else {
                            mView.getMessageListFail( resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getMessageListFail(NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }


}
