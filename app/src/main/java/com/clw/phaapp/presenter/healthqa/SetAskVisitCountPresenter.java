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
import com.clw.phaapp.model.IAskModel;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.impl.AskAnswerModelImpl;
import com.clw.phaapp.model.impl.AskModelImpl;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 设置问答访问次数加1Presenter
 */
public class SetAskVisitCountPresenter extends BasePresenter<AskContract.ISetAskVisitCountView>
        implements AskContract.ISetAskVisitCountPresenter {

    private final static String TAG = "SetAskVisitCountPresenter";

    private IAskModel mIAskModel;

    public SetAskVisitCountPresenter(){
        mIAskModel = new AskModelImpl();
    }

    /**
     * 设置问答访问次数加1
     *
     * @param askEntity
     */
    @Override
    public void setAskVisitCount(final AskEntity askEntity) {
        mRxManager.register(mIAskModel.setAskVisitCount(askEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                Log.d(TAG,resultEntity.toString());
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG,throwable.toString());
            }
        }));

    }


}
