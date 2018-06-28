package com.clw.phaapp.presenter.user;

import android.util.Log;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.MessageContract;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.presenter.base.BaseMessagePresenter;
import io.reactivex.functions.Consumer;

/**
 * 修改消息状态，将消息修改成已读Presenter
 */
public class UpdateMessageStatusPresenter extends BaseMessagePresenter<MessageContract.IUpdateMessageStatusView>
        implements MessageContract.IUpdateMessagePresenter {

    private final static String TAG = "UpdateMessageStatusPresenter";

    /**
     * 修改消息状态，将消息修改成已读
     *
     * @param messageEntity
     * @param view
     */
    @Override
    public void updateMessageStatus(final MessageEntity messageEntity,final MessageContract.IUpdateMessageStatusView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        Log.e(TAG,messageEntity.toString());
        mRxManager.register(mIMessageModel.updateMessageStatusByParam(messageEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                Log.e(TAG,resultEntity.toString());
                if (resultEntity.getState() == 200) {
                    view.updateMessageStatusSuccess(resultEntity.getMessage());

                } else {
                    view.updateMessageStatusFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.updateMessageStatusFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }


}
