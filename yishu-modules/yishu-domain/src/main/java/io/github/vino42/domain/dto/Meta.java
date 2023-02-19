package io.github.vino42.domain.dto;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/22 16:26
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
public class Meta implements Serializable {
    private String icon;
    private String title;
    private boolean isLink;
    private boolean isHide;
    private boolean isFull;
    private boolean isAffix;
    private boolean isKeepAlive;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLink() {
        return isLink;
    }

    public void setLink(boolean link) {
        isLink = link;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean isAffix() {
        return isAffix;
    }

    public void setAffix(boolean affix) {
        isAffix = affix;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        isKeepAlive = keepAlive;
    }
}
