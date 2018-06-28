package com.clw.phaapp.presenter.healthinfo;


import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
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

import java.util.List;


/**
 * 获取健康资讯列表Presenter
 */
public class GetHealthInfoListPresenter extends BaseHealthInfoPresenter<HealthInfoContract.IGetHealthInfoListView>
        implements HealthInfoContract.IGetHealthInfoListPresenter {

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
    public void getHealthInfoList(final HealthInfoEntity healthInfoEntity) {
        if(mView == null || mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("",CommonHintInfo.NO_NETWORDK);
            mView.dismissLoading();
            return ;
        }
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<ResultEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                        ShowApiRequest request=new ShowApiRequest( "http://route.showapi.com/96-109"
                                , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign);
                        //添加查询参数
                        if(healthInfoEntity.getTid()!=null && healthInfoEntity.getTid().length()>0){
                            request.addTextPara("tid",healthInfoEntity.getTid());
                        }
                        if(healthInfoEntity.getKeyword()!=null && healthInfoEntity.getKeyword().length() > 0){
                            request.addTextPara("keyword",healthInfoEntity.getKeyword());
                        }
                        request.addTextPara("page",String.valueOf(healthInfoEntity.getCurrentPage()));

                        ResultEntity resultEntity=new ResultEntity();
                        try{
                            //执行查询
                            String res=request.post();
                            Log.d(TAG,res);
                            //解析JSON
                            JSONObject jsonObject=new JSONObject(res);
                            //
                            int nCode=jsonObject.getInt("showapi_res_code");
                            resultEntity.setData(res);
                            if(nCode == 0){ //易源返回标志,0为成功，其他为失败。
                                //showapi_res_body：消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
                                JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
                                //获取分页数据
                                JSONObject pagebean=showapi_res_body.getJSONObject("pagebean");
                                int allNum = pagebean.getInt("allNum");   //总记录数
                                int allPages = pagebean.getInt("allPages");   //总页数
                                int currentPage = pagebean.getInt("currentPage");   //当前页数
                                int maxResult = pagebean.getInt("maxResult");   //当前页数
                                //分页数据
                                String contentlist=pagebean.getString("contentlist");
                                //转化json,把获取到的资讯列表转化成List
                                List<HealthInfoEntity> rows= GsonUtils.parseJsonToArrayList(contentlist,HealthInfoEntity.class);
                                HealthInfoEntity data=new HealthInfoEntity();
                                data.setAllNum(allNum);
                                data.setAllPages(allPages);
                                data.setCurrentPage(currentPage);
                                data.setMaxResult(maxResult);
                                data.setRows(rows);

                                resultEntity.setState(200);
                                resultEntity.setData(data);
                            }else{
                                resultEntity.setMessage(YiYuanCommonCode.getErrorMessage(nCode));
                            }
                            Log.d(TAG,"result:"+resultEntity.toString());
                        }catch (Exception e){
                            resultEntity.setMessage("网络异常："+e.getMessage());
                            Log.e(TAG,"error:"+resultEntity.toString());
                        }
                        observableEmitter.onNext(resultEntity);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultEntity>() {   //跳转到UI线程
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        if (resultEntity.getState() == 200) {
                            mView.getHealthInfoListSuccess((HealthInfoEntity)resultEntity.getData());
                        } else {
                            mView.getHealthInfoListFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    //处理异常情况
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null || !mView.isActive()) {
                            //如果视图已经不在活动状态，停止UI操作
                            return;
                        }
                        mView.getHealthInfoListFail();
                        mView.showMessage("",throwable.getMessage());
                        Log.e(TAG,"error:"+throwable.getMessage());
                    }
                });
            }
        }, 1000);
    }


    /**
     * 下拉刷新数据
     *
     * @param healthInfoEntity
     */
    @Override
    public void refreshData(final HealthInfoEntity healthInfoEntity) {
        if(mView==null || !mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("",CommonHintInfo.NO_NETWORDK);
            mView.dismissLoading();
            return ;
        }
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<ResultEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                        ShowApiRequest request=new ShowApiRequest( "http://route.showapi.com/96-109"
                                , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign);
                        //添加查询参数
                        if(healthInfoEntity.getTid()!=null && healthInfoEntity.getTid().length()>0){
                            request.addTextPara("tid",healthInfoEntity.getTid());
                        }
                        if(healthInfoEntity.getKeyword()!=null && healthInfoEntity.getKeyword().length() > 0){
                            request.addTextPara("keyword",healthInfoEntity.getKeyword());
                        }
                        request.addTextPara("page",String.valueOf(healthInfoEntity.getCurrentPage()));

                        ResultEntity resultEntity=new ResultEntity();
                        try{
                            //执行查询
                            String res=request.post();
                            Log.d(TAG,res);
                            //解析JSON
                            JSONObject jsonObject=new JSONObject(res);
                            //
                            int nCode=jsonObject.getInt("showapi_res_code");
                            resultEntity.setData(res);
                            if(nCode == 0){ //易源返回标志,0为成功，其他为失败。
                                //showapi_res_body：消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
                                JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
                                //获取分页数据
                                JSONObject pagebean=showapi_res_body.getJSONObject("pagebean");
                                int allNum = pagebean.getInt("allNum");   //总记录数
                                int allPages = pagebean.getInt("allPages");   //总页数
                                int currentPage = pagebean.getInt("currentPage");   //当前页数
                                int maxResult = pagebean.getInt("maxResult");   //当前页数
                                //分页数据
                                String contentlist=pagebean.getString("contentlist");
                                //转化json,把获取到的资讯列表转化成List
                                List<HealthInfoEntity> rows= GsonUtils.parseJsonToArrayList(contentlist,HealthInfoEntity.class);
                                HealthInfoEntity data=new HealthInfoEntity();
                                data.setAllNum(allNum);
                                data.setAllPages(allPages);
                                data.setCurrentPage(currentPage);
                                data.setMaxResult(maxResult);
                                data.setRows(rows);

                                resultEntity.setState(200);
                                resultEntity.setData(data);
                            }else{
                                resultEntity.setMessage(YiYuanCommonCode.getErrorMessage(nCode));
                            }
                            Log.d(TAG,"result:"+resultEntity.toString());
                        }catch (Exception e){
                            resultEntity.setMessage("网络异常："+e.getMessage());
                            Log.e(TAG,"error:"+resultEntity.toString());
                        }
                        observableEmitter.onNext(resultEntity);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultEntity>() {   //跳转到UI线程
                            @Override
                            public void accept(ResultEntity resultEntity) throws Exception {
                                if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                                    return;
                                }
                                if (resultEntity.getState() == 200) {
                                    mView.refreshDataSuccess((HealthInfoEntity)resultEntity.getData());
                                } else {
                                    mView.getHealthInfoListFail();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            //处理异常情况
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (mView == null || !mView.isActive()) {
                                    //如果视图已经不在活动状态，停止UI操作
                                    return;
                                }
                                mView.getHealthInfoListFail();
                                mView.showMessage("",throwable.getMessage());
                                Log.e(TAG,"error:"+throwable.getMessage());
                            }
                        });
            }
        }, 1000);
    }

    /**
     * 上拉加载更多
     *
     * @param healthInfoEntity
     */
    @Override
    public void loadMoreData(final HealthInfoEntity healthInfoEntity) {
        if(mView == null || mView.isActive() || !mView.checkNetworkState()){
            mView.showMessage("",CommonHintInfo.NO_NETWORDK);
            mView.dismissLoading();
            return ;
        }
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<ResultEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                        ShowApiRequest request=new ShowApiRequest( "http://route.showapi.com/96-109"
                                , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign);
                        //添加查询参数
                        if(healthInfoEntity.getTid()!=null && healthInfoEntity.getTid().length()>0){
                            request.addTextPara("tid",healthInfoEntity.getTid());
                        }
                        if(healthInfoEntity.getKeyword()!=null && healthInfoEntity.getKeyword().length() > 0){
                            request.addTextPara("keyword",healthInfoEntity.getKeyword());
                        }
                        request.addTextPara("page",String.valueOf(healthInfoEntity.getCurrentPage()));

                        ResultEntity resultEntity=new ResultEntity();
                        try{
                            //执行查询
                            String res=request.post();
                            Log.d(TAG,res);
                            //解析JSON
                            JSONObject jsonObject=new JSONObject(res);
                            //
                            int nCode=jsonObject.getInt("showapi_res_code");
                            resultEntity.setData(res);
                            if(nCode == 0){ //易源返回标志,0为成功，其他为失败。
                                //showapi_res_body：消息体的JSON封装，所有应用级的返回参数将嵌入此对象 。
                                JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
                                //获取分页数据
                                JSONObject pagebean=showapi_res_body.getJSONObject("pagebean");
                                int allNum = pagebean.getInt("allNum");   //总记录数
                                int allPages = pagebean.getInt("allPages");   //总页数
                                int currentPage = pagebean.getInt("currentPage");   //当前页数
                                int maxResult = pagebean.getInt("maxResult");   //当前页数
                                //分页数据
                                String contentlist=pagebean.getString("contentlist");
                                //转化json,把获取到的资讯列表转化成List
                                List<HealthInfoEntity> rows= GsonUtils.parseJsonToArrayList(contentlist,HealthInfoEntity.class);
                                HealthInfoEntity data=new HealthInfoEntity();
                                data.setAllNum(allNum);
                                data.setAllPages(allPages);
                                data.setCurrentPage(currentPage);
                                data.setMaxResult(maxResult);
                                data.setRows(rows);

                                resultEntity.setState(200);
                                resultEntity.setData(data);
                            }else{
                                resultEntity.setMessage(YiYuanCommonCode.getErrorMessage(nCode));
                            }
                            Log.d(TAG,"result:"+resultEntity.toString());
                        }catch (Exception e){
                            resultEntity.setMessage("网络异常："+e.getMessage());
                            Log.e(TAG,"error:"+resultEntity.toString());
                        }
                        observableEmitter.onNext(resultEntity);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultEntity>() {   //跳转到UI线程
                            @Override
                            public void accept(ResultEntity resultEntity) throws Exception {
                                if (mView == null || !mView.isActive()) {  //如果视图已经不在活动状态，停止UI操作
                                    return;
                                }
                                if (resultEntity.getState() == 200) {
                                    mView.loadMoreDataSuccess((HealthInfoEntity)resultEntity.getData());
                                } else {
                                    mView.getHealthInfoListFail();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            //处理异常情况
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (mView == null || !mView.isActive()) {
                                    //如果视图已经不在活动状态，停止UI操作
                                    return;
                                }
                                mView.getHealthInfoListFail();
                                mView.showMessage("",throwable.getMessage());
                                Log.e(TAG,"error:"+throwable.getMessage());
                            }
                        });
            }
        }, 1000);
    }
}
