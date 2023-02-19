package io.github.vino42.configuration;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author VINO
 * @date 2020/4/11 09:58
 * @describe sql注入过滤校验器
 */
@Slf4j
public class SqlArgumentInjectFilterResolver implements HandlerMethodArgumentResolver {
    
    private static final String[] KEYWORDS = {
            "master",
            "truncate",
            "insert",
            "select",
            "delete",
            "update",
            "declare",
            "alter",
            "drop",
            "sleep"
    };
    
    private static final String CHECKSQL = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
    
    private static final String CHECKSQL_1 =
            "/\\w*((\\%27)|(\\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix";
    
    private static List<String> CHECKSQL_KEYWORDS =
            Arrays.asList(
                    "'|and|exec|insert|select|delete|update|count|*|%|chr|id|master|truncate|char|declare|;|or|-|+|=|,|\"|"
                            .split("\\|"));
    
    /**
     * 判断Controller是否包含page 参数
     *
     * @param parameter 参数
     * @return 是否过滤
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {


        return parameter.getParameterType().equals(Page.class);
    }
    
    /**
     * @param parameter 入参集合
     * @param mavContainer model 和 view
     * @param webRequest web相关
     * @param binderFactory 入参解析
     * @return 检查后新的page对象
     *     <p>page 只支持查询 GET .如需解析POST获取请求报文体处理
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        
        String[] ascs = request.getParameterValues("ascs");
        String[] descs = request.getParameterValues("descs");
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        
        Page page = new Page();
        if (StrUtil.isNotBlank(current)) {
            page.setCurrent(Long.parseLong(current));
        }
        
        if (StrUtil.isNotBlank(size)) {
            page.setSize(Long.parseLong(size));
        }
        
        List<OrderItem> orderItemList = new ArrayList<>();
        Optional.ofNullable(ascs)
                .ifPresent(
                        s ->
                                orderItemList.addAll(
                                        Arrays.stream(s)
                                                .filter(sqlInjectPredicate())
                                                .filter(checkSql())
                                                .filter(checkSqlPatern())
                                                .map(OrderItem::asc)
                                                .collect(Collectors.toList())));
        Optional.ofNullable(descs)
                .ifPresent(
                        s ->
                                orderItemList.addAll(
                                        Arrays.stream(s)
                                                .filter(sqlInjectPredicate())
                                                .map(OrderItem::desc)
                                                .collect(Collectors.toList())));
        page.addOrder(orderItemList);
        
        return page;
    }
    
    private Predicate<? super String> checkSqlPatern() {
        
        return sql -> {
            boolean matches = !Pattern.matches(CHECKSQL, sql);
            boolean matches1 = !Pattern.matches(CHECKSQL_1, sql);
            return matches && matches1;
        };
    }
    
    /**
     * 判断用户输入里面有没有关键字
     *
     * @return Predicate
     */
    private Predicate<String> sqlInjectPredicate() {
        
        return sql -> {
            for (String keyword : KEYWORDS) {
                if (StrUtil.containsIgnoreCase(sql, keyword)) {
                    return false;
                }
            }
            return true;
        };
    }
    
    /**
     * 检测sql，防止sql注入
     *
     * @return 正常返回true 异常返回false
     */
    public Predicate<String> checkSql() {
        
        return sql -> {
            for (String keyword : CHECKSQL_KEYWORDS) {
                if (StrUtil.containsIgnoreCase(sql, keyword)) {
                    return false;
                }
            }
            return true;
        };
    }
}
