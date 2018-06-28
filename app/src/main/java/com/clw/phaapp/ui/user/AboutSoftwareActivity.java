package com.clw.phaapp.ui.user;

import android.os.Bundle;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.R;

public class AboutSoftwareActivity extends BaseSlidingAppComatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_software);
        initActivity();
        initView();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("关于软件");
    }

    @Override
    protected void initView() {

    }
}
