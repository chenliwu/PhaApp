package com.clw.phaapp.presenter.healthqa;


import android.util.Log;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.IAskAnswerModel;
import com.clw.phaapp.model.entity.LikeEntity;
import com.clw.phaapp.model.impl.AskAnswerModelImpl;
import io.reactivex.functions.Consumer;


/**
 * 点赞回答Presenter
 *
 * @author chenliwu
 * @create 2018-03-20 23:37
 **/
public class LikeAskAnswerPresenter extends BasePresenter
        implements AskAnswerContract.ILikeAskAnswerPresenter{

    private static LikeAskAnswerPresenter mLogPresenter=new LikeAskAnswerPresenter();

    public static LikeAskAnswerPresenter getInstance(){
        return mLogPresenter;
    }

    protected IAskAnswerModel mIAskAnswerModel;

    /**
     * 构造函数私有化
     */
    private LikeAskAnswerPresenter(){
        mIAskAnswerModel = new AskAnswerModelImpl();
    }


    private static String TAG="LikeAskAnswerPresenter";

    /**
     * 健康问答业务接口，提供用户的业务方法调用
     */

    /**
     * 点赞
     * 点赞需要的字段：目标记录号，点赞者记录号
     * @param likeEntity
     * @param view
     */
    @Override
    public void likeAskAnswer(LikeEntity likeEntity, final AskAnswerContract.ILikeAskAnswerView view) {
        mRxManager.register(mIAskAnswerModel.like(likeEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(resultEntity.getState() == 200){
                    view.likeAskAnswerSuccess(resultEntity.getMessage());
                }else{
                    view.likeAskAnswerFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG,throwable.getMessage());
                view.likeAskAnswerFail(throwable.getMessage());
            }
        }));
    }


}
