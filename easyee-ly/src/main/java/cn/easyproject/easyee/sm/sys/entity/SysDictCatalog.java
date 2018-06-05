package cn.easyproject.easyee.sm.sys.entity;
import java.io.Serializable;

import cn.easyproject.easyee.auto.EasyField;
import cn.easyproject.easyee.auto.EasyId;
import cn.easyproject.easyee.auto.EasyModule;

import cn.easyproject.easyee.auto.EasyPage;

@EasyModule(label = "字典分类", mybatisTable = "SYS_DICT_CATALOG")
@EasyPage
public class SysDictCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @EasyId
    @EasyField(show = false, inputShow = false) // 查询和输入时不显示字段信息
    private String id;

    @EasyField(show = false, inputShow = false)
    private String parentId;

    @EasyField(label = "分类名称")
    private String catalogName;

    @EasyField(label = "排序号")
    private Integer sortNum;

    @EasyField(label = "备注")
    private String remark;

    public SysDictCatalog() {
    }

    public SysDictCatalog(String id, String catalogName) {
        super();
        this.id = id;
        this.catalogName = catalogName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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
