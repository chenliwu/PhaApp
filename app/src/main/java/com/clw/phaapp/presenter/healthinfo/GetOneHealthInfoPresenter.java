package com.clw.phaapp.presenter.healthinfo;


import android.util.Log;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.yiyuan.YiYuanCommonCode;
import com.clw.phaapp.common.yiyuan.YiYuanCommonInfo;
import com.clw.phaapp.contract.HealthInfoContract;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.base.BaseHealthInfoPresenter;
import com.show.api.ShowApiRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONObject;


/**
 * 获取健康资讯明细Presenter
 */
public class GetOneHealthInfoPresenter extends BaseHealthInfoPresenter<HealthInfoContract.IGetOneHealthInfoView>
        implements HealthInfoContract.IGetOneHealthInfoPresenter {

    private final static String TAG = "GetOneHealthInfoPresenter";

    /**
     * showapi_res_code
     * 易源返回标志,0为成功，其他为失败。
     0成功
     -1，系统调用错误
     -2，可调用次数或金额为0
     -3，读取超时
     -4，服务端返回数据解析错误
     -5，后端服务器DNS解析错误
     -6，服务不存在或未上线
     -1000，系统维护
     -1002，showapi_appid字段必传
     -1003，showapi_sign字段必传
     -1004，签名sign验证有误
     -1005，showapi_timestamp无效
     -1006，app无权限调用接口
     -1007，没有订购套餐
     -1008，服务商关闭对您的调用权限
     -1009，调用频率受限
     -1010，找不到您的应用
     -1011，子授权app_child_id无效
     -1012，子授权已过期或失效
     -1013，子授权ip受限
     */

    /**
     * 获取单条健康资讯明细
     *
     * @param healthInfoEntity
     */
    @Override
    public void getOneHealthInfo(final HealthInfoEntity healthInfoEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("提示信息", CommonHintInfo.NO_NETWORDK);
            mView.dismissLoading();
            return ;
        }
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<ResultEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                        final String res=new ShowApiRequest( "http://route.showapi.com/96-36"
                                , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign)
                                .addTextPara("id", healthInfoEntity.getId())
                                .post();

                        //Log.d("所在的线程：",Thread.currentThread().getName());
                        //Log.d("发送的数据:", 1+"");
                        Log.d(TAG,res);

                        ResultEntity resultEntity=new ResultEntity();
                        //解析JSON
                        JSONObject jsonObject=new JSONObject(res);
                        Log.d(TAG,"jsonObject:"+jsonObject.toString());
                        int nCode=(int)jsonObject.get("showapi_res_code");
                        if(nCode == 0){ //易源返回标志,0为成功，其他为失败。
                            //showapi_res_body：消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
                            JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
                            //获取健康资讯的json字符串
                            String item=showapi_res_body.getString("item");
                            HealthInfoEntity entity=new HealthInfoEntity();
                            //转化json
                            entity=(HealthInfoEntity)entity.fromJson(item);
                            resultEntity.setState(200);
                            resultEntity.setData(entity);
                        }else if(nCode == 3){   //3-读取超时
                            resultEntity.setMessage("读取超时");
                        }else{
                            resultEntity.setMessage(YiYuanCommonCode.getErrorMessage(nCode));
                        }
                        Log.d(TAG,"result:"+resultEntity.toString());
                        observableEmitter.onNext(resultEntity);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultEntity>() {   //跳转到UI线程
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        //Log.d("所在的线程：",Thread.currentThread().getName());
                        //Log.d("接收到的数据:", "integer:" + resultEntity.toString());
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            mView.getOneHealthInfoSuccess((HealthInfoEntity) resultEntity.getData());
                        } else {
                            mView.getOneHealthInfoFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //处理异常情况
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getOneHealthInfoFail();
                        mView.showMessage("",throwable.getMessage());
                        Log.e(TAG,"error:"+throwable.getMessage());
                    }
                });
            }
        }, 1000);
    }

    /**
     * 获取单条资讯明细
     *
     * @param healthInfoEntity
     * @param view
     */
    @Override
    public void getOneHealthInfo(final HealthInfoEntity healthInfoEntity,final HealthInfoContract.IGetOneHealthInfoView view) {
        Observable.create(new ObservableOnSubscribe<ResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                if(view== null || !view.isActive() || !view.checkNetworkState()){
                    return ;
                }
                final String res=new ShowApiRequest( "http://route.showapi.com/96-36"
                        , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign)
                        .addTextPara("id", healthInfoEntity.getId())
                        .post();

                ResultEntity resultEntity=new ResultEntity();
                //解析JSON
                JSONObject jsonObject=new JSONObject(res);
                Log.d(TAG,"jsonObject:"+jsonObject.toString());
                int nCode=(int)jsonObject.get("showapi_res_code");
                if(nCode == 0){ //易源返回标志,0为成功，其他为失败。
                    //showapi_res_body：消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
                    JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
                    //获取健康资讯的json字符串
                    String item=showapi_res_body.getString("item");
                    HealthInfoEntity entity=new HealthInfoEntity();
                    //转化json
                    entity=(HealthInfoEntity)entity.fromJson(item);
                    resultEntity.setState(200);
                    resultEntity.setData(entity);
                }else if(nCode == 3){   //3-读取超时
                    resultEntity.setMessage("读取超时");
                }else{
                    resultEntity.setMessage(YiYuanCommonCode.getErrorMessage(nCode));
                }
                observableEmitter.onNext(resultEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultEntity>() {   //跳转到UI线程
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(view== null || !view.isActive() || !view.checkNetworkState()){
                            return ;
                        }
                        if (resultEntity.getState() == 200) {
                            view.getOneHealthInfoSuccess((HealthInfoEntity) resultEntity.getData());
                        } else {
                            view.getOneHealthInfoFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //处理异常情况
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(view== null || !view.isActive() || !view.checkNetworkState()){
                            return ;
                        }
                        view.getOneHealthInfoFail();
                        view.showMessage("",throwable.getMessage());
                        Log.e(TAG,"error:"+throwable.getMessage());
                    }
                });
    }
}
