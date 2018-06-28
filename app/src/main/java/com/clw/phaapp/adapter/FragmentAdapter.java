package com.clw.phaapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 对于FragmentPagerAdapter的派生类，只重写getItem(int)和getCount()就可以了。
 * 
 * @version 1.0.0
 * 
 * @date 2017-7-14 上午8:36:36
 *
 * @author chenliwu
 */
public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragmentList;

	private String[] mTitles;
	
	public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
		super(fm);
		mFragmentList=fragmentList;
		mTitles=titles;
	}
	
	
	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		//设置标题
		return mTitles[position];
	}
}
