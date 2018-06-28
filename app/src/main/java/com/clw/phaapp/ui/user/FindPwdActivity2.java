package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.utils.MD5Utils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.FindPwdUserPresenter;

public class FindPwdActivity2 extends BaseMvpActivity<UserContract.IFindPwdView,FindPwdUserPresenter>
        implements UserContract.IFindPwdView,View.OnClickListener {

    private EditText edt_new_pwd;
    private EditText edt_conf_pwd;
    private Button btn_find_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd2);
        initActivity();
        initView();
        initDate();
    }

    /**
     * 初始化Activity
     */
    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("找回密码");
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        edt_new_pwd = (EditText) findViewById(R.id.edt_new_pwd);
        edt_conf_pwd = (EditText) findViewById(R.id.edt_conf_pwd);
        btn_find_pwd = (Button) findViewById(R.id.btn_find_pwd);
        btn_find_pwd.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initDate(){
        //读取数据
        mUserEntity=(UserEntity) getIntent().getSerializableExtra("user");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_pwd:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String new_pwd = getNewPwd();
        if (TextUtils.isEmpty(new_pwd)) {
            showShortToast("请输入新密码");
            edt_new_pwd.requestFocus();
            return;
        }
        if(new_pwd.length()< 6 || new_pwd.length()>20){
            showShortToast("密码长度超出限制");
            edt_new_pwd.requestFocus();
            return;
        }
        String conf_pwd = getConfPwd();
        if (TextUtils.isEmpty(conf_pwd)) {
            showShortToast("请输入确认密码");
            return;
        }
        if(conf_pwd.length()< 6 || conf_pwd.length()>20){
            showShortToast("密码长度超出限制");
            edt_conf_pwd.requestFocus();
            return;
        }
        // TODO validate success, do something
        if(new_pwd.equals(conf_pwd)){
            if(mUserEntity!=null){
                //MD5加密
                mUserEntity.setPwd(MD5Utils.md5(new_pwd));
                mPresenter.findPwd(mUserEntity);
            }
        }else{
            showShortToast("两次密码不一致");
        }
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected FindPwdUserPresenter initPresenter() {
        return new FindPwdUserPresenter();
    }

    @Override
    public String getNewPwd() {
        return edt_new_pwd.getText().toString().trim();
    }

    @Override
    public String getConfPwd() {
        return edt_conf_pwd.getText().toString().trim();
    }

    /**
     * 找回密码成功回调
     */
    @Override
    public void findPwdSuccess() {

    }
}
