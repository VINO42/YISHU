package io.github.vino42.web.controller.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.configuration.WxMiniAppConfiguration;
import io.github.vino42.domain.dto.WechatUserLoginDto;
import io.github.vino42.domain.entity.SysRegionGeoEntity;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.ISysRegionGeoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/3 20:24
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Slf4j
@RestController
@Tag(name = "微信端管理登录模块")
@RequestMapping("/wechat")
public class WechatAuthController {
    @Autowired
    ISysAccountService sysAccountService;
    @Autowired
    ISysRegionGeoService sysRegionGeoService;

    @GetMapping("/miniapp/sessionInfo/{appId}")
    public ServiceResponseResult<WxMaJscode2SessionResult> getMiniAppSessionInfo(@PathVariable("appId") String appId,
                                                                                 @RequestParam("jscode") String jsCode) throws WxErrorException {
        WxMaService wxMaService = WxMiniAppConfiguration.getMaServices().get(appId);
        if (wxMaService == null) {
            return ResultMapper.illegalArgument("appid illegal");
        }
        WxMaUserService userService = wxMaService.getUserService();
        WxMaJscode2SessionResult userInfo = userService.getSessionInfo(jsCode);
        log.debug("{}getMiniAppSessionInfo{}", appId, userInfo);
        return ResultMapper.ok(userInfo);
    }


    @PostMapping(value = "/login")
    public ServiceResponseResult<UserSessionDto> login(@Valid @RequestBody WechatUserLoginDto wechatUserLoginDto) throws ExecutionException, InterruptedException {
        //根据unionId和openId查询用户是否存在
        //不存在 为微信用户创建账号 为微信用户创建用户 分配用户组 ，保存用户的头像 昵称
        //如果存在那么生成用户token
        //根据微信端的模糊地理位置 传输的经纬度 查询当前用户所在的城市
        //生成token 将用户头像 token 所在城市regionId 返回前端
        return sysAccountService.wechatLogin(wechatUserLoginDto);
    }
}
