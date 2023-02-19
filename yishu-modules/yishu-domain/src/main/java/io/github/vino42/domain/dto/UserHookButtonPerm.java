package io.github.vino42.domain.dto;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/23 12:40
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class UserHookButtonPerm implements Serializable {
    private Boolean add = true;
    private Boolean batchAdd = true;
    private Boolean export = true;
    private Boolean batchDelete = true;
    private Boolean status = true;
    private Boolean view = true;
    private Boolean edit = true;
    private Boolean reset = true;
    private Boolean delete = true;

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getBatchAdd() {
        return batchAdd;
    }

    public void setBatchAdd(Boolean batchAdd) {
        this.batchAdd = batchAdd;
    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public Boolean getBatchDelete() {
        return batchDelete;
    }

    public void setBatchDelete(Boolean batchDelete) {
        this.batchDelete = batchDelete;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getView() {
        return view;
    }

    public void setView(Boolean view) {
        this.view = view;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
