package cn.easyproject.easyee.sm.sys.entity;

import java.io.Serializable;

import cn.easyproject.easyee.auto.EasyCriteria;
import cn.easyproject.easyee.auto.EasyField;
import cn.easyproject.easyee.auto.EasyId;
import cn.easyproject.easyee.auto.EasyModule;
import cn.easyproject.easyee.auto.EasyPage;

@EasyModule(label = "数据字典", mybatisTable = "SYS_DICT")
@EasyPage
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @EasyId
    @EasyField(show = false, inputShow = false) // 查询和输入时不显示字段信息
    private String id;

    @EasyCriteria(label = "字典分类")
    @EasyField(label = "字典分类")
    private String catalogId;

    @EasyCriteria(label = "关键字", like = true) // 条件字段，模糊查询
    @EasyField(label = "关键字")
    private String key;

    @EasyCriteria(label = "字典值", like = true) // 条件字段，模糊查询
    @EasyField(label = "字典值")
    private String value;

    @EasyField(label = "排序号")
    private Integer sortNum;

    @EasyField(label = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
