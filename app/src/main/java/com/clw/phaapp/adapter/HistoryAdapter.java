package com.clw.phaapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.yiyuan.YiYuanCommonCode;
import com.clw.phaapp.common.yiyuan.YiYuanCommonInfo;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
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
 * 浏览历史列表的适配器
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder > {

    private final static String TAG = "HistoryAdapter";

    /**
     * 健康问答列表
     */
    private List<LogEntity> mDataList;

    private Context mContext;

    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public HistoryAdapter(Context context,List<LogEntity> mDataList) {
        this.mDataList=mDataList;
        this.mContext=context;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_history_list,viewGroup,false);
        MyViewHolder  viewHolder = new MyViewHolder (view,mItemClickListener,mLongClickListener);
        return viewHolder;
    }


    /**
     * 将数据与界面进行绑定的操作
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder  viewHolder, int position) {
        LogEntity logEntity=mDataList.get(position);
        Log.d(TAG,logEntity.toString());
        int nbyte=0;
        if(logEntity.getType()!=null){
            nbyte = logEntity.getType();
        }
        switch (nbyte){
            case 1://1 综合资讯
                showHealthInfo(logEntity.getTargetrecno(),viewHolder);
                break;
            case 2://2 疾病资讯
                showHealthInfo(logEntity.getTargetrecno(),viewHolder);
                break;

            case 3: //3食品资讯
                showHealthInfo(logEntity.getTargetrecno(),viewHolder);
                break;

            case 20:   //20访问问答

                break;
        }

    }

    /**
     * 显示健康资讯信息
     * @param id
     */
    private void showHealthInfo(final String id,final MyViewHolder viewHolder){
        Observable.create(new ObservableOnSubscribe<ResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ResultEntity> observableEmitter) throws Exception {
                final String res=new ShowApiRequest( "http://route.showapi.com/96-36"
                        , YiYuanCommonInfo.showapi_appid, YiYuanCommonInfo.showapi_sign)
                        .addTextPara("id", id)
                        .post();
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
                        if (resultEntity.getState() == 200) {
                            HealthInfoEntity healthInfoEntity=(HealthInfoEntity)resultEntity.getData();
                            viewHolder.txt_showHistoryTitle.setText(healthInfoEntity.getTitle());
                            viewHolder.txt_showAuthor.setText(healthInfoEntity.getAuthor());
                            String smartTime=TimeUtils.getSmartDate_3(healthInfoEntity.getTime());
                            viewHolder.txt_showTime.setText(smartTime+"");
                            //加载图片
                            if (healthInfoEntity.getImg() != null) {
                                Glide.with(mContext)
                                        .load(healthInfoEntity.getImg())
                                        .into(viewHolder.iv_showimg);
                            }
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    //处理异常情况
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,"error:"+throwable.getMessage());
                    }
                });
    }

    /**
     * 显示健康问答
     * @param reno
     * @param viewHolder
     */
    private void showAskInfo(final long reno,final MyViewHolder viewHolder){

    }


    /**
     * 获取数据的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class MyViewHolder  extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnLongClickListener {
        /**
         * 显示标题
         */
        public TextView txt_showHistoryTitle;
        /**
         * 显示来源
         */
        public TextView txt_showAuthor;
        /**
         * 显示浏览次数
         */
        public TextView txt_showVisitCount;

        /**
         * 显示发布时间
         */
        public TextView txt_showTime;

        /**
         * 显示图片
         */
        public ImageView iv_showimg;



        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;

        public MyViewHolder (View view,MyItemClickListener myItemClickListener,
                             MyItemLongClickListener mLongClickListener){
            super(view);
            txt_showHistoryTitle = (TextView) view.findViewById(R.id.txt_showHistoryTitle);
            txt_showAuthor = (TextView) view.findViewById(R.id.txt_showAuthor);
            txt_showVisitCount = (TextView) view.findViewById(R.id.txt_showVisitCount);
            txt_showTime = (TextView) view.findViewById(R.id.txt_showTime);
            iv_showimg = (ImageView) view.findViewById(R.id.iv_showimg);

            //将全局的监听赋值给接口
            this.mListener = myItemClickListener;
            this.mLongClickListener=mLongClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * 点击监听,实现OnClickListener接口重写的方法
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }

        /**
         * 长按监听
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) {
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    }



    /**
     * 点击监听接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    /**
     * 长按监听接口
     */
    public interface MyItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    /**
     * 设置长按事件监听接口
     * @param listener
     */
    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mLongClickListener = listener;
    }

}
