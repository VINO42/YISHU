package io.github.vino42.base.context;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 10:18
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
public  interface   SecurityContext  extends Serializable {

    // ~ Methods
    // ========================================================================================================

    /**
     * Obtains the currently authenticated principal, or an authentication request token.
     *
     * @return the <code>Authentication</code> or <code>null</code> if no authentication
     * information is available
     */
    Authentication getAuthentication();

    /**
     * Changes the currently authenticated principal, or removes the authentication
     * information.
     *
     * @param authentication the new <code>Authentication</code> token, or
     * <code>null</code> if no further authentication information should be stored
     */
    void setAuthentication(Authentication authentication);

}
