package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    /**
     * 通过AliOssUtil上传文件到oss
     *
     * @param file
     * @return
     */
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping(value = "/upload")

    public Result upload(MultipartFile file) {
        try {
            String url = aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("上传文件失败");
    }
}

