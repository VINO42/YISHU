package io.github.vino42.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/22 16:24
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
public class ResourceTreeDto implements Serializable {

    private String id;
    private String parentId;
    private String levelCode;
    private String path;
    private String name;
    private String component;
    private Meta meta;
    private int sort;
    private List<ResourceTreeDto> children;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponent() {
        return component;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public List<ResourceTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceTreeDto> children) {
        this.children = children;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
