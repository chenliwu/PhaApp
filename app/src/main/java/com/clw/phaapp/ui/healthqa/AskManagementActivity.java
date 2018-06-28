package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康问答管理
 */
public class AskManagementActivity extends BaseSlidingAppComatActivity implements View.OnClickListener{

    private TabLayout tablayout;
    private ViewPager viewpager;

    List<Fragment> mFragments;
    String[] mTitles = new String[]{"已审核", "待审核","待修改"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_manament);
        initActivity();
        initView();
        setupViewPager();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("健康问答管理");
    }

    @Override
    protected void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }


    @Override
    public void onClick(View v) {

    }

    private void setupViewPager() {
        mFragments=new ArrayList<Fragment>();
        mFragments.add(AskPassReviewFragment.getInstance());
        mFragments.add(AskWaitReviewFragment.getInstance());
        mFragments.add(AskWaitReviseFragment.getInstance());
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(fragmentAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }

}
