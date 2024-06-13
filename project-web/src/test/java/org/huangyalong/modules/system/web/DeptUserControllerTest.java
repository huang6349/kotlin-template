package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.request.*;
import org.huangyalong.modules.system.response.DeptUserVO;
import org.huangyalong.modules.system.service.*;
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
import static org.huangyalong.modules.system.domain.table.DeptAssocTableDef.DEPT_ASSOC;
import static org.huangyalong.modules.system.domain.table.DeptTableDef.DEPT;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class DeptUserControllerTest extends AbstractControllerTest<DeptUserService, Dept> {

    @Resource
    private MockMvc mvc;

    @Resource
    private DeptAssocService deptAssocService;

    @Resource
    private AccountService accountService;

    @Resource
    private TenantService tenantService;

    @Resource
    private DeptService deptService;

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
        mvc.perform(MockMvcRequestBuilders.post("/dept")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptUserVOS -> {
            Assertions.assertThat(deptUserVOS).hasSize(Convert.toInt(beforeSize + 1));
            val testDeptUserVO = CollUtil.getFirst(deptUserVOS);
            Assertions.assertThat(testDeptUserVO).isNotNull();
            Assertions.assertThat(testDeptUserVO.getId()).isNotNull();
            Assertions.assertThat(testDeptUserVO.getAccountId()).isNotNull();
            Assertions.assertThat(testDeptUserVO.getDeptId()).isNotNull();
            val testDeptAssoc = deptAssocService.getById(testDeptUserVO.getId());
            Assertions.assertThat(testDeptAssoc).isNotNull();
            Assertions.assertThat(testDeptAssoc.getId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getDeptId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testDeptAssoc.getAssocId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testDeptAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testDeptAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testDeptAssoc.getDesc()).isEqualTo(DeptUserUtil.DEFAULT_DESC);
            Assertions.assertThat(testDeptAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getUpdateTime()).isNotNull();
            val testAccount = accountService.getById(testDeptUserVO.getAccountId());
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            val testDept = deptService.getById(testDeptUserVO.getDeptId());
            Assertions.assertThat(testDept).isNotNull();
            Assertions.assertThat(testDept.getId()).isNotNull();
            Assertions.assertThat(testDept.getName()).isEqualTo(DeptUtil.DEFAULT_NAME);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val deptAssoc = deptAssocService.getOne(QueryWrapper.create()
                .orderBy(DEPT_ASSOC.ID.desc()));
        Assertions.assertThat(deptAssoc).isNotNull();
        Assertions.assertThat(deptAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("id", deptAssoc.getId())
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())
                                .set("desc", DeptUserUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptUserVOS -> {
            Assertions.assertThat(deptUserVOS).hasSize(Convert.toInt(beforeSize + 1));
            val testDeptUserVO = CollUtil.getFirst(deptUserVOS);
            Assertions.assertThat(testDeptUserVO).isNotNull();
            Assertions.assertThat(testDeptUserVO.getId()).isNotNull();
            Assertions.assertThat(testDeptUserVO.getAccountId()).isNotNull();
            Assertions.assertThat(testDeptUserVO.getDeptId()).isNotNull();
            val testDeptAssoc = deptAssocService.getById(testDeptUserVO.getId());
            Assertions.assertThat(testDeptAssoc).isNotNull();
            Assertions.assertThat(testDeptAssoc.getId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getDeptId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testDeptAssoc.getAssocId()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testDeptAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testDeptAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testDeptAssoc.getDesc()).isEqualTo(DeptUserUtil.UPDATED_DESC);
            Assertions.assertThat(testDeptAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testDeptAssoc.getUpdateTime()).isNotNull();
            val testAccount = accountService.getById(testDeptUserVO.getAccountId());
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            val testDept = deptService.getById(testDeptUserVO.getDeptId());
            Assertions.assertThat(testDept).isNotNull();
            Assertions.assertThat(testDept.getId()).isNotNull();
            Assertions.assertThat(testDept.getName()).isEqualTo(DeptUtil.DEFAULT_NAME);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptAssocs -> Assertions.assertThat(deptAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/user/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .param("deptId", Convert.toStr(dept.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.records.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.records.[*].mobile").value(hasItem(UserUtil.DEFAULT_MOBILE)))
                .andExpect(jsonPath("$.data.records.[*].deptName").value(hasItem(DeptUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(DeptUserUtil.DEFAULT_DESC)));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptAssocs -> Assertions.assertThat(deptAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/user/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.[*].mobile").value(hasItem(UserUtil.DEFAULT_MOBILE)))
                .andExpect(jsonPath("$.data.[*].deptName").value(hasItem(DeptUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(DeptUserUtil.DEFAULT_DESC)));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptAssocs -> Assertions.assertThat(deptAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/dept/user/_count")
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
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptAssocs -> Assertions.assertThat(deptAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val deptAssoc = deptAssocService.getOne(QueryWrapper.create()
                .orderBy(DEPT_ASSOC.ID.desc()));
        Assertions.assertThat(deptAssoc).isNotNull();
        Assertions.assertThat(deptAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/dept/user/{id:.+}", deptAssoc.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(UserUtil.DEFAULT_USERNAME))
                .andExpect(jsonPath("$.data.nickname").value(UserUtil.DEFAULT_NICKNAME))
                .andExpect(jsonPath("$.data.mobile").value(UserUtil.DEFAULT_MOBILE))
                .andExpect(jsonPath("$.data.deptName").value(DeptUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.desc").value(DeptUserUtil.DEFAULT_DESC));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val dept = deptService.getOne(QueryWrapper.create()
                .orderBy(DEPT.ID.desc()));
        Assertions.assertThat(dept).isNotNull();
        Assertions.assertThat(dept.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/dept/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(DeptUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("deptId", dept.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val deptAssoc = deptAssocService.getOne(QueryWrapper.create()
                .orderBy(DEPT_ASSOC.ID.desc()));
        Assertions.assertThat(deptAssoc).isNotNull();
        Assertions.assertThat(deptAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/dept/user/{id:.+}", deptAssoc.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(deptAssocs -> Assertions.assertThat(deptAssocs)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<DeptUserVO>> assertion) {
        val deptUserVOS = getService().listVO();
        assertion.accept(deptUserVOS);
    }
}
