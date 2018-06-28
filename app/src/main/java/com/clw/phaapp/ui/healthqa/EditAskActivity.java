package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.presenter.common.LogPresenter;
import com.clw.phaapp.presenter.healthqa.PublishAskPresenter;
import com.clw.phaapp.presenter.healthqa.ReviseAskPresenter;
import com.clw.phaapp.presenter.healthqa.SetAskVisitCountPresenter;

/**
 * 编辑健康问答
 */
public class EditAskActivity extends BaseMvpActivity<AskContract.IReviseAskView, ReviseAskPresenter>
        implements AskContract.IReviseAskView, View.OnClickListener {

    private EditText edt_title;
    private EditText edt_content;
    private Button btn_publish;

    /**
     * 记录要编辑的健康问答
     */
    private AskEntity mAskEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ask);
        initActivity();
        initView();
        initUserInfoFromApplication();
        initData();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("编辑健康问答");
    }

    @Override
    protected void initView() {

        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(this);
    }



    /**
     * 初始化数据
     */
    private void initData() {
        if (getIntent().getSerializableExtra("ask") != null) {
            mAskEntity = (AskEntity) getIntent().getSerializableExtra("ask");
            if(mAskEntity!=null){
                edt_title.setText(mAskEntity.getTitle());
                edt_content.setText(mAskEntity.getContent());
            }
        }
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
    protected ReviseAskPresenter initPresenter() {
        return new ReviseAskPresenter();
    }


    /**
     * 成功
     *
     * @param msg
     */
    @Override
    public void reviseAskSuccess(String msg) {
        showShortToast(msg);
        closeView();
    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void reviseAskFail(String msg) {
        showShortToast(msg);
    }

    private void submit() {
        // validate
        String title =edt_title.getText().toString().trim();
        title=title.replace(" ","");
        if (TextUtils.isEmpty(title)) {
            showShortToast("请输入标题");
            edt_title.requestFocus();
            return;
        }



        String content = edt_content.getText().toString().trim();
        content=content.replace(" ","");
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入问题的描述");
            edt_content.requestFocus();
            return;
        }

        //判断标题和内容 是否修改了
        if(title.equals(mAskEntity.getTitle()) && content.equals(mAskEntity.getContent())){
            showShortToast("标题和内容未做修改");
            return ;
        }

        // TODO validate success, do something
        if(mPhaApplication.isLogin() && mUserEntity!=null){
            showProgress("正在提交");
            AskEntity askEntity=new AskEntity();
            askEntity.setRecno(mAskEntity.getRecno());
            askEntity.setTitle(title);
            askEntity.setContent(content);
            askEntity.setUserrecno(mUserEntity.getRecno());
            //修改过后状态变成待审核
            askEntity.setStatus((byte)0);
            mPresenter.reviseAsk(askEntity);
        }
    }
}
