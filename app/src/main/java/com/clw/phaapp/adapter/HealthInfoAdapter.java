package com.clw.phaapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康资讯列表的适配器
 */
public class HealthInfoAdapter extends RecyclerView.Adapter<HealthInfoAdapter.MyViewHolder > {


    private Context mContext;

    /**
     * 健康问答列表
     */
    private List<HealthInfoEntity> mDataList=new ArrayList<>();

    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public HealthInfoAdapter(Context context,List<HealthInfoEntity> mDataList) {
        this.mContext=context;
        this.mDataList=mDataList;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_health_info_list,viewGroup,false);
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
        HealthInfoEntity healthInfoEntity=mDataList.get(position);
        viewHolder.txt_showHealthInfoTitle.setText(healthInfoEntity.getTitle()+"");
        viewHolder.txt_showHealthInfoAuthor.setText(healthInfoEntity.getAuthor()+"");
        String smallDate= TimeUtils.getSmartDate_3(healthInfoEntity.getTime());
        viewHolder.txt_showHealthInfoTime.setText(smallDate+"");
        //加载图片
        if (healthInfoEntity.getImg() != null) {
            Glide.with(mContext)
                    .load(healthInfoEntity.getImg())
                    .into(viewHolder.iv_showHealthInfoImage);
        }
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
        public TextView txt_showHealthInfoTitle;
        /**
         * 显示来源
         */
        public TextView txt_showHealthInfoAuthor;
        /**
         * 显示时间
         */
        public TextView txt_showHealthInfoTime;

        /**
         * 显示资讯图片
         */
        public ImageView iv_showHealthInfoImage;

        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;

        public MyViewHolder (View view,MyItemClickListener myItemClickListener,
                             MyItemLongClickListener mLongClickListener){
            super(view);
            txt_showHealthInfoTitle = (TextView) view.findViewById(R.id.txt_showHealthInfoTitle);
            txt_showHealthInfoAuthor = (TextView) view.findViewById(R.id.txt_showHealthInfoAuthor);
            txt_showHealthInfoTime = (TextView) view.findViewById(R.id.txt_showHealthInfoTime);
            iv_showHealthInfoImage = (ImageView)  view.findViewById(R.id.iv_showHealthInfoImage);

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
