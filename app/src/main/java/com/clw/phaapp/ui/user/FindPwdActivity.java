package com.clw.phaapp.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.mysdk.rxjava.RxManager;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.model.IUserModel;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.model.impl.UserModelImpl;
import io.reactivex.functions.Consumer;

public class FindPwdActivity extends BaseSlidingAppComatActivity implements View.OnClickListener {

    private EditText edt_usercode;
    private Button btn_next;

    /**
     * RxJava管理器
     */
    protected RxManager mRxManager;

    /**
     * 用户业务接口，提供用户的业务方法调用
     */
    protected IUserModel mUserModel;


    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        initActivity();
        initView();
    }

    /**
     * 初始化Activity
     */
    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("找回密码");
        mRxManager=new RxManager();
        mUserModel=new UserModelImpl();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        edt_usercode = (EditText) findViewById(R.id.edt_usercode);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String usercode = edt_usercode.getText().toString().trim();
        if (TextUtils.isEmpty(usercode)) {
            showShortToast("请输入找回密码的账号");
            return;
        }
        // TODO validate success, do something
        final UserEntity userEntity=new UserEntity();
        userEntity.setUsercode(usercode);
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(mContext);
        }
        mProgressDialog.setMessage("正在处理");
        mProgressDialog.show();
        mRxManager.register(mUserModel.getUserInfo(userEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(final ResultEntity resultEntity) throws Exception {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        if(resultEntity.getState() == 200){
                            Intent intent=new Intent(mContext,FindPwdActivity1.class);
                            UserEntity entity= GsonUtils.parseJsonToObject(resultEntity.getData().toString(),
                                    UserEntity.class);
                            intent.putExtra("user",entity);
                            startActivity(intent);
                            finish();
                        }else{
                            showShortToast("账号不存在");
                        }
                    }
                },1000);
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(final Throwable throwable) throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        showShortToast(NetErrorUtils.getErrorMessage(throwable));
                    }
                });
            }
        }));
    }
}
