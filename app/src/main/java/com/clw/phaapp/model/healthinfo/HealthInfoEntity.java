package com.clw.phaapp.model.healthinfo;

import com.clw.phaapp.model.entity.PageDataBean;

/**
 * 资讯条目对象
 * 健康资讯实体类
 *
 * @author chenliwu
 * @create 2018-03-14 21:10
 **/
public class HealthInfoEntity extends PageDataBean{

    /**
     * 标识id
     */
    private String  id;

    /**
     * 来源
     */
    private String  author;

    /**
     * 类目id
     * 1综合资讯，2疾病资讯，3食品资讯
     */
    private String  tid;

    /**
     * 类目名称
     */
    private String  tname;
    /**
     * 标题
     */
    private String  title;
    /**
     * 发布时间 2015-07-28 17:05:24
     */
    private String  time;
    /**
     * 图片
     */
    private String  img;
    /**
     * 正文，此字段在明细接入点中才会出现
     */
    private String  content;


    /**
     * 查询关键字
     */
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HealthInfoEntity{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", tid='" + tid + '\'' +
                ", tname='" + tname + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
