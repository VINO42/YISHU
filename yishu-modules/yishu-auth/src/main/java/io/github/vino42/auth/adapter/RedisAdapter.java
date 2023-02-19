// Copyright 2021 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.github.vino42.auth.adapter;

/**
 * =====================================================================================
 *
 * @Created :   2022/11/27 23:13
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : vino42.github.io
 * @Decription :    自定义RedisAdaptor
 * =====================================================================================
 */
public class RedisAdapter /*implements Adapter */ {
//    private final StringRedisTemplate stringRedisTemplate;
//
//    public RedisAdapter(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }
//
//    /**
//     * loadPolicy loads all policy rules from the storage.
//     */
//    @Override
//    public void loadPolicy(Model model) {
//        Long length = stringRedisTemplate.opsForList().size(REDIS_CASBIN_CACHE_KEY);
//        if (length == null) {
//            return;
//        }
//        List<String> policies = stringRedisTemplate.opsForList().range(REDIS_CASBIN_CACHE_KEY, 0, length);
//        for (String policy : policies) {
//            CasbinRule rule = ((JSONObject) JSONObject.parse(policy)).toJavaObject(CasbinRule.class);
//            loadPolicyLine(rule, model);
//        }
//    }
//
//    /**
//     * savePolicy saves all policy rules to the storage.
//     */
//    @Override
//    public void savePolicy(Model model) {
//        stringRedisTemplate.delete(REDIS_CASBIN_CACHE_KEY);
//        extracted(model, "p");
//        extracted(model, "g");
//    }
//
//    /**
//     * addPolicy adds a policy rule to the storage.
//     */
//    @Override
//    public void addPolicy(String sec, String ptype, List<String> rule) {
//        if (CollectionUtils.isEmpty(rule)) {
//            return;
//        }
//        CasbinRule line = savePolicyLine(ptype, rule);
//        stringRedisTemplate.opsForList().rightPush(REDIS_CASBIN_CACHE_KEY, JSONObject.toJSONString(line));
//    }
//
//    /**
//     * removePolicy removes a policy rule from the storage.
//     */
//    @Override
//    public void removePolicy(String sec, String ptype, List<String> rule) {
//        if (CollectionUtils.isEmpty(rule)) {
//            return;
//        }
//        CasbinRule line = savePolicyLine(ptype, rule);
//        stringRedisTemplate.opsForList().remove(REDIS_CASBIN_CACHE_KEY, 1, JSONObject.toJSONString(line));
//    }
//
//    /**
//     * removeFilteredPolicy removes policy rules that match the filter from the storage.
//     */
//    @Override
//    public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
//        List<String> values = Optional.of(Arrays.asList(fieldValues)).orElse(new ArrayList<>());
//        throw new RuntimeException("not implement");
//
//    }
//
//    /**
//     * AddPolicies adds policy rules to the storage.
//     */
//    public void addPolicies(String sec, String ptype, List<List<String>> rules) {
//        for (List<String> rule : rules) {
//            addPolicy(sec, ptype, rule);
//        }
//    }
//
//    /**
//     * RemovePolicies removes policy rules from the storage.
//     */
//    public void removePolicies(String sec, String ptype, List<List<String>> rules) {
//        for (List<String> rule : rules) {
//            removePolicy(sec, ptype, rule);
//        }
//    }
//
//
//    private void extracted(Model model, String type) {
//        for (Map.Entry<String, Assertion> entry : model.model.get(type).entrySet()) {
//            String ptype = entry.getKey();
//            Assertion ast = entry.getValue();
//
//            for (List<String> rule : ast.policy) {
//                CasbinRule line = savePolicyLine(ptype, rule);
//                stringRedisTemplate.opsForList().rightPush(REDIS_CASBIN_CACHE_KEY, JSONObject.toJSONString(line));
//            }
//        }
//    }
//
//    private void loadPolicyLine(CasbinRule line, Model model) {
//        String lineText = line.getPtype();
//        if (StrUtil.isNotBlank(line.getV0())) {
//            lineText += COMMA + C_SPACE + line.getV0();
//        }
//        if (StrUtil.isNotBlank(line.getV1())) {
//            lineText += COMMA + C_SPACE + line.getV1();
//        }
//        if (StrUtil.isNotBlank(line.getV2())) {
//            lineText += COMMA + C_SPACE + line.getV2();
//        }
//        if (StrUtil.isNotBlank(line.getV3())) {
//            lineText += COMMA + C_SPACE + line.getV3();
//        }
//        if (StrUtil.isNotBlank(line.getV4())) {
//            lineText += COMMA + C_SPACE + line.getV4();
//        }
//        if (StrUtil.isNotBlank(line.getV5())) {
//            lineText += COMMA + C_SPACE + line.getV5();
//        }
//
//        Helper.loadPolicyLine(lineText, model);
//    }
//
//    private CasbinRule savePolicyLine(String ptype, List<String> rule) {
//        CasbinRule line = new CasbinRule();
//
//        line.setPtype(ptype);
//        if (rule.size() > 0) {
//            line.setV0(rule.get(0));
//        }
//        if (rule.size() > 1) {
//            line.setV1(rule.get(1));
//        }
//        if (rule.size() > 2) {
//            line.setV2(rule.get(2));
//        }
//        if (rule.size() > 3) {
//            line.setV3(rule.get(3));
//        }
//        if (rule.size() > 4) {
//            line.setV4(rule.get(4));
//        }
//        if (rule.size() > 5) {
//            line.setV5(rule.get(5));
//        }
//
//        return line;
//    }
}
