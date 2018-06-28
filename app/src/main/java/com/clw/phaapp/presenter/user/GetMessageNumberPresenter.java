package com.clw.phaapp.presenter.user;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.MessageContract;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.presenter.base.BaseMessagePresenter;
import io.reactivex.functions.Consumer;

import java.util.List;

/**
 * 获取消息数量Presenter
 */
public class GetMessageNumberPresenter extends BaseMessagePresenter<MessageContract.IGetMessageNumberView>
        implements MessageContract.IGetMessageNumberPresenter {

    private final static String TAG = "GetMessageNumberPresenter";


    /**
     * 获取消息记录数
     *
     * @param messageEntity
     * @param view
     */
    @Override
    public void getMessageNumber(final MessageEntity messageEntity,final MessageContract.IGetMessageNumberView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        Log.e(TAG,messageEntity.toString());
        mRxManager.register(mIMessageModel.selectMessageTotalByParam(messageEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                Log.e(TAG,resultEntity.toString());
                if (resultEntity.getState() == 200) {
                    double number= (double)resultEntity.getData();
                    view.getMessageNumberSuccess((int)number);

                } else {
                    view.getMessageNumberFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.getMessageNumberFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }


}
