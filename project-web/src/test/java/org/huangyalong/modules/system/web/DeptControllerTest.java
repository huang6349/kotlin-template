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
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.enums.DeptStatus;
import org.huangyalong.modules.system.request.*;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.modules.system.service.DeptService;
import org.huangyalong.modules.system.service.TenantService;
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
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.DeptTableDef.DEPT;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class DeptControllerTest extends AbstractControllerTest<DeptService, Dept> {

    @Resource
    private MockMvc mvc;

    @Resource
    private AccountService accountService;

    @Resource
    private TenantService tenantService;

    @BeforeEach
    void initTest() throws Exception {
        val id = 10000000000000000L;
        StpUtil.login(id);
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", 10000000000000000L)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        StpUtil.login(account.getId());
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> {
            Assertions.assertThat(depts).hasSize(Convert.toInt(beforeSize + 1));
            val testDept = CollUtil.getFirst(depts);
            Assertions.assertThat(testDept).isNotNull();
            Assertions.assertThat(testDept.getId()).isNotNull();
            Assertions.assertThat(testDept.getTenantId()).isNotNull();
            Assertions.assertThat(testDept.getName()).isEqualTo(DeptUtil.DEFAULT_NAME);
            Assertions.assertThat(testDept.getCode()).isEqualTo(DeptUtil.DEFAULT_CODE);
            Assertions.assertThat(testDept.getDesc()).isEqualTo(DeptUtil.DEFAULT_DESC);
            Assertions.assertThat(testDept.getCreateTime()).isNotNull();
            Assertions.assertThat(testDept.getUpdateTime()).isNotNull();
            Assertions.assertThat(testDept.getVersion()).isNotNull();
            Assertions.assertThat(testDept.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val dept = getService().getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO(new JSONObject()
                                .set("id", dept.getId())
                                .set("name", DeptUtil.UPDATED_NAME)
                                .set("code", DeptUtil.UPDATED_CODE)
                                .set("desc", DeptUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> {
            Assertions.assertThat(depts).hasSize(Convert.toInt(beforeSize + 1));
            val testDept = CollUtil.getFirst(depts);
            Assertions.assertThat(testDept).isNotNull();
            Assertions.assertThat(testDept.getId()).isNotNull();
            Assertions.assertThat(testDept.getTenantId()).isNotNull();
            Assertions.assertThat(testDept.getName()).isEqualTo(DeptUtil.UPDATED_NAME);
            Assertions.assertThat(testDept.getCode()).isEqualTo(DeptUtil.UPDATED_CODE);
            Assertions.assertThat(testDept.getDesc()).isEqualTo(DeptUtil.UPDATED_DESC);
            Assertions.assertThat(testDept.getCreateTime()).isNotNull();
            Assertions.assertThat(testDept.getUpdateTime()).isNotNull();
            Assertions.assertThat(testDept.getVersion()).isNotNull();
            Assertions.assertThat(testDept.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> Assertions.assertThat(depts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].name").value(hasItem(DeptUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].code").value(hasItem(DeptUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(DeptUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.records.[*].status").value(hasItem(DeptStatus.TYPE0.getValue())));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> Assertions.assertThat(depts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].name").value(hasItem(DeptUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].code").value(hasItem(DeptUtil.DEFAULT_CODE)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(DeptUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.[*].status").value(hasItem(DeptStatus.TYPE0.getValue())));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> Assertions.assertThat(depts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/_count")
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
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> Assertions.assertThat(depts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val dept = getService().getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/dept/{id:.+}", dept.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(DeptUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.code").value(DeptUtil.DEFAULT_CODE))
                .andExpect(jsonPath("$.data.desc").value(DeptUtil.DEFAULT_DESC))
                .andExpect(jsonPath("$.data.status").value(DeptStatus.TYPE0.getValue()));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val dept = getService().getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/dept/{id:.+}", dept.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(depts -> Assertions.assertThat(depts)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<Dept>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(DEPT.ID.desc())));
    }
}
