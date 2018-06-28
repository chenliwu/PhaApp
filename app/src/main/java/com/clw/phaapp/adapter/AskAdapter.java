package com.clw.phaapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.model.entity.AskEntity;

import java.util.List;

/**
 * 健康问答列表的适配器
 */
public class AskAdapter extends RecyclerView.Adapter<AskAdapter.MyViewHolder > {

    /**
     * 健康问答列表
     */
    private List<AskEntity> mAskEntityList;

    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public AskAdapter(List<AskEntity> mAskEntityList) {
        this.mAskEntityList=mAskEntityList;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_ask_list,viewGroup,false);
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
        AskEntity askEntity=mAskEntityList.get(position);

        //显示问题的标题
        viewHolder.txt_showAskTitle.setText(askEntity.getTitle()+"");

        //显示回答个数
        viewHolder.txt_showAskAnswerCount.setText(askEntity.getAcceptanswercount()+"个回答");

        //显示浏览次数
        int visitcount=0;
        if(askEntity.getVisitcount()!=null){
            visitcount=askEntity.getVisitcount();
        }

        viewHolder.txt_showAskVisitCount.setText(visitcount+"次浏览");
        String smartTime= TimeUtils.getSmartDate_1(String.valueOf(askEntity.getOpdate()));
        viewHolder.txt_showAskTime.setText(smartTime);
    }

    /**
     * 获取数据的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mAskEntityList.size();
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class MyViewHolder  extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnLongClickListener {
        /**
         * 显示标题
         */
        public TextView txt_showAskTitle;
        /**
         * 显示回答个数
         */
        public TextView txt_showAskAnswerCount;
        /**
         * 显示浏览次数
         */
        public TextView txt_showAskVisitCount;

        /**
         * 显示发布时间
         */
        public TextView txt_showAskTime;



        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;

        public MyViewHolder (View view,MyItemClickListener myItemClickListener,
                             MyItemLongClickListener mLongClickListener){
            super(view);
            txt_showAskTitle = view.findViewById(R.id.txt_showAskTitle);
            txt_showAskAnswerCount =  view.findViewById(R.id.txt_showAskAnswerCount);
            txt_showAskVisitCount =  view.findViewById(R.id.txt_showAskVisitCount);
            txt_showAskTime =  view.findViewById(R.id.txt_showAskTime);

            //将全局的监听赋值给接口
            this.mListener = myItemClickListener;
            this.mLongClickListener=mLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
        void onItemClick(View view,int position);
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
        void onItemLongClick(View view,int position);
    }

    /**
     * 设置长按事件监听接口
     * @param listener
     */
    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mLongClickListener = listener;
    }

}
