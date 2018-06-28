package com.clw.phaapp.ui.healthtool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.clw.mysdk.base.fragment.BaseFragment_SupportV4;
import com.clw.phaapp.R;

/**
 * 健康工具Fragment
 */
public class HealthToolFragment extends BaseFragment_SupportV4 implements View.OnClickListener {

    private static HealthToolFragment INSTANCE;
    private LinearLayout ll_search_illness;
    private LinearLayout ll_compute_weight_bml;

    /**
     * 获取实例对象
     *
     * @return
     */
    public static HealthToolFragment getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new HealthToolFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_health_tool, container, false);
        initFragment();
        initView();
        return mBaseView;
    }

    @Override
    protected void initFragment() {
        initToolbarView();
        setToolbarCenterTitle("健康工具");
    }

    @Override
    protected void initView() {

        ll_search_illness = (LinearLayout) mBaseView.findViewById(R.id.ll_search_illness);
        ll_search_illness.setOnClickListener(this);
        ll_compute_weight_bml = (LinearLayout) mBaseView.findViewById(R.id.ll_compute_weight_bml);
        ll_compute_weight_bml.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search_illness:
                startActivity(SearchIllnessActivity.class);
                break;
            case R.id.ll_compute_weight_bml:
                startActivity(ComputeWeightBmlActivity.class);
                break;
        }
    }
}
