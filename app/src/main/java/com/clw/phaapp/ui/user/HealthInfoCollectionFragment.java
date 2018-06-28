package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.AskCollectionAdapter;
import com.clw.phaapp.adapter.AskPassReviewAdapter;
import com.clw.phaapp.adapter.HealthInfoAdapter;
import com.clw.phaapp.adapter.HealthInfoCollectionAdapter;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.healthinfo.GetHealthInfoCollectionListPresenter;
import com.clw.phaapp.presenter.healthqa.GetAskCollectionListPresenter;

import java.util.*;

/**
 * 健康问答收藏列表
 */
public class HealthInfoCollectionFragment extends
		BaseMvpFragmentV4<UserCollectionContract.IGetHealthInfoCollectionView, GetHealthInfoCollectionListPresenter>
		implements UserCollectionContract.IGetHealthInfoCollectionView, OnRefreshListener, OnLoadMoreListener {

	private static HealthInfoCollectionFragment FRAGMENT1;

	/**
	 * 收藏列表
	 */
	private List<UserCollectionEntity> mCollectionList = new ArrayList<>();

	/**
	 * 资讯列表
	 */
	private List<HealthInfoEntity> mDataList = new ArrayList<>();


	/**
	 * 记录当前页数
	 */
	int currentPage = 0;

	/**
	 * 加载数据标识
	 * 0 第一次加载数据
	 * 1 加载第一次数据完毕
	 * 2 其它
	 */
	int loadDataStatus = 0;
	private RecyclerView swipe_target;
	private SwipeToLoadLayout swipeToLoad;


	private HealthInfoCollectionAdapter mAdapter;


	/**
	 * 获取对象实例
	 *
	 * @return
	 */
	public static HealthInfoCollectionFragment getInstance() {
		if (FRAGMENT1 == null) {
			FRAGMENT1 = new HealthInfoCollectionFragment();
		}
		return FRAGMENT1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mBaseView = inflater.inflate(R.layout.fragment_health_ask_collection, container, false);
		initFragment();
		initView();
		initUserInfoFromApplication();
		initRecyclerview();
		return mBaseView;
	}

	@Override
	protected void initFragment() {

	}

	@Override
	protected void initView() {
		swipe_target = mBaseView.findViewById(R.id.swipe_target);
		swipeToLoad = mBaseView.findViewById(R.id.swipeToLoad);
	}



	/**
	 * 初始化Recyclerview
	 */
	private void initRecyclerview() {
		//监听下拉刷新
		swipeToLoad.setOnRefreshListener(this);
		//监听上拉加载更多
		swipeToLoad.setOnLoadMoreListener(this);
		//创建默认的线性LayoutManager
		swipe_target.setLayoutManager(new LinearLayoutManager(mContext));
		//设置Item增加、移除动画
		swipe_target.setItemAnimator(new DefaultItemAnimator());
		swipe_target.addItemDecoration(new DividerItemDecoration(mContext,
				DividerItemDecoration.VERTICAL));
		//创建并设置Adapter
		mAdapter = new HealthInfoCollectionAdapter(mCollectionList,mDataList,this,mContext);
		//设置item的点击事件
		mAdapter.setItemClickListener(new HealthInfoCollectionAdapter.MyItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

			}
		});
		//设置长按事件
		mAdapter.setOnItemLongClickListener(new HealthInfoCollectionAdapter.MyItemLongClickListener() {
			@Override
			public void onItemLongClick(final View view, final int position) {

			}
		});
		swipe_target.setAdapter(mAdapter);
	}




	@Override
	public void onResume() {
		super.onResume();
		if (loadDataStatus == 0) {
			swipeToLoad.setRefreshing(true);
		}
	}

	/**
	 * 实例化Presenter，注意是实现泛型指定的Presenter
	 *
	 * @return
	 */
	@Override
	protected GetHealthInfoCollectionListPresenter initPresenter() {
		return new GetHealthInfoCollectionListPresenter();
	}

	/**
	 * 上拉加载更多
	 */
	@Override
	public void onLoadMore() {
		if (!checkNetworkState()) {
			showShortToast(CommonHintInfo.NO_NETWORDK);
			return;
		}
		if (mUserEntity != null) {
			//添加查询收藏列表的参数
			UserCollectionEntity userCollectionEntity = new UserCollectionEntity();
			//type = 0 查询资讯
			userCollectionEntity.setType((byte) 0);
			//添加查询的用户收藏列表
			userCollectionEntity.setUserrecno(mUserEntity.getRecno());
			//只查询第一页
			userCollectionEntity.setPage(currentPage+1);
			mPresenter.refreshData(userCollectionEntity);
		}
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		if (!checkNetworkState()) {
			showShortToast(CommonHintInfo.NO_NETWORDK);
			return;
		}
		if (mUserEntity != null) {
			//添加查询收藏列表的参数
			UserCollectionEntity userCollectionEntity = new UserCollectionEntity();
			//type = 0 查询资讯
			userCollectionEntity.setType((byte) 0);
			//添加查询的用户收藏列表
			userCollectionEntity.setUserrecno(mUserEntity.getRecno());
			//只查询第一页
			userCollectionEntity.setPage(1);
			mPresenter.refreshData(userCollectionEntity);
		}
	}


	/**
	 * 下拉刷新数据成功回调
	 *
	 * @param userCollectionEntity
	 */
	@Override
	public void refreshDataSuccess(UserCollectionEntity userCollectionEntity) {
		dismissLoading();

		mCollectionList.clear();
		mDataList.clear();

		List collectionList=userCollectionEntity.getRows();
		if(collectionList!=null && collectionList.size()>0){
			currentPage = userCollectionEntity.getPage();
			mCollectionList.addAll(collectionList);

			mAdapter.notifyDataSetChanged();
			if(loadDataStatus == 0){
				loadDataStatus = 1;
			}
		}else{
			if(loadDataStatus != 0){
				showShortToast("暂无更新");
			}else{//第一次刷新数据
				loadDataStatus = 1;
			}
		}


	}



	/**
	 * 上拉加载更多成功回调
	 *
	 * @param userCollectionEntity
	 */
	@Override
	public void loadMoreDataSuccess(UserCollectionEntity userCollectionEntity) {
		dismissLoading();
		List collectionList=userCollectionEntity.getRows();
		if(collectionList!=null && collectionList.size()>0){
			currentPage = userCollectionEntity.getPage();
			mCollectionList.addAll(collectionList);
			mAdapter.notifyDataSetChanged();
		}else{
			showShortToast("已无更多数据");
		}
	}

	/**
	 * 失败回调
	 *
	 * @param msg
	 */
	@Override
	public void getHealthInfoCollectionFail(String msg) {
		dismissLoading();
		showShortToast(msg);
	}

	/**
	 * 关闭加载提示
	 */
	@Override
	public void dismissLoading() {
		super.dismissLoading();
		//停止上拉加载更多
		swipeToLoad.setLoadingMore(false);
		//停止下拉刷新
		swipeToLoad.setRefreshing(false);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		currentPage = 0;
		loadDataStatus = 0;
		mCollectionList.clear();
	}

}
