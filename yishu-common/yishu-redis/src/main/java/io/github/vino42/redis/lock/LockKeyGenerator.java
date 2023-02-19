package io.github.vino42.redis.lock;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import io.github.vino42.redis.lock.annotation.DistributeLock;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 分布式锁Key生成器
 *
 * @author VINO
 * @since 1.0.0
 */
public class LockKeyGenerator implements KeyGenerator {

    private static final ParameterNameDiscoverer NAME_DISCOVERER =
            new DefaultParameterNameDiscoverer();

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public String getKeyName(MethodInvocation invocation, DistributeLock rlock) {

        StringBuilder sb = new StringBuilder();
        Method method = invocation.getMethod();
        sb.append(method.getDeclaringClass().getName()).append(StrPool.COMMA).append(method.getName());
        if (rlock.keys().length > 1 || !StrUtil.EMPTY.equals(rlock.keys()[0])) {
            sb.append(getSpelDefinitionKey(rlock.keys(), method, invocation.getArguments()));
        }
        return sb.toString();
    }

    private String getSpelDefinitionKey(
            String[] definitionKeys, Method method, Object[] parameterValues) {

        EvaluationContext context =
                new MethodBasedEvaluationContext(null, method, parameterValues, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList<>(definitionKeys.length);
        for (String definitionKey : definitionKeys) {
            if (definitionKey != null && !definitionKey.isEmpty()) {
                String key = PARSER.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }
        return StringUtils.collectionToDelimitedString(
                definitionKeyList, StrPool.DOT, StrUtil.EMPTY, StrUtil.EMPTY);
    }
}
