package com.clw.phaapp.ui.healthtool;

import android.os.Bundle;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.app.PhaApplication;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.common.LogPresenter;

/**
 * 常见疾病查询activity
 */
public class SearchIllnessActivity extends BaseSlidingAppComatActivity {

    private UserEntity mUserEntity;

    private PhaApplication mPhaApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_illness);
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
        logEntity.setType((byte)40);
        LogPresenter.getInstance().insertLogRecord(logEntity);
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("常见疾病");
    }

    @Override
    protected void initView() {

    }
}
