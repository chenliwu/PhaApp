package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.PublishAskAnswerPresenter;

/**
 * 回答健康问答
 */
public class PublishAskAnswerActivity extends BaseMvpActivity<AskAnswerContract.IPublishAskAnswerView, PublishAskAnswerPresenter>
        implements AskAnswerContract.IPublishAskAnswerView, View.OnClickListener {
    private TextView txt_showAskTitle;
    private EditText edt_content;
    private Button btn_publish;

    /**
     * 记录健康问答信息
     */
    private AskEntity mAskEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_ask_answer);
        initActivity();
        initView();
        initUserInfoFromApplication();
        initData();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("回答问题");
    }

    @Override
    protected void initView() {
        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(this);
        txt_showAskTitle = (TextView) findViewById(R.id.txt_showAskTitle);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (getIntent().getSerializableExtra("ask") != null) {
            mAskEntity = (AskEntity) getIntent().getSerializableExtra("ask");
            if (mAskEntity != null) {
                txt_showAskTitle.setText(mAskEntity.getTitle());
            }
        }
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected PublishAskAnswerPresenter initPresenter() {
        return new PublishAskAnswerPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                publishAnswer();
                break;
        }
    }

    /**
     * 回答问题
     */
    private void publishAnswer(){
        String content = getAskAnswerContent();
        content = content.replace(" ","");
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入回答的内容");
            edt_content.requestFocus();
            return;
        }
        if(mAskEntity!=null && mUserEntity!=null){
            //回答问题需要的字段：问题记录号，用户记录号，回答内容
            AskAnswerEntity askAnswerEntity=new AskAnswerEntity();
            askAnswerEntity.setUserrecno(mUserEntity.getRecno());
            askAnswerEntity.setAskrecno(mAskEntity.getRecno());
            askAnswerEntity.setContent(content);
            mPresenter.publishAskAnswer(askAnswerEntity);
        }
    }



    /**
     * 回答的内容
     *
     * @return
     */
    @Override
    public String getAskAnswerContent() {
        return edt_content.getText().toString().trim();
    }

    /**
     * 成功回调
     */
    @Override
    public void publishAskAnswerSuccess() {
        closeView();
    }

    /**
     * 失败回调
     */
    @Override
    public void publishAskAnswerFail() {

    }
}
