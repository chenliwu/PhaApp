package com.clw.phaapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.model.entity.UserEntity;

import java.util.List;

/**
 *消息列表的适配器
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder > {

    private BaseMvpActivity mBaseMvpActivity;

    private UserEntity mUserEntity;



    /**
     * 消息列表
     */
    private List<MessageEntity> mDataList;

    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public MessageAdapter(List<MessageEntity> mDataList, BaseMvpActivity baseMvpActivity,UserEntity userEntity) {
        this.mDataList=mDataList;
        this.mBaseMvpActivity=baseMvpActivity;
        this.mUserEntity=userEntity;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_message_list,viewGroup,false);
        MyViewHolder  viewHolder = new MyViewHolder(view, mItemClickListener, mLongClickListener,
                new IMyViewHolderClick() {

                    //进入详情
                    @Override
                    public void onItemClick(MessageEntity messageEntity, int position) {

                    }

                    //回答
                    @Override
                    public void reply(final MessageEntity messageEntity, int position) {

                    }
                });
        return viewHolder;
    }





    /**
     * 将数据与界面进行绑定的操作
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder  viewHolder, int position) {

        MessageEntity messageEntity=mDataList.get(position);
        if(messageEntity.getType()!=null){


            //显示时间
            String smartTime= TimeUtils.getSmartDate_1(String.valueOf(messageEntity.getOpdate()));
            viewHolder.txt_ShowMessageTime.setText(smartTime);

            //类型：0系统通知，1点赞消息，2评论，3回复
            byte type = messageEntity.getType();
            switch (type){
                case 0: //系统通知
                    //隐藏回复按钮
                    viewHolder.txt_ReplyButton.setVisibility(View.GONE);
                    //显示发送者
                    viewHolder.txt_ShowSenderName.setText("系统通知");
                    //显示消息内容
                    viewHolder.txt_MessageContent.setText(messageEntity.getContent());
                    viewHolder.txt_RelatedContent.setVisibility(View.GONE);
                    break;
                case 1://点赞消息


                    break;
                case 2:   //评论

                    break;
                case 3: //回复

                    break;
            }
        }

        //设置删除问题按钮的点击事件，将实体绑定到view上面
        //viewHolder.ll_item.setTag(askEntity);
        //viewHolder.btn_deleteAsk.setTag(askEntity);
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
         * 消息图标
         */
        public ImageView iv_MessageIcon;


        /**
         * 显示消息发送者昵称
         */
        public TextView txt_ShowSenderName;
        /**
         * 显示消息时间
         */
        public TextView txt_ShowMessageTime;
        /**
         * 回复按钮
         */
        public TextView txt_ReplyButton;
        /**
         * 显示消息内容
         */
        public TextView txt_MessageContent;
        /**
         * 显示消息相关内容
         */
        public TextView txt_RelatedContent;

//        /**
//         * 显示发布时间
//         */
//        public TextView txt_showAskTime;
//        /**
//         * 删除问答按钮
//         */
//        public Button btn_deleteAsk;
//
//        /**
//         *
//         */
//        LinearLayout ll_item;




        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;


        /**
         * item内部控件点击事件监听接口，比如item内部的按钮
         */
        IMyViewHolderClick mHolderClick;

        public MyViewHolder (View view,MyItemClickListener myItemClickListener,
                             MyItemLongClickListener mLongClickListener,
                             IMyViewHolderClick holderClick){
            super(view);
            iv_MessageIcon = view.findViewById(R.id.iv_MessageIcon);
            txt_ShowSenderName =  view.findViewById(R.id.txt_ShowSenderName);
            txt_ShowMessageTime =  view.findViewById(R.id.txt_ShowMessageTime);

            txt_MessageContent =  view.findViewById(R.id.txt_MessageContent);
            txt_RelatedContent =  view.findViewById(R.id.txt_RelatedContent);

            txt_ReplyButton =  view.findViewById(R.id.txt_ReplyButton);
            txt_ReplyButton.setOnClickListener(this);

//            //Item布局
//            ll_item =  view.findViewById(R.id.ll_item);
//            ll_item.setOnClickListener(this);
//
//            //删除问答按钮
//            btn_deleteAsk =  view.findViewById(R.id.btn_deleteAsk);
//            btn_deleteAsk.setOnClickListener(this);

            //将全局的监听赋值给接口
            this.mListener = myItemClickListener;
            this.mLongClickListener=mLongClickListener;
            this.mHolderClick=holderClick;
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
            switch (v.getId()){
                case R.id.txt_ReplyButton:  //回复
                    mHolderClick.reply((MessageEntity) v.getTag(),getLayoutPosition());
                    break;


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
     * Item内部控件点击事件回调接口
     */
    interface IMyViewHolderClick{

        /**
         * 点击item，进入问答详情界面
         * @param messageEntity
         * @param position
         */
        void onItemClick(MessageEntity messageEntity, int position);

        /**
         * 回复消息
         * @param messageEntity
         * @param position
         */
        void reply(MessageEntity messageEntity, int position);

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
