package io.github.vino42.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/18 20:29
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class UserLoginDto implements Serializable {
    @NotBlank(message = "手机账号不能为空")
    @Size(min = 11, max = 11, message = "手机账号长度为11位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码长度为8-16位")
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
