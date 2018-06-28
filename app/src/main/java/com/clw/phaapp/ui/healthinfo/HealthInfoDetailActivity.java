package com.clw.phaapp.ui.healthinfo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.HealthInfoContract;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.common.LogPresenter;
import com.clw.phaapp.presenter.healthinfo.CollectHealthInfoPresenter;
import com.clw.phaapp.presenter.healthinfo.GetOneHealthInfoPresenter;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.ganxin.library.LoadDataLayout;

/**
 * 健康资讯明细界面
 */
public class HealthInfoDetailActivity extends BaseMvpActivity<HealthInfoContract.IGetOneHealthInfoView, GetOneHealthInfoPresenter>
        implements HealthInfoContract.IGetOneHealthInfoView, View.OnClickListener {

    private final static String TAG = "HealthInfoDetailActivity";


    private CollectHealthInfoPresenter mCollectHealthInfoPresenter=new CollectHealthInfoPresenter();


    private TextView txt_showHealthInfoTitle;
    private TextView txt_showHealthInfoContent;
    private ImageView iv_showHealthInfoImage;

    /**
     * 记录健康资讯信息
     */
    private HealthInfoEntity mHealthInfoEntity;
    private LoadDataLayout loadDataLayout;
    private TextView txt_showHealthInfoAuthor;
    private TextView txt_showHealthInfoTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info_detail);
        initActivity();
        initView();
        initUserInfoFromApplication();
    }

    String[] mMenuItems = {"收藏资讯", "分享资讯"};


    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("");

        setToolbarOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_menu:
                        if (!checkNetworkState()) {
                            showShortToast(CommonHintInfo.NO_NETWORDK);
                        } else {
                            final NormalListDialog dialog = new NormalListDialog(mContext, mMenuItems);
                            dialog.isTitleShow(false)
                                    .itemTextSize(18)
                                    .widthScale(0.8f)
                                    .layoutAnimation(null)
                                    .show();
                            dialog.setOnOperItemClickL(new OnOperItemClickL() {
                                @Override
                                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    if(!isLogin){
                                        dialog.dismiss();
                                        showShortToast("请先登录");
                                        return ;
                                    }

                                    //position
                                    //showShortToast(mMenuItems[position] + "");
                                    if(position == 0){  //收藏资讯

                                        //要提供的字段：目标记录号，用户记录号，收藏类型
                                        UserCollectionEntity userCollectionEntity=new UserCollectionEntity();

                                        if(mUserEntity==null || mHealthInfoEntity==null){
                                            return ;
                                        }else{
                                            userCollectionEntity.setUserrecno(mUserEntity.getRecno());
                                            userCollectionEntity.setTargetrecno(mHealthInfoEntity.getId());
                                            //收藏的类型：0健康资讯  1 综合资讯，2疾病资讯，3食品资讯
                                            userCollectionEntity.setType((byte)0);
                                        }

                                        mCollectHealthInfoPresenter.collectHealthInfo(userCollectionEntity, new UserCollectionContract.ICollectHealthInfoView() {
                                            @Override
                                            public void collectHealthInfoSuccess(String msg) {
                                                showShortToast(msg);
                                            }

                                            @Override
                                            public void collectHealthInfoFail(String msg) {
                                                showShortToast(msg);
                                            }

                                            @Override
                                            public boolean isActive() {
                                                return isActive;
                                            }

                                            @Override
                                            public void showLoading(String msg) {

                                            }

                                            @Override
                                            public void dismissLoading() {

                                            }

                                            @Override
                                            public void showMessage(String title, String msg) {
                                                showShortToast(msg);
                                            }

                                            @Override
                                            public void showSuccessMessage(String title, String msg) {
                                                showShortToast(msg);
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
                                    }else if(position == 1){    //分享资讯

                                    }else{

                                    }
                                    dialog.dismiss();
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        txt_showHealthInfoTitle = (TextView) findViewById(R.id.txt_showHealthInfoTitle);
        txt_showHealthInfoContent = (TextView) findViewById(R.id.txt_showHealthInfoContent);
        iv_showHealthInfoImage = (ImageView) findViewById(R.id.iv_showHealthInfoImage);
        txt_showHealthInfoAuthor = (TextView) findViewById(R.id.txt_showHealthInfoAuthor);
        txt_showHealthInfoTime = (TextView) findViewById(R.id.txt_showHealthInfoTime);
        loadDataLayout = (LoadDataLayout) findViewById(R.id.loadDataLayout);
        //设置重新加载操作
        loadDataLayout.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                loadDataLayout.setStatus(LoadDataLayout.LOADING);//加载中
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadHealthInfoDetail();
                        //loadDataLayout.setStatus(LoadDataLayout.LOADING); //加载中
                        //loadDataLayout.setStatus(LoadDataLayout.EMPTY); //无数据
                        //loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
                        //loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
                        //loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功

                    }
                }, 2000);
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        //业务调用需要在这里调用，因为在onResume方法中，基类的Activity会将当前Activity与Presenter绑定起来。
        //如果在Activity还没有与Presenter绑定之前就操作UI，就会出现空指针异常。
        if (mHealthInfoEntity == null) {
            loadHealthInfoDetail();
        }

    }


    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetOneHealthInfoPresenter initPresenter() {
        return new GetOneHealthInfoPresenter();
    }

    /**
     * 成功回调
     */
    @Override
    public void getOneHealthInfoSuccess(HealthInfoEntity healthInfoEntity) {
        loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功
        mHealthInfoEntity = healthInfoEntity;


        if (healthInfoEntity.getImg() != null) {
            //加载图片
            Glide.with(mContext)
                    .load(healthInfoEntity.getImg())
                    .into(iv_showHealthInfoImage);
        }
        setToolbarCenterTitle(healthInfoEntity.getTname());
        txt_showHealthInfoTitle.setText(healthInfoEntity.getTitle());
        txt_showHealthInfoAuthor.setText(healthInfoEntity.getAuthor());
        txt_showHealthInfoTime.setText(healthInfoEntity.getTime());

        txt_showHealthInfoContent.setText(Html.fromHtml(healthInfoEntity.getContent()));
        Log.d(TAG,healthInfoEntity.toString());
    }

    /**
     * 失败回调
     */
    @Override
    public void getOneHealthInfoFail() {
        loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    /**
     * 加载健康资讯详情
     */
    private void loadHealthInfoDetail() {
        if (!checkNetworkState()) {
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
            return;
        }
        loadDataLayout.setStatus(LoadDataLayout.LOADING);//加载中
        //获取健康资讯的id
        String id = getIntent().getStringExtra("id");
        HealthInfoEntity healthInfoEntity = new HealthInfoEntity();
        healthInfoEntity.setId(id);
        //查询资讯明细
        mPresenter.getOneHealthInfo(healthInfoEntity);

        //添加日志
        if(getIntent().getStringExtra("tid")!=null){
            String tid = getIntent().getStringExtra("tid");
            LogEntity logEntity=new LogEntity();
            if(mUserEntity!=null){
                //添加用户记录号
                logEntity.setUserrecno(mUserEntity.getRecno());
            }
            //设置访问类型
            logEntity.setType(Byte.parseByte(tid));
            //设置目标记录号
            logEntity.setTargetrecno(id);
            LogPresenter.getInstance().insertLogRecord(logEntity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //想要使用顶部栏的菜单，需要重写此方法，加载相应menu文件到布局，并且添加菜单监听器
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}
