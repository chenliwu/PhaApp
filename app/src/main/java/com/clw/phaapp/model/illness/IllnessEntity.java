package com.clw.phaapp.model.illness;

/**
 * 易源数据，常见疾病实体类
 *
 * @author chenliwu
 * @create 2018-03-13 23:21
 **/
public class IllnessEntity {

    /**
     * 疾病id
     */
    private String id;

    /**
     * 描述
     */
    private String summary;

    /**
     * 科目名称
     */
    private String typeName;

    /**
     * 科室id
     */
    private String typeId;

    /**
     * 子科室id
     */
    private String subTypeId;

    /**
     * 子科室名称
     */
    private String subTypeName;

    /**
     * 疾病名称
     */
    private String name;

    /**
     * 标签
     */
    private String tagList;

    /**
     * 入库时间
     */
    private String ct;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }
}
