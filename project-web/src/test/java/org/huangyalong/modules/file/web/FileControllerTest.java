package org.huangyalong.modules.file.web;

import cn.dev33.satoken.stp.StpUtil;
import lombok.val;
import org.huangyalong.core.AbstractIntegrationTest;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.modules.file.request.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class FileControllerTest extends AbstractIntegrationTest {

    @Resource
    private MockMvc mvc;

    @BeforeEach
    void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Test
    void upload() throws Exception {
        mvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                        .file(FileUtil.createMockFile())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.origFilename").value(FileUtil.DEFAULT_ORIG_FILENAME))
                .andExpect(jsonPath("$.data.ext").value(FileUtil.DEFAULT_EXT))
                .andExpect(jsonPath("$.data.contentType").value(MediaType.TEXT_PLAIN_VALUE));
    }
}
