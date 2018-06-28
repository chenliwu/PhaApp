package com.clw.phaapp.model.entity;

import com.clw.mysdk.base.BaseBean;

public class UserEntity extends BaseBean{
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 账号
     */
    private String usercode;

    /**
     * 登录密码
     */
    private String pwd;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别：男M女F
     */
    private String sex;

    /**
     * 出生日期
     */
    private Integer birthday;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 头像URL
     */
    private String headerurl;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 状态：0正常，1锁定
     */
    private Byte status;

    /**
     * 注册日期
     */
    private Integer regdate;

    /**
     * 密保问题
     */
    private String security;

    /**
     * 密保答案
     */
    private String answer;

    /**
     * 备注
     */
    private String mark;

    /**
     * 记录号
     * @return recno 记录号
     */
    public Long getRecno() {
        return recno;
    }

    /**
     * 记录号
     * @param recno 记录号
     */
    public void setRecno(Long recno) {
        this.recno = recno;
    }

    /**
     * 账号
     * @return usercode 账号
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * 账号
     * @param usercode 账号
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    /**
     * 登录密码
     * @return pwd 登录密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 登录密码
     * @param pwd 登录密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 昵称
     * @return nickname 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 性别：男M女F
     * @return sex 性别：男M女F
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别：男M女F
     * @param sex 性别：男M女F
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 出生日期
     * @return birthday 出生日期
     */
    public Integer getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    /**
     * 电话号码
     * @return tel 电话号码
     */
    public String getTel() {
        return tel;
    }

    /**
     * 电话号码
     * @param tel 电话号码
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 微信号
     * @return wechat 微信号
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * 微信号
     * @param wechat 微信号
     */
    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    /**
     * QQ号码
     * @return qq QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * QQ号码
     * @param qq QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 头像URL
     * @return headerurl 头像URL
     */
    public String getHeaderurl() {
        return headerurl;
    }

    /**
     * 头像URL
     * @param headerurl 头像URL
     */
    public void setHeaderurl(String headerurl) {
        this.headerurl = headerurl == null ? null : headerurl.trim();
    }

    /**
     * 积分
     * @return score 积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 积分
     * @param score 积分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 状态：0正常，1锁定
     * @return status 状态：0正常，1锁定
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态：0正常，1锁定
     * @param status 状态：0正常，1锁定
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 注册日期
     * @return regdate 注册日期
     */
    public Integer getRegdate() {
        return regdate;
    }

    /**
     * 注册日期
     * @param regdate 注册日期
     */
    public void setRegdate(Integer regdate) {
        this.regdate = regdate;
    }

    /**
     * 密保问题
     * @return security 密保问题
     */
    public String getSecurity() {
        return security;
    }

    /**
     * 密保问题
     * @param security 密保问题
     */
    public void setSecurity(String security) {
        this.security = security == null ? null : security.trim();
    }

    /**
     * 密保答案
     * @return answer 密保答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 密保答案
     * @param answer 密保答案
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 备注
     * @return mark 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 备注
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "recno=" + recno +
                ", usercode='" + usercode + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", tel='" + tel + '\'' +
                ", wechat='" + wechat + '\'' +
                ", qq='" + qq + '\'' +
                ", headerurl='" + headerurl + '\'' +
                ", score=" + score +
                ", status=" + status +
                ", regdate=" + regdate +
                ", security='" + security + '\'' +
                ", answer='" + answer + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}