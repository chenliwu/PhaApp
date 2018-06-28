package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.clw.phaapp.R;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.ReportContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.ReportEntity;
import com.clw.phaapp.presenter.common.ReportPresenter;

/**
 * 举报健康问答
 */
public class ReportAskActivity extends BaseMvpActivity<ReportContract.IReportView, ReportPresenter>
        implements ReportContract.IReportView, View.OnClickListener {

    private RadioButton rbtn_1;
    private RadioButton rbtn_2;
    private EditText edt_detail;
    private TextView txt_showAskTitle;
    private Button btn_publish;

    /**
     * 记录健康问答信息
     */
    private AskEntity mAskEntity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ask);
        initActivity();
        initView();
        initUserInfoFromApplication();
        initData();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("举报问答");
    }

    @Override
    protected void initView() {
        btn_publish = (Button) findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(this);
        txt_showAskTitle = (TextView) findViewById(R.id.txt_showAskTitle);
        rbtn_1 = (RadioButton) findViewById(R.id.rbtn_1);
        rbtn_1.setOnClickListener(this);
        rbtn_2 = (RadioButton) findViewById(R.id.rbtn_2);
        rbtn_2.setOnClickListener(this);
        edt_detail = (EditText) findViewById(R.id.edt_detail);
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
    protected ReportPresenter initPresenter() {
        return new ReportPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                report();
                break;
        }
    }

    /**
     * 举报需要提供的字段
     * 举报类型(0内容不实，1内容违规)，举报内容类型(0 问答)，举报目标记录号，用户记录号，举报描述
     */
    void report() {
        String detail = edt_detail.getText().toString().trim();
        if (TextUtils.isEmpty(detail)) {
            showShortToast("请输入举报理由");
            return;
        }
        if(mAskEntity!=null && mUserEntity!=null){
            ReportEntity reportEntity = new ReportEntity();
            if(rbtn_1.isChecked()){
                reportEntity.setType((byte)0);
            }
            if(rbtn_2.isChecked()){
                reportEntity.setType((byte)1);
            }
            reportEntity.setContenttype((byte) 0);
            reportEntity.setTargetrecno(mAskEntity.getRecno().toString());
            reportEntity.setUserrecno(mUserEntity.getRecno());
            reportEntity.setDetail(detail);
            mPresenter.report(reportEntity);
        }

    }


    /**
     * 举报成功
     *
     * @param msg
     */
    @Override
    public void reportSuccess(String msg) {
        showShortToast(msg);
        closeView();
    }

    /**
     * 举报失败
     *
     * @param msg
     */
    @Override
    public void reportFail(String msg) {
        showShortToast(msg);
    }
}
