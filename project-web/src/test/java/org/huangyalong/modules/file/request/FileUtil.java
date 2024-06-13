package org.huangyalong.modules.file.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

public final class FileUtil {

    public static final String DEFAULT_NAME = "file";
    public static final String DEFAULT_EXT = "txt";
    public static final String DEFAULT_ORIG_FILENAME = StrUtil.format("{}.{}", DEFAULT_NAME, DEFAULT_EXT);

    public static MockMultipartFile createMockFile() {
        return new MockMultipartFile(DEFAULT_NAME,
                DEFAULT_ORIG_FILENAME,
                MediaType.TEXT_PLAIN_VALUE,
                RandomUtil.randomString(12).getBytes(StandardCharsets.UTF_8));
    }
}
