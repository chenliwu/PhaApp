package com.clw.phaapp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import cn.finalteam.galleryfinal.*;
import cn.finalteam.galleryfinal.widget.GFImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.clw.phaapp.model.entity.UserEntity;

/**
 * 应用信息全局类
 */
public class PhaApplication extends Application {
    private static final String LOG_TAG = "YZ_LOGGER";

    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;

    /**
     * 记录登录用户的信息
     */
    protected UserEntity userEntity;

    /**
     * 用户是否登录
     */
    protected boolean isLogin;

    /**
     * 该用户的消息数量
     */
    protected int messageNumber;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

        initGallerFinal();
    }

    /**
     * 初始化图片选择器
     */
    private void initGallerFinal(){
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(com.clw.phaapp.R.color.colorPrimary)    //标题栏背景颜色
                .setTitleBarTextColor(com.clw.phaapp.R.color.white)//标题栏文本字体颜色
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(Color.RED)   //设置Floating按钮Nornal状态颜色
                .setFabPressedColor(Color.BLUE) //设置Floating按钮Pressed状态颜色
                .setCheckNornalColor(Color.WHITE)   //选择框未选颜色
                .setCheckSelectedColor(com.clw.phaapp.R.color.colorPrimary) //选择框选中颜色
                .setIconBack(com.clw.phaapp.R.drawable.ic_arrow_back_white_24dp)
                //.setIconRotate(R.mipmap.ic_action_repeat)
                //.setIconCrop(R.mipmap.ic_action_crop)
                //.setIconCamera(R.mipmap.ic_action_camera)//...其他配置
                .build();

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)  //开启相机功能
                .setEnableEdit(true)    //开启编辑功能
                .setEnableCrop(true)    //开启裁剪功能
                .setEnableRotate(true)  //开启旋转功能
                .setCropSquare(true)    //裁剪正方形
                .setEnablePreview(true) //是否开启预览功能
                .build();
        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            Glide.with(activity)
                    .load("file://" + path)
                    .placeholder(defaultDrawable)
                    .error(defaultDrawable)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                    .skipMemoryCache(true)
                    .into(new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            imageView.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            //imageView.setTag(R.id.adapter_item_tag_key,request);
                        }

                        @Override
                        public Request getRequest() {
                            //return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                            return null;
                        }
                    });
        }

        @Override
        public void clearMemoryCache() {
        }
    }

}
