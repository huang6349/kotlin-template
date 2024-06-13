package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.enums.IsDeleted;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.enums.PermStatus;
import org.huangyalong.modules.system.request.PermUtil;
import org.huangyalong.modules.system.service.PermService;
import org.huangyalong.web.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.hasItem;
import static org.huangyalong.modules.system.domain.table.PermTableDef.PERM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class PermControllerTest extends AbstractControllerTest<PermService, Perm> {

    @Resource
    private MockMvc mvc;

    @BeforeEach
    void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> {
            Assertions.assertThat(perms).hasSize(Convert.toInt(beforeSize + 1));
            val testPerm = CollUtil.getFirst(perms);
            Assertions.assertThat(testPerm).isNotNull();
            Assertions.assertThat(testPerm.getId()).isNotNull();
            Assertions.assertThat(testPerm.getName()).isEqualTo(PermUtil.DEFAULT_NAME);
            Assertions.assertThat(testPerm.getCode()).isEqualTo(PermUtil.DEFAULT_CODE);
            Assertions.assertThat(testPerm.getDesc()).isEqualTo(PermUtil.DEFAULT_DESC);
            Assertions.assertThat(testPerm.getStatus()).isEqualTo(PermStatus.TYPE0);
            Assertions.assertThat(testPerm.getCreateTime()).isNotNull();
            Assertions.assertThat(testPerm.getUpdateTime()).isNotNull();
            Assertions.assertThat(testPerm.getVersion()).isNotNull();
            Assertions.assertThat(testPerm.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val perm = getService().getOne(QueryWrapper.create()
                .orderBy(PERM.ID.desc()));
        Assertions.assertThat(perm).isNotNull();
        Assertions.assertThat(perm.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO(new JSONObject()
                                .set("id", perm.getId())
                                .set("name", PermUtil.UPDATED_NAME)
                                .set("code", PermUtil.UPDATED_CODE)
                                .set("desc", PermUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> {
            Assertions.assertThat(perms).hasSize(Convert.toInt(beforeSize + 1));
            val testPerm = CollUtil.getFirst(perms);
            Assertions.assertThat(testPerm).isNotNull();
            Assertions.assertThat(testPerm.getId()).isNotNull();
            Assertions.assertThat(testPerm.getName()).isEqualTo(PermUtil.UPDATED_NAME);
            Assertions.assertThat(testPerm.getCode()).isEqualTo(PermUtil.UPDATED_CODE);
            Assertions.assertThat(testPerm.getDesc()).isEqualTo(PermUtil.UPDATED_DESC);
            Assertions.assertThat(testPerm.getStatus()).isEqualTo(PermStatus.TYPE0);
            Assertions.assertThat(testPerm.getCreateTime()).isNotNull();
            Assertions.assertThat(testPerm.getUpdateTime()).isNotNull();
            Assertions.assertThat(testPerm.getVersion()).isNotNull();
            Assertions.assertThat(testPerm.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> Assertions.assertThat(perms)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/perm/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].name").value(hasItem(PermUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].code").value(hasItem(PermUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(PermUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.records.[*].status").value(hasItem(PermStatus.TYPE0.getValue())));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> Assertions.assertThat(perms)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/perm/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].name").value(hasItem(PermUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].code").value(hasItem(PermUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(PermUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.[*].status").value(hasItem(PermStatus.TYPE0.getValue())));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> Assertions.assertThat(perms)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/perm/_count")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(beforeSize + 1));
    }

    @Order(6)
    @Test
    void getById() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> Assertions.assertThat(perms)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val perm = getService().getOne(QueryWrapper.create()
                .orderBy(PERM.ID.desc()));
        Assertions.assertThat(perm).isNotNull();
        Assertions.assertThat(perm.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/perm/{id:.+}", perm.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(PermUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.code").value(PermUtil.DEFAULT_CODE))
                .andExpect(jsonPath("$.data.desc").value(PermUtil.DEFAULT_DESC))
                .andExpect(jsonPath("$.data.status").value(PermStatus.TYPE0.getValue()));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val perm = getService().getOne(QueryWrapper.create()
                .orderBy(PERM.ID.desc()));
        Assertions.assertThat(perm).isNotNull();
        Assertions.assertThat(perm.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/perm/{id:.+}", perm.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(perms -> Assertions.assertThat(perms)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<Perm>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(PERM.ID.desc())));
    }
}
