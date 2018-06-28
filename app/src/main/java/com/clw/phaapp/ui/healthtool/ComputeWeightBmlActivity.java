package com.clw.phaapp.ui.healthtool;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.common.LogPresenter;

import java.text.DecimalFormat;

/**
 * 计算体重BML指数activity
 */
public class ComputeWeightBmlActivity extends BaseSlidingAppComatActivity implements View.OnClickListener {

    private EditText edt_height;
    private EditText edt_weight;
    private Button btn_compute_weight_bml;
    private TextView txt_showWeightBml;
    private TextView txt_showWeightBmlHint;


    private UserEntity mUserEntity;

    private PhaApplication mPhaApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_weight_bml);
        initActivity();
        initView();
        insertLog();
    }

    /**
     * 插入日志
     *
     * type 访问类型：0-19资讯，20-39问答，40-59工具
     * 1 综合资讯，2 疾病资讯，3食品资讯
     * 20访问问答
     * 40使用查询常见疾病，41使用计算BML体重指数
     *
     */
    private void insertLog(){
        mPhaApplication=(PhaApplication)getApplication();
        if(mPhaApplication!=null){
            mUserEntity = mPhaApplication.getUserEntity();
        }
        //添加日志
        LogEntity logEntity=new LogEntity();
        if(mUserEntity!=null){
            //添加用户记录号
            logEntity.setUserrecno(mUserEntity.getRecno());
        }
        //设置访问类型
        logEntity.setType((byte)41);
        LogPresenter.getInstance().insertLogRecord(logEntity);
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("计算体重BML指数");
    }

    @Override
    protected void initView() {

        edt_height = (EditText) findViewById(R.id.edt_height);
        edt_height.setOnClickListener(this);
        edt_weight = (EditText) findViewById(R.id.edt_weight);
        edt_weight.setOnClickListener(this);
        btn_compute_weight_bml = (Button) findViewById(R.id.btn_compute_weight_bml);
        btn_compute_weight_bml.setOnClickListener(this);
        txt_showWeightBml = (TextView) findViewById(R.id.txt_showWeightBml);
        txt_showWeightBml.setOnClickListener(this);
        txt_showWeightBmlHint = (TextView) findViewById(R.id.txt_showWeightBmlHint);
        txt_showWeightBmlHint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_compute_weight_bml:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String height = edt_height.getText().toString().trim();
        if (TextUtils.isEmpty(height)) {
            showShortToast("请输入您的身高");
            edt_height.requestFocus();
            return;
        }

        String weight = edt_weight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {
            showShortToast("请输入您的体重");
            edt_weight.requestFocus();
            return;
        }
        // TODO validate success, do something
        //体质指数（BMI）=体重（kg）÷身高^2（m）
        double dHeight = Double.parseDouble(height);
        double dWeight = Double.parseDouble(weight);

        dHeight=dHeight/100.0;

        double dResult = dWeight / (dHeight * dHeight);
        showBml(dResult);
    }

    /**
     * 显示计算BML指数的结果
     *
     * @param dResult
     */
    public void showBml(double dResult) {
        txt_showWeightBml.setVisibility(View.VISIBLE);
        txt_showWeightBmlHint.setVisibility(View.VISIBLE);
        txt_showWeightBmlHint.setGravity(View.TEXT_ALIGNMENT_CENTER);

        //保留两位小数
        DecimalFormat df=new DecimalFormat(".##");
        String result=df.format(dResult);
        txt_showWeightBml.setText(result+"");
        if (dResult < 18.5) {
            txt_showWeightBml.setTextColor(Color.RED);
            txt_showWeightBmlHint.setText("您的体重过轻。");
        }else if(dResult <= 23.9 && dResult>=18.5){
            txt_showWeightBml.setTextColor(Color.GREEN);
            txt_showWeightBmlHint.setText("您的体重在正常范围内，请继续保持！");
        }else if(dResult <= 27.9 && dResult>=24){
            txt_showWeightBml.setTextColor(Color.RED);
            txt_showWeightBmlHint.setText("您的体重处于过重状态。");
        }else if(dResult <= 32 && dResult>=28){
            txt_showWeightBml.setTextColor(Color.RED);
            txt_showWeightBmlHint.setText("您的体重处于肥胖状态。");
        }else if(dResult > 32){
            txt_showWeightBml.setTextColor(Color.RED);
            txt_showWeightBmlHint.setText("您的体重处于非常肥胖状态，请注意锻炼。");
        }
    }

}
