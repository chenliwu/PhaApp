package com.clw.phaapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.clw.mysdk.utils.MD5Utils;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.LoginUserPresenter;

/**
 * MVP中的View层：负责UI具体实现展现。
 * 实现View层，并且通过泛型说明View层接口和Presenter类
 * 另外，View层要实现对应的View接口
 */
public class LoginActivity extends BaseMvpActivity<UserContract.ILoginView, LoginUserPresenter>
        implements UserContract.ILoginView, View.OnClickListener {

    private EditText edt_usercode;
    private EditText edt_pwd;
    private Button btn_login;
    private TextView tv_btn_register;
    private TextView tv_btn_find_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActivity();
        initView();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("用户登录");
    }

    @Override
    protected void initView() {
        edt_usercode = (EditText) findViewById(R.id.edt_usercode);
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tv_btn_register = (TextView) findViewById(R.id.tv_btn_register);
        tv_btn_register.setOnClickListener(this);
        tv_btn_find_password = (TextView) findViewById(R.id.tv_btn_find_password);
        tv_btn_find_password.setOnClickListener(this);
    }

    /**
     * 检查输入
     * @return
     */
    public boolean checkInput(){
        if(getUsercode().length() == 0){
            showShortToast("请输入账号");
            edt_usercode.setFocusable(true);
            return false;
        }
        if(getPwd().length() == 0){
            showShortToast("请输入密码");
            edt_pwd.setFocusable(true);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (checkInput()) {
                    UserEntity userEntity=new UserEntity();
                    userEntity.setUsercode(getUsercode());
                    //MD5加密
                    userEntity.setPwd(MD5Utils.md5(getPwd()));
                    mPresenter.login(userEntity);
                }
                break;
            case R.id.tv_btn_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_btn_find_password:
                startActivity(FindPwdActivity.class);
                break;
        }
    }


    @Override
    protected LoginUserPresenter initPresenter() {
        return new LoginUserPresenter();
    }

    /////////////  View层接口 ///////////////

    @Override
    public String getUsercode() {
        return edt_usercode.getText().toString();
    }

    @Override
    public String getPwd() {
        return edt_pwd.getText().toString();
    }

    @Override
    public void clearUsercode() {
        edt_usercode.setText("");
    }

    @Override
    public void clearPwd() {
        edt_pwd.setText("");
    }


    /**
     * 登录成功回调
     * @param userEntity
     */
    @Override
    public void loginSuccess(UserEntity userEntity) {
        if(mPhaApplication!=null && mPhaApplication.getUserEntity()==null){
            mPhaApplication.setUserEntity(userEntity);
            mPhaApplication.setLogin(true);
        }
        //保存登录信息到SharedPreferences
        putBooleanToSharedPreferences(SharePreferencesKey
                .IS_LOGIN,true);
        putStringToSharedPreferences(SharePreferencesKey
                .LOGIN_USER_INFO,userEntity.toJson());
        //发送一个广播给个人中心Fragment显示数据
        Intent intent=new Intent(PersonalityFragment.LOGIN_ACTION);
        sendBroadcast(intent);
        closeView();
    }

}