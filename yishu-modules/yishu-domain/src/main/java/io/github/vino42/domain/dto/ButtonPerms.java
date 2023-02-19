package io.github.vino42.domain.dto;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/23 12:39
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class ButtonPerms implements Serializable {

    private String component;

    private String button;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
