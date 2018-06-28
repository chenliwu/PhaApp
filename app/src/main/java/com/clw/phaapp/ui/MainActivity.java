package com.clw.phaapp.ui;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.clw.mysdk.base.activity.BaseFragmentActivity;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.contract.MessageContract;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.GetMessageNumberPresenter;
import com.clw.phaapp.presenter.user.GetUserInfoPresenter;
import com.clw.phaapp.ui.healthqa.HealthQAFragment;
import com.clw.phaapp.ui.healthinfo.HealthInformationFragment;
import com.clw.phaapp.ui.healthtool.HealthToolFragment;
import com.clw.phaapp.ui.user.PersonalityFragment;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private List<Fragment> mFragmentList;

    private HealthInformationFragment mHealthInformationFragment;
    private HealthQAFragment mHealthAskFragment;
    private HealthToolFragment mHealthToolFragment;
    private PersonalityFragment mPersonalityFragment;


    /**
     * 加载消息
     */
    private GetMessageNumberPresenter mGetMessageNumberPresenter=new GetMessageNumberPresenter();

    private boolean isActive;


    ///////////////// 记录登录用户的信息  /////////////////

    protected PhaApplication mPhaApplication;

    protected UserEntity mUserEntity;

    protected boolean isLogin;

    ///////////////////////////////////////////////////////

    /**
     * 是否已经检查过用户的状态
     */
    boolean checkUserStatus;


    private GetUserInfoPresenter mGetUserStatusPresenter;


    /**
     * Fragment显示容器
     */
    private FrameLayout fl_content;
    /**
     * “资讯”按钮
     */
    private RadioButton rbtn_health_information;
    /**
     * “资讯”红色圆点指示器
     */
    private TextView tv_health_information_indicator;
    /**
     * “资讯”布局容器
     */
    private RelativeLayout rl_health_information;


    /**
     * “问答”按钮
     */
    private RadioButton rbtn_health_ask;
    /**
     * “问答”圆点指示器
     */
    private TextView tv_health_ask_indicator;
    /**
     * “问答”布局容器
     */
    private RelativeLayout rl_health_ask;


    /**
     * “工具”按钮
     */
    private RadioButton rbtn_health_tool;
    /**
     * “工具”圆点指示器
     */
    private TextView tv_health_tool_indicator;
    /**
     * “工具”布局容器
     */
    private RelativeLayout rl_health_tool;


    /**
     * “个人中心”按钮
     */
    private RadioButton rbtn_personality;
    /**
     * “个人中心”圆点指示器
     */
    private TextView tv_personality_indicator;
    /**
     * “个人中心”布局容器
     */
    private RelativeLayout rl_personality;



    private static Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        initView();
        initFragment();
        initUserInfoFromSharedPreferences();
        hideIndecator();


        initHandler();

        //从服务器获取消息数量
        getMessageNumberFromServer();

        //轮询服务器消息
        startMessageService();

    }

    /**
     * 轮询服务器消息
     */
    private void startMessageService(){
        startService(new Intent(this, MessageService.class));
    }

    @Override
    protected void initActivity() {
        mContext = this;
        isActive = true;
        mPhaApplication = (PhaApplication) getApplication();
        setCouldDoubleBackExit(true);
    }

    @Override
    protected void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);

        rbtn_health_information = (RadioButton) findViewById(R.id.rbtn_health_information);
        tv_health_information_indicator = (TextView) findViewById(R.id.tv_health_information_indicator);
        rl_health_information = (RelativeLayout) findViewById(R.id.rl_health_information);
        rl_health_information.setOnClickListener(this);

        rbtn_health_ask = (RadioButton) findViewById(R.id.rbtn_health_ask);
        tv_health_ask_indicator = (TextView) findViewById(R.id.tv_health_ask_indicator);
        rl_health_ask = (RelativeLayout) findViewById(R.id.rl_health_ask);
        rl_health_ask.setOnClickListener(this);

        rbtn_health_tool = (RadioButton) findViewById(R.id.rbtn_health_tool);
        tv_health_tool_indicator = (TextView) findViewById(R.id.tv_health_tool_indicator);
        rl_health_tool = (RelativeLayout) findViewById(R.id.rl_health_tool);
        rl_health_tool.setOnClickListener(this);

        rbtn_personality = (RadioButton) findViewById(R.id.rbtn_personality);
        tv_personality_indicator = (TextView) findViewById(R.id.tv_personality_indicator);
        rl_personality = (RelativeLayout) findViewById(R.id.rl_personality);
        rl_personality.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkUserStatus) {
            //检查用户的状态，判断是否被锁定
            getUserStatus();
            checkUserStatus = true;
        }

        if(mPhaApplication!=null){
            showMessageNumber(mPhaApplication.getMessageNumber());
        }
    }

    /**
     * 隐藏指示器
     */
    private void hideIndecator(){
        tv_health_information_indicator.setVisibility(View.GONE);
        tv_health_ask_indicator.setVisibility(View.GONE);
        tv_health_tool_indicator.setVisibility(View.GONE);
        tv_personality_indicator.setVisibility(View.GONE);
    }

    /**
     * 如果已经登录，每次进入APP都要检查用户的状态
     * 返回status：0正常，1锁定
     */
    private void getUserStatus() {
        final UserEntity userEntity = mPhaApplication.getUserEntity();
        if (isLogin && userEntity != null) {
            mGetUserStatusPresenter = new GetUserInfoPresenter();
            mGetUserStatusPresenter.getUserInfo(userEntity, new UserContract.IGetUserInfoView() {
                @Override
                public void getUserInfoSuccess(UserEntity entity) {
                    switch (entity.getStatus()) {
                        case 0: //用户处于正常状态，就初始化用户信息
                            putStringToSharedPreferences(SharePreferencesKey
                                    .LOGIN_USER_INFO,entity.toJson());
                            initUserInfoFromSharedPreferences();
                            break;
                        case 1: //用户被锁定，强制退出登录
                            logout();
                            final MaterialDialog dialog = new MaterialDialog(MainActivity.this);
                            dialog.btnNum(1)
                                    .content("你的账号已被锁定，请联系客服")
                                    .showAnim(null)
                                    .btnText("确定")
                                    .show();
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            dialog.dismiss();
                                        }
                                    });
                            break;
                        default:

                            break;
                    }
                }

                @Override
                public void getUserInfoFail() {
                    showShortToast("获取用户状态失败:");
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
                    showShortToast(msg);
                }

                @Override
                public void showSuccessMessage(String title, String msg) {
                    showShortToast(msg);
                }

                @Override
                public void showErrorMessage(String title, String msg) {
                    showMessageDialog("showErrorMessage",msg);
                    //showShortToast(msg);
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

    }

    /**
     * 退出登录
     */
    private void logout() {
        if (mPhaApplication != null) {
            mPhaApplication.setUserEntity(null);
            mPhaApplication.setLogin(false);
        }
        putBooleanToSharedPreferences(SharePreferencesKey.IS_LOGIN, false);
        //发送一个广播给个人中心Fragment显示数据
        Intent intent = new Intent(PersonalityFragment.LOGIN_ACTION);
        sendBroadcast(intent);
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


    /**
     * 初始化要显示的Fragment
     */
    void initFragment() {
        mHealthInformationFragment = HealthInformationFragment.getINSTANCE();
        mHealthAskFragment = HealthQAFragment.getINSTANCE();
        mHealthToolFragment = HealthToolFragment.getINSTANCE();
        mPersonalityFragment = PersonalityFragment.getINSTANCE();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mHealthInformationFragment);
        mFragmentList.add(mHealthAskFragment);
        mFragmentList.add(mHealthToolFragment);
        mFragmentList.add(mPersonalityFragment);

        addAllFragment();
        hideAllFragment();
        showFragment(mHealthInformationFragment);
    }

    /**
     * 添加所有Fragment
     */
    private void addAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : mFragmentList) {
            ft.add(R.id.fl_content, fragment);
        }
        ft.commit();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : mFragmentList) {
            ft.hide(fragment);
        }
        ft.commit();
    }

    /**
     * 显示指定的Fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    /**
     * 从SharedPreferences中读取用户信息，并初始化
     */
    private void initUserInfoFromSharedPreferences() {
        //获取登录状态
        isLogin = getBooleanFromSharedPreferences(SharePreferencesKey.IS_LOGIN, false);
        mPhaApplication.setLogin(isLogin);
        if (!isLogin) {
            return;
        }
        //读取用户json字符串并解析
        String json = getStringFromSharedPreferences(SharePreferencesKey
                .LOGIN_USER_INFO, "");
        UserEntity mUserEntity = new UserEntity();
        mUserEntity = (UserEntity) mUserEntity.fromJson(json);
        mPhaApplication.setUserEntity(mUserEntity);
        //Log.d(TAG,"user:"+json);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_health_information:    //显示“资讯”Fragment
                hideAllFragment();
                showFragment(mHealthInformationFragment);
                changeRadioButtonState(true, false, false, false);
                break;
            case R.id.rl_health_ask:    //显示“问答”Fragment
                hideAllFragment();
                showFragment(mHealthAskFragment);
                changeRadioButtonState(false, true, false, false);
                //调用下拉刷新
                mHealthAskFragment.refreshing();
                break;
            case R.id.rl_health_tool:   //显示“工具”Fragment
                hideAllFragment();
                showFragment(mHealthToolFragment);
                changeRadioButtonState(false, false, true, false);
                break;
            case R.id.rl_personality:   //显示“个人中心”Fragment
                hideAllFragment();
                showFragment(mPersonalityFragment);
                changeRadioButtonState(false, false, false, true);
                break;
        }
    }





    /**
     * 从服务器获取消息数量
     */
    public void getMessageNumberFromServer(){
        UserEntity userEntity=mPhaApplication.getUserEntity();
        if(userEntity!=null){
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setReceiver(userEntity.getRecno());
            mGetMessageNumberPresenter.getMessageNumber(messageEntity, new MessageContract.IGetMessageNumberView() {
                @Override
                public void getMessageNumberSuccess(int number) {
                    if(mPhaApplication!=null){
                        mPhaApplication.setMessageNumber(number);
                    }
                    showMessageNumber(number);
                }

                @Override
                public void getMessageNumberFail(String msg) {
                    Log.d(TAG,msg);
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

    }

    /**
     * 显示消息数量
     */
    private void showMessageNumber(int number){
        if(number > 0){
            tv_personality_indicator.setVisibility(View.VISIBLE);
            tv_personality_indicator.setText(number+"");
            mPersonalityFragment.showMessageNumber(number);
        }else{
            tv_personality_indicator.setVisibility(View.GONE);
            mPersonalityFragment.showMessageNumber(number);
        }
    }


    /**
     * 改变单选按钮状态
     *
     * @param a
     * @param b
     * @param c
     * @param d
     */
    void changeRadioButtonState(boolean a, boolean b, boolean c, boolean d) {
        rbtn_health_information.setChecked(a);
        rbtn_health_ask.setChecked(b);
        rbtn_health_tool.setChecked(c);
        rbtn_personality.setChecked(d);
    }

    /**
     * 修改消息指示器
     */
    public final static int UPDATE_MESSAGE_INDICATOR=1000;


    /**
     * 轮询服务器消息Service
     */
    public static class MessageService extends Service{

        /**
         * 获取消息线程
         */
        private MessageThread messageThread = null;

        @Override
        public void onStart(Intent intent, int startId) {
            //开启线程
            messageThread = new MessageThread();
            messageThread.isRunning = true;
            messageThread.start();
            super.onStart(intent, startId);
        }

        /**
         * Return the communication channel to the service.  May return null if
         * clients can not bind to the service.  The returned
         * {@link IBinder} is usually for a complex interface
         * that has been <a href="{@docRoot}guide/components/aidl.html">described using
         * aidl</a>.
         * <p>
         * <p><em>Note that unlike other application components, calls on to the
         * IBinder interface returned here may not happen on the main thread
         * of the process</em>.  More information about the main thread can be found in
         * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
         * Threads</a>.</p>
         *
         * @param intent The Intent that was used to bind to this service,
         *               as given to {@link Context#bindService
         *               Context.bindService}.  Note that any extras that were included with
         *               the Intent at that point will <em>not</em> be seen here.
         * @return Return an IBinder through which clients can call on to the
         * service.
         */
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            //停止轮询
            messageThread.isRunning=false;
        }

        /**
         * 消息轮询线程
         */
        class MessageThread extends Thread{
            /**
             * 运行状态，下一步骤有大用
             */
            public boolean isRunning = true;

            public void run() {
                while(isRunning){
                    try {
                        //10秒钟轮询一次
                        Thread.sleep(10000);
                        mHandler.sendEmptyMessage(UPDATE_MESSAGE_INDICATOR);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 修改消息数
     */
    public static final int UPDATE_MESSAGE_NUMBER = 1000;

    /**
     * 初始化处理其它线程发来的消息
     */
    private void initHandler(){
        mHandler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    //修改通知指示器
                    case UPDATE_MESSAGE_NUMBER:
                        getMessageNumberFromServer();
                        break;
                    default:
                        break;
                }
            }
        };
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停止轮询服务器消息
        stopService(new Intent(MainActivity.this,MessageService.class));

        mFragmentList.clear();
        checkUserStatus = false;
        isActive = false;
    }
}
