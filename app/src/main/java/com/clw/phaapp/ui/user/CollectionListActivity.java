package com.clw.phaapp.ui.user;

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

public class CollectionListActivity extends BaseSlidingAppComatActivity implements View.OnClickListener{

    private TabLayout tablayout;
    private ViewPager viewpager;

    List<Fragment> mFragments;
    String[] mTitles = new String[]{"健康资讯", "健康问答"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        initActivity();
        initView();
        setupViewPager();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("收藏列表");
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
        mFragments.add(HealthInfoCollectionFragment.getInstance());
        mFragments.add(HealthAskCollectionFragment.getInstance());
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(fragmentAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }

}
