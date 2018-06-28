package com.clw.phaapp.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.utils.TextViewUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LikeEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.healthqa.AcceptAskAnswerPresenter;
import com.clw.phaapp.presenter.healthqa.DeleteAskAnswerPresenter;
import com.clw.phaapp.presenter.healthqa.LikeAskAnswerPresenter;

import java.util.List;

/**
 * 采纳回答，健康问答列表的适配器
 */
public class AcceptAskAnswerAdapter extends RecyclerView.Adapter<AcceptAskAnswerAdapter.MyViewHolder > {

    private static String TAG="AcceptAskAnswerAdapter";

    /**
     * 回答列表
     */
    private List<AskAnswerEntity> mDataList;

    private BaseMvpActivity mBaseMvpActivity;

    private AskEntity mAskEntity;

    /**
     * 采纳回答Presenter
     */
    private AcceptAskAnswerPresenter mAcceptAskAnswerPresenter=new AcceptAskAnswerPresenter();

    /**
     * 删除回答Presenter
     */
    private DeleteAskAnswerPresenter mDeleteAskAnswerPresenter=new DeleteAskAnswerPresenter();


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

    public AcceptAskAnswerAdapter(BaseMvpActivity baseMvpActivity, AskEntity askEntity,
                                  List<AskAnswerEntity> mDataList, UserEntity userEntity) {
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
                    inflate(R.layout.item_accept_ask_answer_list,viewGroup,false);
            viewHolder = new MyViewHolder(view, mItemClickListener, mLongClickListener, viewType,
                    new IMyViewHolderClick() {
                        //采纳回答
                        @Override
                        public void acceptAskAnswer(final AskAnswerEntity askAnswerEntity,final int position) {
                            if(askAnswerEntity.getStatus() == 1){
                                mBaseMvpActivity.showMessage("","该回答已经被采纳了");
                                return ;
                            }
                            AlertDialog alertDialog = new AlertDialog.Builder(mBaseMvpActivity)
                                    .setMessage("确定要采纳该回答吗？")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            mAcceptAskAnswerPresenter.acceptAskAnswer(askAnswerEntity, new AskAnswerContract.IAcceptAskAnswerView() {
                                                @Override
                                                public void acceptAskAnswerSuccess(String msg) {
                                                    mBaseMvpActivity.showMessage("",msg);
                                                    //状态：0未采纳，1已采纳
                                                    mDataList.get(position).setStatus((byte)1);
                                                    notifyItemChanged(position);
                                                }

                                                @Override
                                                public void acceptAskAnswerFail(String msg) {
                                                    mBaseMvpActivity.showMessage("",msg);
                                                }

                                                @Override
                                                public boolean isActive() {
                                                    return mBaseMvpActivity.isActive();
                                                }

                                                @Override
                                                public void showLoading(String msg) {

                                                }

                                                @Override
                                                public void dismissLoading() {

                                                }

                                                @Override
                                                public void showMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showSuccessMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showErrorMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showProgress(String msg) {

                                                }

                                                @Override
                                                public void dismissProgress() {

                                                }

                                                @Override
                                                public void closeView() {

                                                }

                                                @Override
                                                public boolean checkNetworkState() {
                                                    return NetworkUtils.isAvailable(mBaseMvpActivity);
                                                }
                                            });

                                            dialog.dismiss();
                                        }
                                    }).create();
                            alertDialog.show();

                        }
                        //删除回答
                        @Override
                        public void deleteAskAnswer(final AskAnswerEntity askAnswerEntity, final int position) {
                            //mBaseMvpActivity.showMessage("","删除回答："+position);

                            AlertDialog alertDialog = new AlertDialog.Builder(mBaseMvpActivity)
                                    .setTitle("温馨提示")
                                    .setMessage("确定要删除该回答吗？")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            mDeleteAskAnswerPresenter.deleteAskAnswer(askAnswerEntity, new AskAnswerContract.IDeleteAskAnswerView() {
                                                @Override
                                                public void deleteAskAnswerSuccess(String msg) {
                                                    mBaseMvpActivity.showMessage("",msg);
                                                    mDataList.remove(position);
                                                    notifyDataSetChanged();
                                                }

                                                @Override
                                                public void deleteAskAnswerFail(String msg) {
                                                    mBaseMvpActivity.showMessage("",msg);
                                                }

                                                @Override
                                                public boolean isActive() {
                                                    return mBaseMvpActivity.isActive();
                                                }

                                                @Override
                                                public void showLoading(String msg) {

                                                }

                                                @Override
                                                public void dismissLoading() {

                                                }

                                                @Override
                                                public void showMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showSuccessMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showErrorMessage(String title, String msg) {

                                                }

                                                @Override
                                                public void showProgress(String msg) {

                                                }

                                                @Override
                                                public void dismissProgress() {

                                                }

                                                @Override
                                                public void closeView() {

                                                }

                                                @Override
                                                public boolean checkNetworkState() {
                                                    return NetworkUtils.isAvailable(mBaseMvpActivity);
                                                }
                                            });

                                            dialog.dismiss();
                                        }
                                    }).create();
                            alertDialog.show();


                        }

                        /**
                         * 点击全文，显示回答详情
                         *
                         * @param askAnswerEntity
                         * @param position
                         */
                        @Override
                        public void showContentDetails(AskAnswerEntity askAnswerEntity, int position) {
                            mBaseMvpActivity.showMessage("","点击全文："+position);
                        }

                    });
        }
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

            //显示回答的状态   状态：0未采纳，1已采纳
            if(askAnswerEntity.getStatus()!=null){
                if(askAnswerEntity.getStatus() == 0){
                    viewHolder.txt_showAnswerStatus.setTextColor(Color.RED);
                    viewHolder.txt_showAnswerStatus.setText("未采纳");
                }else if(askAnswerEntity.getStatus() == 1){
                    viewHolder.txt_showAnswerStatus.setTextColor(Color.GREEN);
                    viewHolder.txt_showAnswerStatus.setText("已采纳");
                }else{
                    viewHolder.txt_showAnswerStatus.setText("");
                }
            }

            //显示回答内容
            TextViewUtils.toggleEllipsize(
                    mBaseMvpActivity,
                    viewHolder.txt_showAnswerContent,
                    10,
                    askAnswerEntity.getContent(),
                    "全文",
                    R.color.colorPrimary,
                    false);


            //设置采纳回答和删除回答按钮的点击事件，将实体绑定到view上面
            viewHolder.txt_acceptAskAnswer.setTag(askAnswerEntity);
            viewHolder.txt_deleteAskAnswer.setTag(askAnswerEntity);
            viewHolder.txt_showAnswerContent.setTag(askAnswerEntity);

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
         * 显示回答状态
         */
        public TextView txt_showAnswerStatus;

        /**
         * 显示内容
         */
        public TextView txt_showAnswerContent;
        /**
         * 采纳回答按钮
         */
        public TextView txt_acceptAskAnswer;

        /**
         * 删除回答按钮
         */
        public TextView txt_deleteAskAnswer;







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
                txt_showAnswerTime = view.findViewById(R.id.txt_showAnswerTime);
                txt_showAnswerStatus = view.findViewById(R.id.txt_showAnswerStatus);
                txt_showAnswerContent = view.findViewById(R.id.txt_showAnswerContent);
                txt_acceptAskAnswer = view.findViewById(R.id.txt_acceptAskAnswer);
                txt_deleteAskAnswer =  view.findViewById(R.id.txt_deleteAskAnswer);

                txt_acceptAskAnswer.setOnClickListener(this);
                txt_deleteAskAnswer.setOnClickListener(this);
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
                case R.id.txt_acceptAskAnswer:  //采纳回答按钮
                    mHolderClick.acceptAskAnswer((AskAnswerEntity) v.getTag(),getLayoutPosition());
                    break;
                case R.id.txt_deleteAskAnswer: //删除回答按钮
                    mHolderClick.deleteAskAnswer((AskAnswerEntity) v.getTag(),getLayoutPosition());
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
         * 采纳回答
         * @param askAnswerEntity
         * @param position
         */
        void acceptAskAnswer(AskAnswerEntity askAnswerEntity, int position);

        /**
         * 删除回答
         * @param askAnswerEntity
         * @param position
         */
        void deleteAskAnswer(AskAnswerEntity askAnswerEntity, int position);


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
