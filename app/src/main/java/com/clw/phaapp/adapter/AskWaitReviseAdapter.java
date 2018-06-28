package com.clw.phaapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.DeleteAskPresenter;
import com.clw.phaapp.ui.healthqa.EditAskActivity;

import java.util.List;

/**
 * 待修改,健康问答列表的适配器
 */
public class AskWaitReviseAdapter extends RecyclerView.Adapter<AskWaitReviseAdapter.MyViewHolder > {

    private BaseMvpFragmentV4 mBaseMvpFragmentV4;

    private Context mContext;


    protected DeleteAskPresenter mDeleteAskPresenter=new DeleteAskPresenter();



    /**
     * 健康问答列表
     */
    private List<AskEntity> mDataList;

    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public AskWaitReviseAdapter(List<AskEntity> mAskEntityList, BaseMvpFragmentV4 mBaseMvpFragmentV4, Context context) {
        this.mDataList=mAskEntityList;
        this.mBaseMvpFragmentV4=mBaseMvpFragmentV4;
        this.mContext=context;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_management_ask_wait_review_list,viewGroup,false);
        MyViewHolder  viewHolder = new MyViewHolder(view, mItemClickListener, mLongClickListener,
                new IMyViewHolderClick() {

                    //进入问答详情
                    @Override
                    public void onItemClick(AskEntity askEntity, int position) {
//                        Intent intent=new Intent(mContext,AcceptAskAnswerActivity.class);
//                        intent.putExtra("ask",askEntity);
//                        mContext.startActivity(intent);
                    }

                    /**
                     * 编辑回答
                     *
                     * @param askEntity
                     * @param position
                     */
                    @Override
                    public void editAsk(AskEntity askEntity, int position) {
                        //mBaseMvpFragmentV4.showMessage("","编辑回答");
                        Intent intent=new Intent(mContext,EditAskActivity.class);
                        intent.putExtra("ask",askEntity);
                        mContext.startActivity(intent);
                    }

                    //删除问答操作
                    @Override
                    public void deleteAsk(final AskEntity askEntity, int position) {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                                .setMessage("确定要删除该问答吗？")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sureDeleteAsk(askEntity);
                                        dialog.dismiss();
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
        return viewHolder;
    }

    /**
     * 确定删除问答
     * @param askEntity
     */
    private void sureDeleteAsk(final AskEntity askEntity){
        mDeleteAskPresenter.deleteAsk(askEntity, new AskContract.IDeleteAskView() {
            @Override
            public void deleteAskSuccess(String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
                mDataList.remove(askEntity);
                notifyDataSetChanged();
            }

            @Override
            public void deleteAskFail(String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public void showLoading(String msg) {

            }

            @Override
            public void dismissLoading() {

            }

            @Override
            public void showMessage(String title, String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
            }

            @Override
            public void showSuccessMessage(String title, String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
            }

            @Override
            public void showErrorMessage(String title, String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
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
                return NetworkUtils.isAvailable(mContext);
            }
        });
    }






    /**
     * 将数据与界面进行绑定的操作
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder  viewHolder, int position) {
        AskEntity askEntity=mDataList.get(position);

        //显示问题的标题
        viewHolder.txt_showAskTitle.setText(askEntity.getTitle()+"");


        //显示问答得状态：0待审核，1审核通过，2待修改
        byte status=askEntity.getStatus();
        if(status == 0){
            viewHolder.txt_showAskStatus.setTextColor(Color.RED);
            viewHolder.txt_showAskStatus.setText("待审核");
        }else if(status == 1){
            viewHolder.txt_showAskStatus.setTextColor(Color.GREEN);
            viewHolder.txt_showAskStatus.setText("审核通过");
        }else{
            viewHolder.txt_showAskStatus.setTextColor(Color.RED);
            viewHolder.txt_showAskStatus.setText("待修改");
        }

        //显示时间
        String smartTime= TimeUtils.getSmartDate_1(String.valueOf(askEntity.getOpdate()));
        viewHolder.txt_showAskTime.setText(smartTime);

        //设置删除问题按钮的点击事件，将实体绑定到view上面
        viewHolder.ll_item.setTag(askEntity);
        viewHolder.btn_editAsk.setTag(askEntity);
        viewHolder.btn_deleteAsk.setTag(askEntity);
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
        public TextView txt_showAskTitle;

        /**
         * 显示问题状态
         */
        public TextView txt_showAskStatus;

        /**
         * 显示发布时间
         */
        public TextView txt_showAskTime;

        /**
         * 编辑问答按钮
         */
        public Button btn_editAsk;
        /**
         * 删除问答按钮
         */
        public Button btn_deleteAsk;

        /**
         *
         */
        LinearLayout ll_item;




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
            txt_showAskTitle = view.findViewById(R.id.txt_showAskTitle);
            txt_showAskStatus =  view.findViewById(R.id.txt_showAskStatus);
            txt_showAskTime =  view.findViewById(R.id.txt_showAskTime);

            //Item布局
            ll_item =  view.findViewById(R.id.ll_item);
            ll_item.setOnClickListener(this);

            //编辑问题
            btn_editAsk = view.findViewById(R.id.btn_editAsk);
            btn_editAsk.setOnClickListener(this);

            //删除问答按钮
            btn_deleteAsk = view.findViewById(R.id.btn_deleteAsk);
            btn_deleteAsk.setOnClickListener(this);

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
                case R.id.ll_item:  //进入问答详情界面
                    mHolderClick.onItemClick((AskEntity) v.getTag(),getLayoutPosition());
                    break;

                case R.id.btn_editAsk:  //编辑问题
                    mHolderClick.editAsk((AskEntity) v.getTag(),getLayoutPosition());
                    break;

                case R.id.btn_deleteAsk:    //删除问答
                    mHolderClick.deleteAsk((AskEntity) v.getTag(),getLayoutPosition());
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
         * @param askEntity
         * @param position
         */
        void onItemClick(AskEntity askEntity, int position);

        /**
         * 编辑回答
         * @param askEntity
         * @param position
         */
        void editAsk(AskEntity askEntity, int position);

        /**
         * 删除回答
         * @param askEntity
         * @param position
         */
        void deleteAsk(AskEntity askEntity, int position);

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
