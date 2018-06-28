package com.clw.phaapp.presenter.common;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.LogContract;
import com.clw.phaapp.model.ICommonModel;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.impl.CommonModelImpl;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 获取浏览历史Presenter
 */
public class GetVisitHistorylPresenter extends BasePresenter<LogContract.IGetVisitHistoryView>
        implements LogContract.IGetVisitHistoryPresenter {

    private final static String TAG = "GetVisitHistorylPresenter";

    private ICommonModel mICommonModel;

    public GetVisitHistorylPresenter(){
        mICommonModel = new CommonModelImpl();
    }

    /**
     * 获取浏览历史
     *
     * @param logEntity
     */
    @Override
    public void getVisitHistory(final LogEntity logEntity) {
        if(mView==null || !mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mICommonModel.selectVisitHistoryRecordsByPage(logEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (mView==null || !mView.isActive() ) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        Log.d(TAG,resultEntity.toString());
                        if (resultEntity.getState() == 200) {
                            LogEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),LogEntity.class);
                            List<LogEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),LogEntity.class);
                            Log.d(TAG,"list size:"+list.size());
                            data.setRows(list);
                            mView.getVisitHistorySuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getVisitHistoryFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView==null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getVisitHistoryFail();
                        Log.e(TAG,throwable.getMessage());
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }


}
