package com.clw.phaapp.ui.healthinfo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.clw.mysdk.base.fragment.BaseFragment_SupportV4;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康资讯Fragment
 */
public class HealthInformationFragment extends BaseFragment_SupportV4{

    private static HealthInformationFragment INSTANCE;


    List<Fragment> mFragments;
    String[] mTitles = new String[]{"综合资讯", "疾病资讯", "食品资讯"};
    private TabLayout tablayout;
    private ViewPager viewpager;



    /**
     * 获取实例对象
     *
     * @return
     */
    public static HealthInformationFragment getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new HealthInformationFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_health_information, container, false);
        initFragment();
        initView();
        setupViewPager();
        return mBaseView;
    }

    @Override
    protected void initFragment() {
        //initSearchToolbar();
//        setToolbarNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showShortToast("搜索事件");
//            }
//        });
        initToolbarView();
        setToolbarCenterTitle("健康资讯");
    }

    @Override
    protected void initView() {
        tablayout = (TabLayout) mBaseView.findViewById(R.id.tablayout);
        viewpager = (ViewPager) mBaseView.findViewById(R.id.viewpager);
    }

    /**
     * 设置Fragment
     */
    private void setupViewPager() {
        mFragments=new ArrayList<Fragment>();
        mFragments.add(GlobalHealthInfoFragment.getInstance());
        mFragments.add(IllnessHealthInfoFragment.getInstance());
        mFragments.add(FoodHealthInfoFragment.getInstance());
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),mFragments,mTitles);
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(fragmentAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }



}
