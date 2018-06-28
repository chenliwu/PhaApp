package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.clw.mysdk.utils.MD5Utils;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.RegisterUserPresenter;

public class RegisterActivity extends BaseMvpActivity<UserContract.IRegisterView, RegisterUserPresenter>
        implements UserContract.IRegisterView, View.OnClickListener {

    private EditText edt_usercode;
    private EditText edt_pwd;
    private EditText edt_conf_pwd;
    private Spinner spriner_security;
    private EditText edt_answer;
    private Button btn_register;

    /**
     * 注册用户信息
     */
    private UserEntity mRegisterUserEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initActivity();
        initView();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("注册账号");
    }

    @Override
    protected void initView() {
        edt_usercode = (EditText) findViewById(R.id.edt_usercode);
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        edt_conf_pwd = (EditText) findViewById(R.id.edt_conf_pwd);
        spriner_security = (Spinner) findViewById(R.id.spriner_security);
        edt_answer = (EditText) findViewById(R.id.edt_answer);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected RegisterUserPresenter initPresenter() {
        return new RegisterUserPresenter();
    }

    @Override
    public String getUsercode() {
        return edt_usercode.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return edt_pwd.getText().toString().trim();
    }

    @Override
    public String getSecurity() {
        return spriner_security.getSelectedItem().toString();
    }

    @Override
    public String getAnswer() {
        return edt_answer.getText().toString().trim();
    }


    /**
     * 注册成功回调方法
     */
    @Override
    public void registerSuccess() {
        showShortToast("注册成功");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String usercode = edt_usercode.getText().toString().trim();
        String usercode_reg="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (TextUtils.isEmpty(usercode)) {
            showShortToast("请输入账号");
            edt_usercode.requestFocus();
            return;
        }
        if(!usercode.matches(usercode_reg)){
            showShortToast("账号输入不合法");
            edt_usercode.requestFocus();
            return ;
        }

        String pwd = edt_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showShortToast("请输入登录密码");
            edt_pwd.requestFocus();
            return;
        }
        if(pwd.length()< 6 || pwd.length()>20){
            showShortToast("密码长度超出限制");
            edt_pwd.requestFocus();
            return;
        }

        String conf_pwd = edt_conf_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(conf_pwd)) {
            showShortToast("请输入确认密码");
            edt_conf_pwd.requestFocus();
            return;
        }
        if(conf_pwd.length()<6 || conf_pwd.length()>20){
            showShortToast("密码长度超出限制");
            edt_conf_pwd.requestFocus();
            return;
        }
        if(!conf_pwd.equals(pwd)){
            showShortToast("两次密码不一致");
            edt_pwd.requestFocus();
            return;
        }

        int index=spriner_security.getSelectedItemPosition();
        if(index == 0){
            showShortToast("请选择密保问题");
            return ;
        }
        String answer = edt_answer.getText().toString().trim();
        if (TextUtils.isEmpty(answer)) {
            showShortToast("请输入密保问题的答案");
            edt_answer.requestFocus();
            return;
        }
        if(answer.length() > 15){
            showShortToast("答案长度超出限制");
            edt_answer.requestFocus();
            return;
        }
        // TODO validate success, do something
        mRegisterUserEntity=new UserEntity();
        mRegisterUserEntity.setUsercode(getUsercode());
        mRegisterUserEntity.setNickname(getUsercode());
        mRegisterUserEntity.setPwd(MD5Utils.md5(getPwd()));
        mRegisterUserEntity.setSecurity(getSecurity());
        mRegisterUserEntity.setAnswer(getAnswer());
        //mRegisterUserEntity.setRegdate(Integer.parseInt(TimeUtils.getCurrentTime_4()));
        mPresenter.register(mRegisterUserEntity);
    }

}
