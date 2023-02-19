package io.github.vino42.base.context;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 11:09
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
public class YiShuSecurityContext implements SecurityContext {

    private Authentication authentication;

    public YiShuSecurityContext() {
    }

    public YiShuSecurityContext(Authentication authentication) {
        this.authentication = authentication;
        this.authentication.setAuthenticated(authentication.getDetails() == null);
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {

        this.authentication = authentication;
        this.authentication.setAuthenticated(authentication.getDetails() != null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof YiShuSecurityContext) {
            YiShuSecurityContext test = (YiShuSecurityContext) obj;

            if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) {
                return true;
            }

            if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
                    && this.getAuthentication().equals(test.getAuthentication())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        if (this.authentication == null) {
            return -1;
        } else {
            return this.authentication.hashCode();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        if (this.authentication == null) {
            sb.append(": Null authentication");
        } else {
            sb.append(": Authentication: ").append(this.authentication);
        }

        return sb.toString();
    }
}
