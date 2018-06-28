package com.clw.phaapp.ui.user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import com.bumptech.glide.Glide;
import com.clw.mysdk.utils.FileUtils;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.user.UpdateUserPresenter;
import com.clw.phaapp.presenter.user.UploadImgPresenter;
import com.clw.phaapp.utils.OkHttpClientManager;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ShowUserinfoActivity extends BaseMvpActivity<UserContract.IUpdateView,UpdateUserPresenter>
        implements UserContract.IUpdateView,View.OnClickListener {

    private CircleImageView civ_head;
    private RelativeLayout rl_reviseHeader;
    private TextView txt_showNickname;
    private RelativeLayout rl_reviseNickname;
    private TextView txt_showName;
    private RelativeLayout rl_reviseName;
    private TextView txt_showSex;
    private RelativeLayout rl_reviseSex;
    private TextView txt_showBirthday;
    private RelativeLayout rl_reviseBirthday;
    //private TextView txt_showTel;
    //private RelativeLayout rl_reviseTel;
    private TextView txt_showScore;
    private RelativeLayout rl_showScore;
    private Button btn_logout;
    private LinearLayout ll_revisePwd;

    /**
     * 上传头像Presenter
     */
    private UploadImgPresenter mUploadImgPresenter=new UploadImgPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_userinfo);
        initActivity();
        initView();
        initUserInfoFromApplication();
        showUserinfo();
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected UpdateUserPresenter initPresenter() {
        return new UpdateUserPresenter();
    }

    /**
     * 初始化Activity
     */
    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("个人信息");
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        civ_head = (CircleImageView) findViewById(R.id.civ_head);
        rl_reviseHeader = (RelativeLayout) findViewById(R.id.rl_reviseHeader);
        rl_reviseHeader.setOnClickListener(this);

        txt_showNickname = (TextView) findViewById(R.id.txt_showNickname);
        rl_reviseNickname = (RelativeLayout) findViewById(R.id.rl_reviseNickname);
        rl_reviseNickname.setOnClickListener(this);

        txt_showName = (TextView) findViewById(R.id.txt_showName);
        rl_reviseName = (RelativeLayout) findViewById(R.id.rl_reviseName);
        rl_reviseName.setOnClickListener(this);

        txt_showSex = (TextView) findViewById(R.id.txt_showSex);
        rl_reviseSex = (RelativeLayout) findViewById(R.id.rl_reviseSex);
        rl_reviseSex.setOnClickListener(this);

        txt_showBirthday = (TextView) findViewById(R.id.txt_showBirthday);
        rl_reviseBirthday = (RelativeLayout) findViewById(R.id.rl_reviseBirthday);
        rl_reviseBirthday.setOnClickListener(this);

        //txt_showTel = (TextView) findViewById(R.id.txt_showTel);
        //rl_reviseTel = (RelativeLayout) findViewById(R.id.rl_reviseTel);
        //rl_reviseTel.setOnClickListener(this);

        txt_showScore = (TextView) findViewById(R.id.txt_showScore);
        rl_showScore = (RelativeLayout) findViewById(R.id.rl_showScore);
        rl_showScore.setOnClickListener(this);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        ll_revisePwd = (LinearLayout) findViewById(R.id.ll_revisePwd);
        ll_revisePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_reviseHeader:  //修改头像
                reviseHeaderImg();
                break;
            case R.id.rl_reviseNickname:    //修改昵称
                reviseNickname();
                break;
            case R.id.rl_reviseName:    //修改姓名
                reviseName();
                break;
            case R.id.rl_reviseSex: //修改性别
                reviseSex();
                break;
            case R.id.rl_reviseBirthday:
                reviseBirthday();
                break;
            case R.id.ll_revisePwd: //修改密码
                startActivity(RevisePwdActivity.class);
                break;
            case R.id.btn_logout:   //退出账号
                logout();
                break;

        }
    }

    /**
     * 修改个人信息成功回调方法
     */
    @Override
    public void updateSuccess() {
        if(mUserEntity!=null && strReviseNickname!=null){
            mUserEntity.setNickname(strReviseNickname);
        }
        if(mUserEntity!=null && strReviseName!=null){
            mUserEntity.setName(strReviseName);
        }
        if(mUserEntity!=null && strReviseSex!=null){
            mUserEntity.setSex(strReviseSex);
        }
        if(mUserEntity!=null && nBirthday!=null){
            mUserEntity.setBirthday(nBirthday);
        }
        saveUserinfoToSharedPreferences();
        showUserinfo();
    }

    /**
     * 失败回调接口
     */
    @Override
    public void updateFail() {
        showUserinfo();
    }

    /**
     * 保存用户信息
     */
    private void saveUserinfoToSharedPreferences() {
        if(mUserEntity!=null){
            putStringToSharedPreferences(SharePreferencesKey
                    .LOGIN_USER_INFO,mUserEntity.toJson());
        }
    }

    /**
     * 显示用户信息
     */
    private void showUserinfo() {
        if (mUserEntity != null) {
            String path=mUserEntity.getHeaderurl();
            if(path!=null && path.length() > 0){
                Glide.with(mContext)
                        .load("http://"+ServerInformation.SERVER_IP+":"+ServerInformation.SERVER_PORT+path)
                        .into(civ_head);
            }
            //OkHttpClientManager.displayImage(civ_head,"http://"+ServerInformation.SERVER_IP+":"+ServerInformation.SERVER_PORT+
            //                        "/PHA_APP_Server/fileUpload/images/8792a695-28f3-42a8-8c27-c16cc03f6e8c.jpg");

            String nickname = mUserEntity.getNickname();
            String name = mUserEntity.getName();
            String sex = mUserEntity.getSex();
            Integer birthday = mUserEntity.getBirthday();
            String tel = mUserEntity.getTel();
            Integer score = mUserEntity.getScore();

            txt_showNickname.setText(nickname);
            if (name != null) {
                txt_showName.setText(name);
            } else {
                txt_showName.setText("");
            }

            if ("M".equals(sex)) {
                txt_showSex.setText("男");
            } else {
                txt_showSex.setText("女");
            }
            if (birthday != null) {

                txt_showBirthday.setText(String.valueOf(birthday/10000+"年"+birthday/100%100+"月"+birthday%100+"日"));
            } else {
                txt_showBirthday.setText("");
            }
//            if (tel != null) {
//                txt_showTel.setText(tel);
//            } else {
//                txt_showTel.setText("");
//            }
            txt_showScore.setText(String.valueOf(score));
        }
    }

    ////////////////////////////////////////
    //记录修改的信息
    String strReviseNickname;
    String strReviseName;
    String strReviseSex;
    Integer nBirthday=null;


    /**
     * 修改头像
     */
    private void reviseHeaderImg(){

        final String[] stringItems = {"打开相册"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, null);
        dialog.itemTextColor(R.color.colorPrimary);
        dialog.cancelText(R.color.colorPrimary);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showShortToast(""+position);
                if(0 == position){
                    GalleryFinal.openGallerySingle(200, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            if(resultList!=null && resultList.size()>0){
                                PhotoInfo photoInfo=resultList.get(0);
                                //showShortToast(photoInfo.getPhotoPath());
                                uploadImg(photoInfo.getPhotoPath());
//                                try {
//
//                                    //拿到图片的路径，并显示出来
//                                    FileInputStream fis = new FileInputStream(photoInfo.getPhotoPath());
//                                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
//                                    if(bitmap!=null){
//                                        civ_head.setImageBitmap(bitmap);
//                                    }
//
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                }


                            }
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {

                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 上传头像
     * @param path
     */
    private void uploadImg(final String path){
        File file=new File(path);
        if(file.exists()){
            if(NetworkUtils.isAvailable(mContext)){
                if(mProgressDialog == null){
                    mProgressDialog=new ProgressDialog(mContext);
                }
                mProgressDialog.setMessage("正在上传头像");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                try {
                    OkHttpClientManager.postAsyn(ServerInformation.BASE_URL + "upload/img", new OkHttpClientManager.ResultCallback<ResultEntity>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            mProgressDialog.dismiss();
                            showMessageDialog("",e.toString());
                        }

                        @Override
                        public void onResponse(ResultEntity response) {
                            mProgressDialog.dismiss();
                            //文件上传成功后会返回一个图片在服务器的路径：
                            ///PHA_APP_Server/fileUpload/images/6c119e57-29b6-482d-bd18-43b67f2d3ebb.jpg
                            //showMessageDialog("",response.getData().toString());
                            if(mUserEntity!=null && response.getState() == 200){
                                mUserEntity.setHeaderurl(response.getData().toString());
                                saveUserinfoToSharedPreferences();
                            }
                        }

                    },file,"imgFile");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                showShortToast(CommonHintInfo.NO_NETWORDK);
                return ;
            }
        }else{
            showShortToast("文件不存在");
        }
    }

    /**
     * 弹出对话框
     *
     * @param title   对话框的标题
     * @param conetnt 对话框的内容
     */
    private void showMessageDialog(String title, String conetnt) {
        AlertDialog.Builder b = new AlertDialog.Builder(mContext);
        // 设置对话框标题
        b.setTitle(title);
        // 设置对话框图标
        // b.setIcon(R.drawable.ic_launcher);
        // 设置对话框的内容
        b.setMessage(conetnt);
        // 添加对话框的按钮
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whitch) {

            }
        });
        b.show();
    }


    /**
     * 修改昵称
     */
    private void reviseNickname(){
        View view = getLayoutInflater().inflate(R.layout.dialog_input_view, null);
        final TextView textView = (TextView)  view.findViewById(R.id.txt_dialog_hint);
        final EditText editText = (EditText) view.findViewById(R.id.edt_dialog_input);
        textView.setText("修改昵称");
        if(mUserEntity!=null && mUserEntity.getNickname()!=null){
            editText.setText(mUserEntity.getNickname());
        }else{
            editText.setHint("请输入昵称，20字以内");
        }
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = editText.getText().toString();
                        if(content.length() == 0){
                            showShortToast("昵称不能为空");
                        }else{
                            content=content.replace(" ","");
                            if(mUserEntity!=null && !content.equals(mUserEntity.getNickname())){
                                UserEntity userEntity=new UserEntity();
                                userEntity.setRecno(mUserEntity.getRecno());
                                userEntity.setNickname(content);
                                strReviseNickname=content;
                                //提交数据到服务器
                                mPresenter.update(userEntity);
                            }else{

                            }

                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 修改姓名
     */
    private void reviseName(){
        View view = getLayoutInflater().inflate(R.layout.dialog_input_view, null);
        final TextView textView = (TextView)  view.findViewById(R.id.txt_dialog_hint);
        final EditText editText = (EditText) view.findViewById(R.id.edt_dialog_input);
        textView.setText("修改姓名");
        if(mUserEntity!=null && mUserEntity.getName()!=null){
            editText.setText(mUserEntity.getName());
        }else{
            editText.setHint("请输入姓名，10字以内");
        }
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = editText.getText().toString();
                        if(content.length() == 0){
                            showShortToast("姓名不能为空");
                        }else{
                            content=content.replace(" ","");
                            if(mUserEntity!=null && !content.equals(mUserEntity.getName())){
                                UserEntity userEntity=new UserEntity();
                                userEntity.setRecno(mUserEntity.getRecno());
                                userEntity.setName(content);
                                strReviseName=content;
                                //提交数据到服务器
                                mPresenter.update(userEntity);
                            }
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


    /**
     * 记录选择的性别
     */
    int nSex=0;
    int nChoiceSex=-1;
    /**
     * 修改性别
     */
    private void reviseSex(){
        final String[] sexs = { "男","女"};
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(mContext);
        singleChoiceDialog.setTitle("请选择性别");
        // 第二个参数是默认选项，此处设置为0

        if(mUserEntity!=null && "F".equals(mUserEntity.getSex())){
            nSex=1;
        }
        singleChoiceDialog.setSingleChoiceItems(sexs, nSex,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nChoiceSex=which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(nChoiceSex!=-1){
                            if(mUserEntity!=null && nSex!=nChoiceSex){
                                UserEntity userEntity=new UserEntity();
                                userEntity.setRecno(mUserEntity.getRecno());
                                if(0 == nChoiceSex){
                                    //修改性别为男
                                    userEntity.setSex("M");
                                    strReviseSex="M";
                                }else{
                                    //修改性别为女
                                    userEntity.setSex("F");
                                    strReviseSex="F";
                                }
                                //提交数据到服务器
                                mPresenter.update(userEntity);
                            }
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * 日期选择器对话框监听
     */
    DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //String text="您选择了:"+year+"年"+(month+1)+"月"+dayOfMonth+"日";
            //showShortToast(text);
            nBirthday=year;
            nBirthday=nBirthday*100+(month+1);
            nBirthday=nBirthday*100+dayOfMonth;
            if(mUserEntity!=null && nBirthday!=mUserEntity.getBirthday()){
                UserEntity userEntity=new UserEntity();
                userEntity.setRecno(mUserEntity.getRecno());
                userEntity.setBirthday(nBirthday);
                mPresenter.update(userEntity);
            }
        }
    };

    /**
     * 修改生日
     */
    private void reviseBirthday(){
        int year,month,day;
        if(mUserEntity!=null && mUserEntity.getBirthday()!=null){
            year=mUserEntity.getBirthday()/10000;
            month=mUserEntity.getBirthday()/100%100;
            day=mUserEntity.getBirthday()%100;
            new DatePickerDialog(mContext,onDateSetListener,
                    year,
                    month-1,
                    day).show();
            return ;
        }
        Calendar calendar= Calendar.getInstance();
        new DatePickerDialog(mContext,onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 退出登录
     */
    private void logout(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("操作提示")//设置对话框的标题
                .setMessage("确定退出账号吗？")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mPhaApplication!=null){
                            mPhaApplication.setUserEntity(null);
                            mPhaApplication.setLogin(false);
                        }
                        putBooleanToSharedPreferences(SharePreferencesKey
                                .IS_LOGIN,false);
                        //发送一个广播给个人中心Fragment显示数据
                        Intent intent=new Intent(PersonalityFragment.LOGIN_ACTION);
                        sendBroadcast(intent);
                        dialog.dismiss();
                        finish();
                    }
                }).create();
        dialog.show();
    }


}
