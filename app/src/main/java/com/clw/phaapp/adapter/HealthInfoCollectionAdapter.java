package com.clw.phaapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.contract.HealthInfoContract;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.healthinfo.GetOneHealthInfoPresenter;
import com.clw.phaapp.presenter.user.DeleteUserCollectionPresenter;
import com.clw.phaapp.ui.healthinfo.HealthInfoDetailActivity;
import com.clw.phaapp.ui.healthqa.AskDatailActivity;

import java.util.List;

/**
 * 收藏列表,健康问答列表的适配器
 */
public class HealthInfoCollectionAdapter extends RecyclerView.Adapter<HealthInfoCollectionAdapter.MyViewHolder > {

    private BaseMvpFragmentV4 mBaseMvpFragmentV4;

    private Context mContext;


    protected DeleteUserCollectionPresenter mDeleteUserCollectionPresenter=new DeleteUserCollectionPresenter();


    protected GetOneHealthInfoPresenter mGetOneHealthInfoPresenter=new GetOneHealthInfoPresenter();

    /**
     * 收藏列表
     */
    private List<UserCollectionEntity> mCollectionList;


    /**
     * 健康问答列表
     */
    private List<HealthInfoEntity> mDataList;




    /**
     * 点击监听接口对象
     */
    private MyItemClickListener mItemClickListener;

    /**
     * 长按监听接口对象
     */
    private MyItemLongClickListener mLongClickListener;

    public HealthInfoCollectionAdapter(List<UserCollectionEntity> mCollectionList, List<HealthInfoEntity> mHealthInfoEntityList,
                                       BaseMvpFragmentV4 mBaseMvpFragmentV4, Context context) {
        this.mCollectionList=mCollectionList;
        this.mDataList=mHealthInfoEntityList;
        this.mBaseMvpFragmentV4=mBaseMvpFragmentV4;
        this.mContext=context;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_health_info_collection_list,viewGroup,false);
        MyViewHolder  viewHolder = new MyViewHolder(view, mItemClickListener, mLongClickListener,
                new IMyViewHolderClick() {

                    //进入资讯详情
                    @Override
                    public void onItemClick(HealthInfoEntity healthInfoEntity, int position) {
                        Intent intent=new Intent(mContext,HealthInfoDetailActivity.class);
                        intent.putExtra("id",healthInfoEntity.getId());
                        mContext.startActivity(intent);
                    }

                    //删除收藏
                    @Override
                    public void deleteUserCollection(final UserCollectionEntity userCollectionEntity,final int position) {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                                .setMessage("确定要取消该收藏吗？")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sureDeleteHealthInfoCollection(userCollectionEntity,position);
                                        dialog.dismiss();
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
        return viewHolder;
    }

    /**
     * 确定取消收藏
     * @param userCollectionEntity
     */
    private void sureDeleteHealthInfoCollection(final UserCollectionEntity userCollectionEntity, final int position){
        mDeleteUserCollectionPresenter.deleteUserCollection(userCollectionEntity, new UserCollectionContract.IDeleteUserCollectionView() {
            @Override
            public void deleteUserCollectionSuccess(String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
                mCollectionList.remove(position);
                //mDataList.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void deleteUserCollectionFail(String msg) {
                mBaseMvpFragmentV4.showMessage("",msg);
            }

            @Override
            public boolean isActive() {
                return mBaseMvpFragmentV4.isActive();
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
    public void onBindViewHolder(final MyViewHolder  viewHolder, int position) {
        UserCollectionEntity userCollectionEntity=mCollectionList.get(position);

        HealthInfoEntity healthInfoEntity=new HealthInfoEntity();
        healthInfoEntity.setId(userCollectionEntity.getTargetrecno());
        mGetOneHealthInfoPresenter.getOneHealthInfo(healthInfoEntity, new HealthInfoContract.IGetOneHealthInfoView() {
            @Override
            public void getOneHealthInfoSuccess(HealthInfoEntity healthInfoEntity) {
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

            @Override
            public void getOneHealthInfoFail() {

            }

            @Override
            public boolean isActive() {
                return mBaseMvpFragmentV4.isActive();
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
                return NetworkUtils.isAvailable(mContext);
            }
        });

//        HealthInfoEntity healthInfoEntity=mDataList.get(position);
//        viewHolder.txt_showHealthInfoTitle.setText(healthInfoEntity.getTitle()+"");
//        viewHolder.txt_showHealthInfoAuthor.setText(healthInfoEntity.getAuthor()+"");
//        String smallDate= TimeUtils.getSmartDate_3(healthInfoEntity.getTime());
//        viewHolder.txt_showHealthInfoTime.setText(smallDate+"");
//        //加载图片
//        if (healthInfoEntity.getImg() != null) {
//            Glide.with(mContext)
//                    .load(healthInfoEntity.getImg())
//                    .into(viewHolder.iv_showHealthInfoImage);
//        }

        //设置取消收藏的点击事件，将实体绑定到view上面
        viewHolder.ll_item.setTag(healthInfoEntity);
        viewHolder.btn_deleteCollection.setTag(userCollectionEntity);
    }

    /**
     * 获取数据的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mCollectionList.size();
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

        /**
         * 取消收藏按钮
         */
        public Button btn_deleteCollection;

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
            txt_showHealthInfoTitle = (TextView) view.findViewById(R.id.txt_showHealthInfoTitle);
            txt_showHealthInfoAuthor = (TextView) view.findViewById(R.id.txt_showHealthInfoAuthor);
            txt_showHealthInfoTime = (TextView) view.findViewById(R.id.txt_showHealthInfoTime);
            iv_showHealthInfoImage = (ImageView)  view.findViewById(R.id.iv_showHealthInfoImage);

            //Item布局
            ll_item =  view.findViewById(R.id.ll_item);
            ll_item.setOnClickListener(this);

            //删除问答按钮
            btn_deleteCollection =  view.findViewById(R.id.btn_deleteCollection);
            btn_deleteCollection.setOnClickListener(this);

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
                    mHolderClick.onItemClick((HealthInfoEntity) v.getTag(),getLayoutPosition());
                    break;

                case R.id.btn_deleteCollection:    //取消收藏
                    mHolderClick.deleteUserCollection((UserCollectionEntity) v.getTag(),getLayoutPosition());
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
         * 点击item，进入资讯详情界面
         * @param healthInfoEntity
         * @param position
         */
        void onItemClick(HealthInfoEntity healthInfoEntity, int position);

        /**
         * 删除收藏
         * @param userCollectionEntity
         * @param position
         */
        void deleteUserCollection(UserCollectionEntity userCollectionEntity, int position);

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
