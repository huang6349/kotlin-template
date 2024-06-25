package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.enums.IsDeleted;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.enums.RoleStatus;
import org.huangyalong.modules.system.request.RoleUtil;
import org.huangyalong.modules.system.service.RoleService;
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
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class RoleControllerTest extends AbstractControllerTest<RoleService, Role> {

    @Resource
    private MockMvc mvc;

    @Resource
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> {
            Assertions.assertThat(roles).hasSize(Convert.toInt(beforeSize + 1));
            val testRole = CollUtil.getFirst(roles);
            Assertions.assertThat(testRole).isNotNull();
            Assertions.assertThat(testRole.getId()).isNotNull();
            Assertions.assertThat(testRole.getName()).isEqualTo(RoleUtil.DEFAULT_NAME);
            Assertions.assertThat(testRole.getCode()).isEqualTo(RoleUtil.DEFAULT_CODE);
            Assertions.assertThat(testRole.getDesc()).isEqualTo(RoleUtil.DEFAULT_DESC);
            Assertions.assertThat(testRole.getStatus()).isEqualTo(RoleStatus.TYPE0);
            Assertions.assertThat(testRole.getCreateTime()).isNotNull();
            Assertions.assertThat(testRole.getUpdateTime()).isNotNull();
            Assertions.assertThat(testRole.getVersion()).isNotNull();
            Assertions.assertThat(testRole.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val role = getService().getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO(new JSONObject()
                                .set("id", role.getId())
                                .set("name", RoleUtil.UPDATED_NAME)
                                .set("code", RoleUtil.UPDATED_CODE)
                                .set("desc", RoleUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> {
            Assertions.assertThat(roles).hasSize(Convert.toInt(beforeSize + 1));
            val testRole = CollUtil.getFirst(roles);
            Assertions.assertThat(testRole).isNotNull();
            Assertions.assertThat(testRole.getId()).isNotNull();
            Assertions.assertThat(testRole.getName()).isEqualTo(RoleUtil.UPDATED_NAME);
            Assertions.assertThat(testRole.getCode()).isEqualTo(RoleUtil.UPDATED_CODE);
            Assertions.assertThat(testRole.getDesc()).isEqualTo(RoleUtil.UPDATED_DESC);
            Assertions.assertThat(testRole.getStatus()).isEqualTo(RoleStatus.TYPE0);
            Assertions.assertThat(testRole.getCreateTime()).isNotNull();
            Assertions.assertThat(testRole.getUpdateTime()).isNotNull();
            Assertions.assertThat(testRole.getVersion()).isNotNull();
            Assertions.assertThat(testRole.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> Assertions.assertThat(roles)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/role/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].name").value(hasItem(RoleUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].code").value(hasItem(RoleUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(RoleUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.records.[*].status").value(hasItem(RoleStatus.TYPE0.getValue())));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> Assertions.assertThat(roles)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/role/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].name").value(hasItem(RoleUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].code").value(hasItem(RoleUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(RoleUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.[*].status").value(hasItem(RoleStatus.TYPE0.getValue())));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> Assertions.assertThat(roles)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/role/_count")
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
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> Assertions.assertThat(roles)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val role = getService().getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/role/{id:.+}", role.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(RoleUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.code").value(RoleUtil.DEFAULT_CODE))
                .andExpect(jsonPath("$.data.desc").value(RoleUtil.DEFAULT_DESC))
                .andExpect(jsonPath("$.data.status").value(RoleStatus.TYPE0.getValue()));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val role = getService().getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/role/{id:.+}", role.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(roles -> Assertions.assertThat(roles)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<Role>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(ROLE.ID.desc())));
    }
}
