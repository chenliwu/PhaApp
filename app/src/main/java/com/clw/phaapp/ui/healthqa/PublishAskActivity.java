package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.utils.StringUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.PublishAskPresenter;

/**
 * 发起健康问答
 */
public class PublishAskActivity extends BaseMvpActivity<AskContract.IPublishView, PublishAskPresenter>
        implements AskContract.IPublishView, View.OnClickListener {

    private EditText edt_title;
    private EditText edt_content;
    private Button btn_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_ask);
        initActivity();
        initView();
        initUserInfoFromApplication();
//        if(mUserEntity!=null){
//            showShortToast(mUserEntity.toString());
//        }else{
//            showShortToast("mUserEntity == null");
//        }
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("发起健康问答");
    }

    @Override
    protected void initView() {

        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
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
    protected PublishAskPresenter initPresenter() {
        return new PublishAskPresenter();
    }


    /**
     * 获取标题
     *
     * @return
     */
    @Override
    public String getAskTitle() {
        return edt_title.getText().toString().trim();
    }

    /**
     * 获取问题内容
     *
     * @return
     */
    @Override
    public String getAskContent() {
        return edt_content.getText().toString().trim();
    }

    /**
     * 发起问答成功
     */
    @Override
    public void publishSuccess() {

    }

    /**
     * 发起问答失败
     */
    @Override
    public void publishFail() {

    }

    private void submit() {
        // validate
        String title =getAskTitle();
        title=title.replace(" ","");
        if (TextUtils.isEmpty(title)) {
            showShortToast("请输入标题");
            edt_title.requestFocus();
            return;
        }

        String content = getAskContent();
        content=content.replace(" ","");
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入问题的描述");
            edt_content.requestFocus();
            return;
        }
        // TODO validate success, do something
        if(mPhaApplication.isLogin() && mUserEntity!=null){
            AskEntity askEntity=new AskEntity();
            askEntity.setTitle(title);
            askEntity.setContent(content);
            askEntity.setUserrecno(mUserEntity.getRecno());
            mPresenter.publishAsk(askEntity);
        }else{
            showShortToast("请登录后再发起问答");
        }

    }
}
