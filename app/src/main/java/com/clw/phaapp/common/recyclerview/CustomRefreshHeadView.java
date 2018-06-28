package com.clw.phaapp.common.recyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.clw.phaapp.R;

/**
 * 自定义布局下拉刷新头部
 */
public class CustomRefreshHeadView extends RelativeLayout implements SwipeRefreshTrigger,SwipeTrigger {

    /**
     * 刷新显示的文本
     */
    private TextView mDescText;

    /**
     * 刷新显示的图片
     */
    private ImageView mImageView;

    /**
     * 动画对象
     */
    private ObjectAnimator anim;

    /**
     * 是否释放刷新
     */
    private boolean isRelease;

    public CustomRefreshHeadView(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 自定义下拉刷新头部，在该构造方法中初始化布局
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomRefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        //通过布局文件来初始化布局,性能较差，达不到预期效果
        //View view= LayoutInflater.from(context).inflate(R.layout.custom_refresh_header,null);
        //mImageView=view.findViewById(R.id.image_refresh_header);
        //mDescText=view.findViewById(R.id.textview_refresh_header);
        //addView(view);
    }

    /**
     * 通过代码动态初始化布局，性能较好，能达到预期效果
     */
    private void initView(Context context) {
        //初始化线性布局
        LinearLayout ll = new LinearLayout(context);
        ll.setGravity(Gravity.CENTER);
        ll.setPadding(10,10,10,10);
        ViewGroup.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);
        //ll.setBackgroundResource(R.color.gray);
        //初始化显示文本
        mDescText = new TextView(context);
        mDescText.setTextSize(16);
        mDescText.setTextColor(Color.GRAY);
        mDescText.setText("下拉刷新");
        //初始化显示图片
        mImageView= new ImageView(context);
        mImageView.setImageResource(R.drawable.ic_refreshing_green_500_36dp);
        //添加控件到线性布局
        ll.addView(mImageView);
        ll.addView(mDescText);
        addView(ll);
    }

    /**
     * 下拉到一定位置之后调用此方法
     */
    @Override
    public void onRefresh() {
        //开始刷新，启动旋转动画
        anim = ObjectAnimator.ofFloat(mImageView, "rotation", mImageView.getRotation(), mImageView.getRotation()+360f)
                .setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.start();
        mDescText.setText("正在刷新数据");
    }

    /**
     * 下拉之前调用此方法
     */
    @Override
    public void onPrepare() {
        mImageView.setImageResource(R.drawable.ic_refreshing_green_500_36dp);
        isRelease = false;
    }

    /**
     * 当下拉到一定位置之后 显示刷新
     * @param yScroll
     * @param isComplete
     * @param b1
     */
    @Override
    public void onMove(int yScroll, boolean isComplete, boolean b1) {
        if (!isComplete) {
            if (yScroll < getHeight()) {//Y轴与下拉控件
                mDescText.setText("下拉刷新");
            } else {
                mDescText.setText("松开刷新");
            }
        }
    }

    /**
     * 上拉到一定距离之后松开刷新
     */
    @Override
    public void onRelease() {
        isRelease = true;
    }

    /**
     * 刷新完成后
     */
    @Override
    public void onComplete() {
        anim.cancel();
        //mImageView.setImageResource(R.drawable.ic_refreshed_green_500_36dp);
        mDescText.setText("刷新完毕");
    }

    /**
     * 重置
     */
    @Override
    public void onReset() {
        mImageView.setImageResource(R.drawable.ic_refreshing_green_500_36dp);
    }
}
