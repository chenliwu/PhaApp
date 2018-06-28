package com.clw.phaapp.ui.user;

import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.clw.mysdk.base.fragment.BaseFragment_SupportV4;
import com.clw.phaapp.R;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.ui.healthqa.AskManagementActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心Fragment
 */
public class PersonalityFragment extends BaseFragment_SupportV4 implements View.OnClickListener {

    private static PersonalityFragment INSTANCE;

    private CircleImageView civ_head;
    private TextView tv_username;
    private LinearLayout ll_ask_management;
    private LinearLayout ll_collection;
//    private LinearLayout ll_history;
    private RelativeLayout rl_message;
    private TextView tv_message_indicator;

    //private TextView tv_btn_settings;
    private TextView tv_btn_about;
    private TextView tv_btn_feekback;

    /**
     * 应用程序全局变量
     */
    private PhaApplication mPhaApplication;


    /**
     * 用户是否已经登录
     */
    private boolean isLogin;

    /**
     * 如果已经登录，记录登录用户的信息
     */
    private UserEntity mUserEntity;

    /**
     * 获取实例对象
     *
     * @return
     */
    public static PersonalityFragment getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new PersonalityFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_personality, container, false);
        initFragment();
        initView();
        updateContent();
        hideIndecator();
        return mBaseView;
    }



    @Override
    protected void initFragment() {
        mPhaApplication=(PhaApplication)getActivity().getApplication();
    }

    /**
     * 从全局应用变量中中读取用户信息，并初始化
     */
    private void initUserInfoFromApplication(){
        if(mPhaApplication!=null){
            mUserEntity=mPhaApplication.getUserEntity();
            isLogin=mPhaApplication.isLogin();
        }
    }


    /**
     * 隐藏指示器
     */
    private void hideIndecator(){
        tv_message_indicator.setVisibility(View.GONE);
    }


    /**
     * 显示消息数量
     * @param number
     */
    public void showMessageNumber(int number){
        if(number > 0){
            tv_message_indicator.setVisibility(View.VISIBLE);
            tv_message_indicator.setText(number+"");
        }else{
            tv_message_indicator.setVisibility(View.GONE);
        }
    }



    /**
     * 刷新界面信息
     */
    private void updateContent(){
        initUserInfoFromApplication();
        showUserInfo();
    }

    /**
     * 显示用户的信息
     */
    public void showUserInfo() {
        if (null != mUserEntity && isLogin) {
            tv_username.setText(mUserEntity.getNickname());

            //显示头像
            String path=mUserEntity.getHeaderurl();
            //showMessageDialog("头像路径","头像路径："+path);
            if(path!=null && path.length() > 0){
                Glide.with(mContext)
                        .load("http://"+ServerInformation.SERVER_IP+":"+ServerInformation.SERVER_PORT+path)
                        .into(civ_head);
            }
//            Glide.with(mContext)
//                    .load("http://"+ServerInformation.SERVER_IP+":"+ServerInformation.SERVER_PORT+
//                            "/PHA_APP_Server/fileUpload/images/8792a695-28f3-42a8-8c27-c16cc03f6e8c.jpg")
//                    .into(civ_head);
        }else{
            tv_username.setText("请点击登录");
        }
    }


    /**
     * 弹出对话框
     *
     * @param title   对话框的标题
     * @param conetnt 对话框的内容
     */
    private void showMessageDialog(String title, String conetnt) {
        AlertDialog.Builder b = new AlertDialog.Builder(mContext);
        // 设置对话框标题
        b.setTitle(title);
        // 设置对话框图标
        // b.setIcon(R.drawable.ic_launcher);
        // 设置对话框的内容
        b.setMessage(conetnt);
        // 添加对话框的按钮
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whitch) {

            }
        });
        b.show();
    }


    @Override
    protected void initView() {

        civ_head = (CircleImageView) mBaseView.findViewById(R.id.civ_head);
        civ_head.setOnClickListener(this);
        tv_username = (TextView) mBaseView.findViewById(R.id.tv_username);
        ll_ask_management = (LinearLayout) mBaseView.findViewById(R.id.ll_ask_management);
        ll_ask_management.setOnClickListener(this);
        ll_collection = (LinearLayout) mBaseView.findViewById(R.id.ll_collection);
        ll_collection.setOnClickListener(this);
        //ll_history = (LinearLayout) mBaseView.findViewById(R.id.ll_history);
        //ll_history.setOnClickListener(this);
        //tv_btn_settings = (TextView) mBaseView.findViewById(R.id.tv_btn_settings);
        //tv_btn_settings.setOnClickListener(this);
        tv_btn_about = (TextView) mBaseView.findViewById(R.id.tv_btn_about);
        tv_btn_about.setOnClickListener(this);
        tv_btn_feekback = (TextView) mBaseView.findViewById(R.id.tv_btn_feekback);
        tv_btn_feekback.setOnClickListener(this);
        tv_message_indicator = (TextView) mBaseView.findViewById(R.id.tv_message_indicator);
        rl_message = (RelativeLayout) mBaseView.findViewById(R.id.rl_message);
        rl_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_head: //点击头像
                if (!isLogin) {   //未登录就进入登录界面
                    startActivity(LoginActivity.class);
                } else {    //进入个人信息界面
                    startActivity(ShowUserinfoActivity.class);
                }
                break;
            case R.id.ll_ask_management: //进入健康问答管理
                if(isLogin){   //已经登录
                    startActivity(AskManagementActivity.class);
                }else{
                    showShortToast("请先登录账号");
                }

                break;
            case R.id.ll_collection:    //进入收藏界面
                if(isLogin){   //已经登录
                    startActivity(CollectionListActivity.class);
                }else{
                    showShortToast("请先登录账号");
                }
                break;
            //case R.id.ll_history:   //进入浏览历史
            //    startActivity(HistoryActivity.class);
            //    break;
            case R.id.rl_message:   //进入消息通知
                if(isLogin){   //已经登录
                    startActivity(MessageActivity.class);
                }else{
                    showShortToast("请先登录账号");
                }
                break;
            //case R.id.tv_btn_settings:
            //    break;
            case R.id.tv_btn_feekback:  //进入用户反馈
                startActivity(UserFeedbackActivity.class);
                break;
            case R.id.tv_btn_about: //进入关于软件
                startActivity(AboutSoftwareActivity.class);
                break;
        }
    }

    /**
     * 登录广播监听
     */
    public class LoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateContent();
        }
    }



    /////////   登录广播接收器，检测登录广播，更新UI界面   /////////
    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    LoginReceiver mLoginReceiver;

    @Override
    public void onResume() {
        super.onResume();
        //动态注册一个广播接受器
        IntentFilter intentFilter = new IntentFilter(LOGIN_ACTION);
        if(mLoginReceiver == null){
            mLoginReceiver = new LoginReceiver();
        }
        mContext.registerReceiver(mLoginReceiver, intentFilter);
        updateContent();

        Log.d(getTag(),"onResume");
        if(mPhaApplication!=null){
            showMessageNumber(mPhaApplication.getMessageNumber());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginReceiver != null) {
            mContext.unregisterReceiver(mLoginReceiver);
        }

    }


}
