package io.github.vino42.auth.configuration;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/17 21:47
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 暂时不用
 * =====================================================================================
 */
//@Component
public class InitCasbinDatasAction /*implements ApplicationRunner*/ {

  /*  private static final Logger LOGGER = LoggerFactory.getLogger(InitCasbinDatasAction.class);
    @Autowired
    private Enforcer enforcer;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void run(ApplicationArguments args) {
        *//**
     * p, alice, data1, read
     * p, bob, data2, write
     * p, data_group_admin, data_group, write
     *
     * g, alice, data_group_admin
     * g2, data1, data_group
     * g2, data2, data_group
     *//*
        stringRedisTemplate.delete(REDIS_CASBIN_CACHE_KEY);
        //p, alice, data1, read
        List<CasbinUserResourcePermissionModel> casbinUserResourcePermissionModels = sysUserService.getCasbinUserResourcePermissionModel();
        //g, alice, data_group_admin
        List<CasbinUserRoleModel> casbinUserRoleModels = sysUserService.getCasbinUserRoleModel();
        //g2, data2, data_group
        List<CasbinRoleResourceModel> casbinRoleResourceModels = sysUserService.getCasbinRoleResourceModel();

//        List<CasbinRule> list = toCasbinRuleList(casbinUserResourcePermissionModels, casbinUserRoleModels, casbinRoleResourceModels);
        casbinUserResourcePermissionModels.forEach(casbinUserResourcePermissionModel -> {
            enforcer.addPermissionForUser(casbinUserResourcePermissionModel.getUserId(),
                    casbinUserResourcePermissionModel.getResourcePath(),
                    casbinUserResourcePermissionModel.getPermission());
        });
        //g, alice, data_group_admin
        casbinUserRoleModels.forEach(casbinUserRoleModel -> {
            enforcer.addRoleForUser(casbinUserRoleModel.getUserId(), casbinUserRoleModel.getUserGroup());
        });
        //g2, data2, data_group
        casbinRoleResourceModels.forEach(casbinRoleResourceModel -> {
            enforcer.addGroupingPolicy(CASBIN_POLICY_G2, casbinRoleResourceModel.getResourcePath(), casbinRoleResourceModel.getPermissionGroup());
        });
        enforcer.savePolicy();
    }*/
}