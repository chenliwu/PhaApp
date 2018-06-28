package com.clw.phaapp.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.utils.TextViewUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LikeEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.healthqa.LikeAskAnswerPresenter;

import java.util.List;

/**
 * 健康问答列表的适配器
 */
public class AskAnswerAdapter extends RecyclerView.Adapter<AskAnswerAdapter.MyViewHolder > {

    private static String TAG="AskAnswerAdapter";

    /**
     * 健康问答列表
     */
    private List<AskAnswerEntity> mDataList;

    private BaseMvpActivity mBaseMvpActivity;

    private AskEntity mAskEntity;

    /**
     * 记录用户信息
     */
    private UserEntity mUserEntity;



    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public AskAnswerAdapter(BaseMvpActivity baseMvpActivity, AskEntity askEntity,
                            List<AskAnswerEntity> mDataList,UserEntity userEntity) {
        this.mBaseMvpActivity=baseMvpActivity;
        mAskEntity=askEntity;
        this.mDataList=mDataList;
        this.mUserEntity=userEntity;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        MyViewHolder  viewHolder;
        if(viewType == 1){//显示问题详情
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.item_ask_detail,viewGroup,false);
            viewHolder = new MyViewHolder (view,mItemClickListener,mLongClickListener,viewType,null);

        }else{  //显示回答列表
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.item_ask_answer_list,viewGroup,false);
            viewHolder = new MyViewHolder(view, mItemClickListener, mLongClickListener, viewType,
                    new IMyViewHolderClick() {
                        //点赞操作
                        //点赞需要的字段：目标记录号，点赞者记录号
                        @Override
                        public void like(AskAnswerEntity askAnswerEntity,final int position) {
                            if(askAnswerEntity.isLike()){
                                mBaseMvpActivity.showMessage("","不能重复点赞哟");
                                return ;
                            }

                            //mBaseMvpActivity.showMessage("","点赞："+position);
                            LikeEntity likeEntity=new LikeEntity();
                            likeEntity.setTargetrecno(String.valueOf(askAnswerEntity.getRecno()));
                            likeEntity.setUserrecno(mUserEntity.getRecno());
                            //调用点赞方法
                            LikeAskAnswerPresenter.getInstance().likeAskAnswer(likeEntity, new AskAnswerContract.ILikeAskAnswerView() {
                                @Override
                                public void likeAskAnswerSuccess(String msg) {
                                    mDataList.get(position).setLike(true);
                                    int likecount=mDataList.get(position).getLikecount();
                                    mDataList.get(position).setLikecount(likecount+1);
                                    //更新数据
                                    //notifyDataSetChanged();
                                    notifyItemChanged(position);
                                    mBaseMvpActivity.showMessage("",msg);
                                }

                                @Override
                                public void likeAskAnswerFail(String msg) {
                                    mBaseMvpActivity.showMessage("",msg);
                                }
                            });

                        }
                        //评论操作
                        @Override
                        public void comment(AskAnswerEntity askAnswerEntity, int position) {
                            //mBaseMvpActivity.showMessage("","评论："+position);
                        }


                        /**
                         * 点击全文，显示回答详情
                         *
                         * @param askAnswerEntity
                         * @param position
                         */
                        @Override
                        public void showContentDetails(AskAnswerEntity askAnswerEntity, int position) {
                            //mBaseMvpActivity.showMessage("","点击全文："+position);
                        }
                    });
        }
        //Log.d("AskAnswerAdapter","viewType="+viewType);
        return viewHolder;
    }







    /**
     * 将数据与界面进行绑定的操作
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder  viewHolder, int position) {
        if(position >= 1){   //显示回答列表
            AskAnswerEntity askAnswerEntity=mDataList.get(position);
            //显示作者
            viewHolder.txt_showAnswerAuthor.setText(askAnswerEntity.getShowusernickname());

            //显示时间
            String smartTime=TimeUtils.getSmartDate_1(String.valueOf(askAnswerEntity.getOptime()));
            viewHolder.txt_showAnswerTime.setText(smartTime);
            //显示回答内容
            //viewHolder.txt_showAnswerContent.setText(askAnswerEntity.getContent());
            TextViewUtils.toggleEllipsize(
                    mBaseMvpActivity,
                    viewHolder.txt_showAnswerContent,
                    10,
                    askAnswerEntity.getContent(),
                    "全文",
                    R.color.colorPrimary,
                    false);
            //显示浏览次数
            viewHolder.txt_showVisitCount.setText(askAnswerEntity.getVisitcount()+"次浏览");

            //显示点赞次数
            viewHolder.txt_like.setText("点赞（"+askAnswerEntity.getLikecount()+"人点赞）");
//            if(askAnswerEntity.getLikecount() > 0){
//                viewHolder.txt_like.setText("点赞（"+askAnswerEntity.getLikecount()+"人点赞）");
//            }

            //设置评论和点赞按钮的点击事件，将实体绑定到view上面
            viewHolder.txt_comment.setTag(askAnswerEntity);
            viewHolder.txt_like.setTag(askAnswerEntity);
            viewHolder.txt_showAnswerContent.setTag(askAnswerEntity);

            //判断是否当前对其是否点赞
            if(askAnswerEntity.isLike()){
                //设置点赞按钮左边的图片
                Drawable drawable = mBaseMvpActivity.getResources().getDrawable(R.drawable.ic_like);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_like.setCompoundDrawables(drawable,null,null,null);
            }else{
                //设置点赞按钮左边的图片
                Drawable drawable = mBaseMvpActivity.getResources().getDrawable(R.drawable.ic_unlike);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_like.setCompoundDrawables(drawable,null,null,null);
            }
        }else{  //显示问题详情
            //显示健康问答的明细
            viewHolder.txt_showAskTitle.setText(mAskEntity.getTitle());
            viewHolder.txt_showAnswerCount.setText(mDataList.size()-1+"个回答");
            viewHolder.txt_showAskContent.setText(mAskEntity.getContent());

            String smartTime= TimeUtils.getSmartDate_1(String.valueOf(mAskEntity.getOpdate()));
            viewHolder.txt_showAskTime.setText("创建于 "+smartTime);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 1;
        }
        return super.getItemViewType(position);
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
    public class MyViewHolder  extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnLongClickListener {

        public ImageView iv_showimg;

        /**
         * 显示作者
         */
        public TextView txt_showAnswerAuthor;
        /**
         * 显示回答时间
         */
        public TextView txt_showAnswerTime;
        /**
         * 显示浏览次数
         */
        public TextView txt_showVisitCount;
        /**
         * 显示内容
         */
        public TextView txt_showAnswerContent;
        /**
         * 评论按钮
         */
        public TextView txt_comment;

        /**
         * 点赞按钮
         */
        public TextView txt_like;







        /////////////////////  显示问题详情  /////////////////////
        /**
         * 显示问题标题
         */
        public TextView txt_showAskTitle;
        /**
         * 显示回答数量
         */
        public TextView txt_showAnswerCount;
        /**
         * 显示问题的描述
         */
        public TextView txt_showAskContent;
        /**
         * 显示问题的发表时间
         */
        public TextView txt_showAskTime;



        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;
        private int viewType;

        /**
         * item内部控件点击事件监听接口，比如item内部的按钮
         */
        IMyViewHolderClick mHolderClick;

        public MyViewHolder (View view,MyItemClickListener myItemClickListener,
                             MyItemLongClickListener mLongClickListener,int viewType,IMyViewHolderClick holderClick){
            super(view);
            this.viewType=viewType;
            this.mHolderClick=holderClick;

            if(viewType == 0){  //显示回答列表
                iv_showimg = view.findViewById(R.id.iv_showimg);
                txt_showAnswerAuthor = view.findViewById(R.id.txt_showAnswerAuthor);
                txt_showAnswerTime = (TextView) view.findViewById(R.id.txt_showAnswerTime);
                txt_showAnswerContent = view.findViewById(R.id.txt_showAnswerContent);
                txt_showVisitCount = view.findViewById(R.id.txt_showVisitCount);
                txt_comment = view.findViewById(R.id.txt_comment);
                txt_like =  view.findViewById(R.id.txt_like);

                txt_comment.setOnClickListener(this);
                txt_like.setOnClickListener(this);
                txt_showAnswerContent.setOnClickListener(this);
            }else{  //显示问题详情
                txt_showAskTitle = view.findViewById(R.id.txt_showAskTitle);
                txt_showAnswerCount = view.findViewById(R.id.txt_showAnswerCount);
                txt_showAskContent = view.findViewById(R.id.txt_showAskContent);
                txt_showAskTime = view.findViewById(R.id.txt_showAskTime);
            }

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
            switch (v.getId()){
                case R.id.txt_comment:  //评论按钮
                    mHolderClick.comment((AskAnswerEntity) v.getTag(),getLayoutPosition());
                    break;
                case R.id.txt_like: //点赞按钮
                    mHolderClick.like((AskAnswerEntity) v.getTag(),getLayoutPosition());
                    break;
                case R.id.txt_showAnswerContent:    //全文
                    mHolderClick.showContentDetails((AskAnswerEntity) v.getTag(),getLayoutPosition());
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
         * 点赞
         * @param askAnswerEntity
         * @param position
         */
        void like(AskAnswerEntity askAnswerEntity,int position);

        /**
         * 评论
         * @param askAnswerEntity
         * @param position
         */
        void comment(AskAnswerEntity askAnswerEntity,int position);

        /**
         * 点击全文，显示回答详情
         * @param askAnswerEntity
         * @param position
         */
        void showContentDetails(AskAnswerEntity askAnswerEntity,int position);

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
