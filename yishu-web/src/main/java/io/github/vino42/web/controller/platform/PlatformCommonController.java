package io.github.vino42.web.controller.platform;

import cn.hutool.core.lang.UUID;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.minio.configuration.MinioProperties;
import io.github.vino42.minio.service.ObjectService;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static cn.hutool.core.text.StrPool.DOT;
import static cn.hutool.core.text.StrPool.SLASH;
import static io.github.vino42.base.constants.YiShuConstant.OssConstants.IMG_BUCKET_NAME;
import static io.github.vino42.base.enums.GenderEnum.getGender;
import static io.github.vino42.base.enums.ResourceTypeEnum.getResourceTypeEnum;
import static io.github.vino42.enums.TableStatusEnum.getTableStatus;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:42
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/common")
public class PlatformCommonController {
    @Autowired
    ObjectService objectService;
    @Autowired
    MinioProperties minioProperties;
    @Value("${yishu.oss.minio.displayendpoint}")
    private String displayUrlPrefix;

    @GetMapping(value = "/status")
    public ServiceResponseResult getStatus() {
        return ResultMapper.ok(getTableStatus());
    }


    @GetMapping(value = "/gender")
    public ServiceResponseResult getGenders() {
        return ResultMapper.ok(getGender());
    }


    @GetMapping(value = "/getAccountTypeEnum")
    public ServiceResponseResult getAccountTypeEnum() {
        return ResultMapper.ok(AccountTypeEnum.getAccountTypeEnum());
    }


    @GetMapping(value = "/resourceTypes")
    public ServiceResponseResult resourceTypes() {
        return ResultMapper.ok(getResourceTypeEnum());
    }

    @RequestMapping(value = "/file/uploadImg")
    public ServiceResponseResult uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(DOT) + 1);
        String fileName = UUID.fastUUID().toString(true) + DOT + ext;
        PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
                .bucket(IMG_BUCKET_NAME)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1).build();
        ObjectWriteResponse objectWriteResponse = objectService.putObject(objectArgs);
        return ResultMapper.ok(displayUrlPrefix + SLASH + IMG_BUCKET_NAME + SLASH + objectWriteResponse.object());
    }

}
