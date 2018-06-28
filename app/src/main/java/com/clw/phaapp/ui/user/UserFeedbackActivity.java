package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserFeedbackEntity;
import com.clw.phaapp.presenter.user.FeedbackUserPresenter;

public class UserFeedbackActivity extends BaseMvpActivity<UserContract.IFeedbackView, FeedbackUserPresenter>
        implements UserContract.IFeedbackView, View.OnClickListener {

    private RadioButton rbtn_bug;
    private RadioButton rbtn_suggestion;
    private EditText edt_contact;
    private EditText edt_content;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);
        initActivity();
        initView();
        initUserInfoFromApplication();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("用户反馈");
    }

    @Override
    protected void initView() {
        rbtn_bug = (RadioButton) findViewById(R.id.rbtn_bug);
        rbtn_bug.setOnClickListener(this);
        rbtn_suggestion = (RadioButton) findViewById(R.id.rbtn_suggestion);
        rbtn_suggestion.setOnClickListener(this);
        edt_contact = (EditText) findViewById(R.id.edt_contact);
        edt_contact.setOnClickListener(this);
        edt_content = (EditText) findViewById(R.id.edt_content);
        edt_content.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }


    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected FeedbackUserPresenter initPresenter() {
        return new FeedbackUserPresenter();
    }

    /**
     * 成功回调接口
     */
    @Override
    public void feedbackSuccess() {
        closeView();
    }

    /**
     * 失败回调接口
     */
    @Override
    public void feedbackFail() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String contact = edt_contact.getText().toString().trim().replace(" ","");
        if (TextUtils.isEmpty(contact)) {
            showShortToast("请输入联系方式");
            edt_contact.requestFocus();
            return;
        }

        String content = edt_content.getText().toString().trim().replace(" ","");
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入反馈内容");
            edt_content.requestFocus();
            return;
        }
        // TODO validate success, do something
        UserFeedbackEntity userFeedbackEntity=new UserFeedbackEntity();
        byte type=0;
        if(rbtn_suggestion.isChecked()){
            type=1;
        }
        if(mUserEntity!=null){
            userFeedbackEntity.setUserrecno(String.valueOf(mUserEntity.getRecno()));
        }
        userFeedbackEntity.setType(type);
        userFeedbackEntity.setContact(contact);
        userFeedbackEntity.setContent(content);
        mPresenter.feedback(userFeedbackEntity);
    }
}
