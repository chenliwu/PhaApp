package com.clw.phaapp.presenter.common;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.ReportContract;
import com.clw.phaapp.model.IReportModel;
import com.clw.phaapp.model.entity.ReportEntity;
import com.clw.phaapp.model.impl.ReportModelImpl;
import io.reactivex.functions.Consumer;


/**
 * @author chenliwu
 * @create 2018-04-08 1:04
 **/
public class ReportPresenter extends BasePresenter<ReportContract.IReportView>
    implements ReportContract.IReportPresenterr{


    private IReportModel mIReportModel;

    public ReportPresenter(){
        mIReportModel=new ReportModelImpl();
    }

    /**
     * 举报
     *
     * @param reportEntity
     */
    @Override
    public void report(ReportEntity reportEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            return ;
        }
        mRxManager.register(mIReportModel.insertRecord(reportEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                    return;
                }
                if (resultEntity.getState() == 200) {
                    mView.reportSuccess(resultEntity.getMessage());
                } else {
                    mView.reportFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                mView.reportFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}
