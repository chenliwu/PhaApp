package com.clw.phaapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.model.entity.UserEntity;

public class FindPwdActivity1 extends BaseSlidingAppComatActivity implements View.OnClickListener {

    private final static String TAG="FindPwdActivity1";

    private EditText edt_security;
    private EditText edt_answer;
    private Button btn_next;

    /**
     * 记录用户信息
     */
    private UserEntity mUserEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd1);
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
        edt_security = (EditText) findViewById(R.id.edt_security);
        edt_answer = (EditText) findViewById(R.id.edt_answer);
        edt_answer.setOnClickListener(this);
        edt_answer.setFocusable(true);
        edt_answer.requestFocus();

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    private void initDate(){
        mUserEntity=(UserEntity) getIntent().getSerializableExtra("user");
        if(mUserEntity!=null){
            edt_security.setText(mUserEntity.getSecurity());
        }
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
        String answer = edt_answer.getText().toString().trim();
        if (TextUtils.isEmpty(answer)) {
            showShortToast("请输入密保答案");
            return;
        }
        // TODO validate success, do something
        if(answer.equals(mUserEntity.getAnswer())){
            Intent intent=new Intent(mContext,FindPwdActivity2.class);
            intent.putExtra("user",mUserEntity);
            startActivity(intent);
            finish();
        }else{
            showShortToast("答案不正确");
        }

    }
}
