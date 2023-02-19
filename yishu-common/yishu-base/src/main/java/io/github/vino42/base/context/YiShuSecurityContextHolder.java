package io.github.vino42.base.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.util.Assert;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 0:58
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 模仿SrpingSecurityContextHolder
 * =====================================================================================
 */
public class YiShuSecurityContextHolder {
    private static final ThreadLocal<SecurityContext> SECURITY_THREAD_CONTEXT = new SecurityThreadLocal();


    public static void clearContext() {
        SECURITY_THREAD_CONTEXT.remove();
    }

    public static SecurityContext getContext() {
        SecurityContext ctx = SECURITY_THREAD_CONTEXT.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            SECURITY_THREAD_CONTEXT.set(ctx);
        }

        return ctx;
    }

    public static void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        SECURITY_THREAD_CONTEXT.set(context);
    }

    public static SecurityContext createEmptyContext() {
        return new YiShuSecurityContext();
    }


    private static class SecurityThreadLocal extends TransmittableThreadLocal<SecurityContext> {
        @Override
        protected SecurityContext initialValue() {
            return super.initialValue();
        }
    }
}
