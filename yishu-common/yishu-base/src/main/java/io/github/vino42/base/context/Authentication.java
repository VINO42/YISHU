package io.github.vino42.base.context;

import java.io.Serializable;
import java.util.Collection;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 10:20
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright :
 * @Decription :
 * =====================================================================================
 */
public interface Authentication extends Serializable {

    Collection<String> getRoles();

    Collection<String> getPerms();

    Collection<String> getResources();

    Object getDetails();


    boolean isAuthenticated();


    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

}
