package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.utils.MD5Utils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.RevisePwdUserPresenter;

public class RevisePwdActivity extends BaseMvpActivity<UserContract.IRevisePwdView,RevisePwdUserPresenter>
        implements UserContract.IRevisePwdView,View.OnClickListener  {

    private EditText edt_old_pwd;
    private EditText edt_new_pwd;
    private EditText edt_conf_pwd;
    private Button btn_revise_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_pwd);
        initActivity();
        initView();
        initUserInfoFromApplication();
    }



    /**
     * 初始化Activity
     */
    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("修改密码");
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {

        edt_old_pwd = (EditText) findViewById(R.id.edt_old_pwd);
        edt_old_pwd.setOnClickListener(this);
        edt_new_pwd = (EditText) findViewById(R.id.edt_new_pwd);
        edt_new_pwd.setOnClickListener(this);
        edt_conf_pwd = (EditText) findViewById(R.id.edt_conf_pwd);
        edt_conf_pwd.setOnClickListener(this);
        btn_revise_pwd = (Button) findViewById(R.id.btn_revise_pwd);
        btn_revise_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_revise_pwd:
                submit();
                break;
        }
    }


    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected RevisePwdUserPresenter initPresenter() {
        return new RevisePwdUserPresenter();
    }

    @Override
    public String getOldPwd() {
        return edt_old_pwd.getText().toString().trim();
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
     * 修改密码成功回调
     */
    @Override
    public void revisePwdSuccess() {
        mUserEntity.setPwd(MD5Utils.md5(getNewPwd()));
        saveUserinfoToSharedPreferences();
        closeView();
    }

    /**
     * 保存用户信息
     */
    private void saveUserinfoToSharedPreferences() {
        if(mUserEntity!=null){
            putStringToSharedPreferences(SharePreferencesKey
                    .LOGIN_USER_INFO,mUserEntity.toJson());
        }
    }

    private void submit() {
        // validate
        String old_pwd = edt_old_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(old_pwd)) {
            showShortToast("请输入原始密码");
            edt_old_pwd.requestFocus();
            return;
        }
        if(mUserEntity== null || !MD5Utils.md5(old_pwd).equals(mUserEntity.getPwd())){
            showShortToast("原始密码不正确");
            edt_old_pwd.requestFocus();
            return ;
        }

        String new_pwd = edt_new_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(new_pwd)) {
            showShortToast("请输入新密码");
            edt_new_pwd.requestFocus();
            return;
        }

        String conf_pwd = edt_conf_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(conf_pwd)) {
            showShortToast("请输入确认密码");
            edt_conf_pwd.requestFocus();
            return;
        }

        if(!new_pwd.equals(conf_pwd)){
            showShortToast("输入的新密码不一致");
            return ;
        }

        if(MD5Utils.md5(new_pwd).equals(mUserEntity.getPwd())){
            showShortToast("新密码不能和原始密码相同");
            return ;
        }

        // TODO validate success, do something
        if(mUserEntity!=null){
            UserEntity userEntity=new UserEntity();
            userEntity.setRecno(mUserEntity.getRecno());
            userEntity.setPwd(MD5Utils.md5(new_pwd));
            mPresenter.revisePwd(userEntity);
        }
    }
}
