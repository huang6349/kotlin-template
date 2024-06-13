package org.huangyalong.modules.file.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.dromara.x.file.storage.core.FileStorageService;
import org.huangyalong.core.commons.exception.BadRequestException;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.modules.file.response.FileVO;
import org.huangyalong.modules.file.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@AllArgsConstructor
@RestController
@RequestMapping("/file")
@Tag(name = "文件管理")
public class FileController {

    @SaCheckLogin
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传文件")
    public ApiResponse<FileVO> upload(@RequestParam("file") MultipartFile file) {
        val path = DatePattern.PURE_DATE_FORMAT.format(new DateTime());
        val fileInfo = SpringUtil.getBean(FileStorageService.class)
                .of(file)
                .setPath(StrUtil.format("{}/", path))
                .upload();
        val fileVO = FileVO.create()
                .setId(fileInfo.getId())
                .setUrl(fileInfo.getUrl())
                .setSize(fileInfo.getSize())
                .setFilename(fileInfo.getFilename())
                .setOrigFilename(fileInfo.getOriginalFilename())
                .setExt(fileInfo.getExt())
                .setContentType(fileInfo.getContentType())
                .setCreateTime(fileInfo.getCreateTime())
                .setUpdateTime(fileInfo.getCreateTime());
        return ApiResponse.ok(fileVO);
    }

    @GetMapping("/download/{id:.+}")
    @Operation(summary = "下载文件")
    public void download(@PathVariable Serializable id,
                         HttpServletResponse response) {
        try {
            val out = response.getOutputStream();
            SpringUtil.getBean(FileStorageService.class)
                    .download(SpringUtil.getBean(FileService.class)
                            .getById(id)
                            .without())
                    .outputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("下载失败，服务器暂时无法处理这个文件");
        }
    }
}
