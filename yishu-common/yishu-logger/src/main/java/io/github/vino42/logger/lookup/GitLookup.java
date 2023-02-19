package io.github.vino42.logger.lookup;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static io.github.vino42.base.constants.YiShuConstant.NOT_AVALIABLE;


/**
 * =====================================================================================
 *
 * @Created :   2021/12/16 17:52
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :   38912428@qq.com
 * @Copyright :
 * @Description : gitLookUp
 * <p>
 * =====================================================================================
 */
@Slf4j
@Plugin(name = "git", category = StrLookup.CATEGORY)
public class GitLookup extends AbstractLookup {

    private static final String PROPERTIES_NAME = "git.properties";

    private static Map<String, String> GIT_CACHE = Maps.newHashMapWithExpectedSize(1);

    private static final String GIT_BRANCH_KEY = "git.branch";

    static {
        InputStream inputStream = GitLookup.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
        if (inputStream != null) {
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                log.error("[git endpoint error,emsg::{}]", e.getMessage(), e);
            }

            String jsonStr = resultStringBuilder.toString();
            if (StringUtils.isNotBlank(jsonStr)) {
                JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
                GIT_CACHE.put(GIT_BRANCH_KEY, jsonObject.getStr(GIT_BRANCH_KEY, NOT_AVALIABLE));
            }
        }
    }

    @Override
    public String lookup(final LogEvent event, final String key) {
        return GIT_CACHE.getOrDefault(key, NOT_AVALIABLE);
    }

}
