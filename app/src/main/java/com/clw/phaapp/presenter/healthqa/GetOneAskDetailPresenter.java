package com.clw.phaapp.presenter.healthqa;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.base.BaseAskPresenter;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 获取健康问答明细Presenter
 */
public class GetOneAskDetailPresenter extends BaseAskPresenter<AskContract.IGetOneAskDetailView>
        implements AskContract.IGetOneAskDetailPresenter {

    private final static String TAG = "GetOneAskDetailPresenter";

    /**
     * 获取健康问答列表
     *
     * @param askEntity
     */
    @Override
    public void getOneAskDetail(final AskEntity askEntity) {
        if(mView == null || !mView.isActive()|| !mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mAskModel.selectOneAskRecord(askEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        Log.d(TAG,resultEntity.toString());
                        if (resultEntity.getState() == 200) {
                            AskEntity data=GsonUtils.parseJsonToObject(resultEntity.getData().toString(),AskEntity.class);
                            List<AskEntity> list=GsonUtils.parseJsonToArrayList(data.getRows().toString(),AskEntity.class);
                            Log.d(TAG,"list size:"+list.size());
                            data.setRows(list);
                            mView.getOneAskDetailSuccess(data);
                        } else {
                            mView.showErrorMessage("操作失败", resultEntity.getMessage());
                            mView.getOneAskDetailFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.dismissLoading();
                        Log.e(TAG,throwable.getMessage());
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        }, 1000);
    }


}
