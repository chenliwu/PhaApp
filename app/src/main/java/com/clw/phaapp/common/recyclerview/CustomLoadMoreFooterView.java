package com.clw.phaapp.common.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * 自定义布局，上拉加载更多尾部
 */
public class CustomLoadMoreFooterView extends RelativeLayout implements SwipeTrigger, SwipeLoadMoreTrigger {


    /**
     * 刷新显示的文本
     */
    private TextView mDescText;

    /**
     * 进度条
     */
    private ProgressBar mProgressBar;


    public CustomLoadMoreFooterView(Context context) {
        super(context);
    }

    /**
     * 自定义上拉加载更多，在该构造方法中初始化布局
     * @param context
     * @param attrs
     */
    public CustomLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        //通过布局文件方式来定义尾部布局，性能较差，达不到预期效果
        //View view= LayoutInflater.from(context).inflate(R.layout.custom_load_more_footer,null);
        //mDescText=view.findViewById(R.id.textview_load_more_footer);
        //mProgressBar=view.findViewById(R.id.progress_load_more_footer);
        //addView(view);
    }

    public CustomLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 通过代码动态生成尾部布局，性能较好，能达到预期效果
     */
    private void initView(Context context){
        //初始化线性布局
        LinearLayout ll = new LinearLayout(getContext());
        ll.setGravity(Gravity.CENTER);
        ll.setPadding(10,10,10,10);
        ViewGroup.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);
        //ll.setBackgroundResource(R.color.gray);
        //初始化显示文本
        mDescText = new TextView(getContext());
        mDescText.setTextSize(16);
        mDescText.setTextColor(Color.GRAY);
        mDescText.setText("上拉加载更多");
        mProgressBar=new ProgressBar(context,null, android.R.attr.progressBarStyleSmall);
        //添加控件到线性布局
        ll.addView(mDescText);
        ll.addView(mProgressBar);
        addView(ll);
    }

    /**
     * 上拉到一定位置之后调用此方法
     */
    @Override
    public void onLoadMore() {
        mDescText.setText("正在加载更多");
        //显示进度条
        mProgressBar.setVisibility(VISIBLE);
    }

    /**
     * 上拉之前调用此方法
     */
    @Override
    public void onPrepare() {
        //mDescText.setText("");
    }

    /**
     * 当上拉到一定位置之后 显示刷新
     * @param yScrolled
     * @param isComplete
     * @param automatic
     */
    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            //隐藏进度条
            mProgressBar.setVisibility(INVISIBLE);
            if (yScrolled <= -getHeight()) {
                mDescText.setText("松开加载更多");
            } else {
                mDescText.setText("上拉加载更多");
            }
        } else {
            mDescText.setText("正在加载更多");
            //显示进度条
            mProgressBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 上拉到一定距离之后松开刷新
     */
    @Override
    public void onRelease() {
        //mDescText.setText("正在加载更多");
    }

    /**
     * 加载完成后
     */
    @Override
    public void onComplete() {
        //隐藏进度条
        mProgressBar.setVisibility(INVISIBLE);
        mDescText.setText("加载完成");
    }

    /**
     * 重置
     */
    @Override
    public void onReset() {
        //mDescText.setText("");
    }
}
