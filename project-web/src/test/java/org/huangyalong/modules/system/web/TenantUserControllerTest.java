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
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.RoleUtil;
import org.huangyalong.modules.system.request.TenantUserUtil;
import org.huangyalong.modules.system.request.TenantUtil;
import org.huangyalong.modules.system.request.UserUtil;
import org.huangyalong.modules.system.response.TenantUserVO;
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
import static org.huangyalong.modules.system.domain.table.RoleAssocTableDef.ROLE_ASSOC;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;
import static org.huangyalong.modules.system.domain.table.TenantAssocTableDef.TENANT_ASSOC;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class TenantUserControllerTest extends AbstractControllerTest<TenantUserService, Tenant> {

    @Resource
    private MockMvc mvc;

    @Resource
    private TenantAssocService tenantAssocService;

    @Resource
    private RoleAssocService roleAssocService;

    @Resource
    private AccountService accountService;

    @Resource
    private TenantService tenantService;

    @Resource
    private RoleService roleService;

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
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(UserUtil.createBO())))
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
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantUserVOS -> {
            Assertions.assertThat(tenantUserVOS).hasSize(Convert.toInt(beforeSize + 1));
            val testTenantUserVO = CollUtil.getFirst(tenantUserVOS);
            Assertions.assertThat(testTenantUserVO).isNotNull();
            Assertions.assertThat(testTenantUserVO.getId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getAccountId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getTenantId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getRoleId()).isNotNull();
            val testTenantAssoc = tenantAssocService.getById(testTenantUserVO.getId());
            Assertions.assertThat(testTenantAssoc).isNotNull();
            Assertions.assertThat(testTenantAssoc.getId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getTenantId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testTenantAssoc.getAssocId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testTenantAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testTenantAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testTenantAssoc.getDesc()).isEqualTo(TenantUserUtil.DEFAULT_DESC);
            Assertions.assertThat(testTenantAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getUpdateTime()).isNotNull();
            val testAccount = accountService.getById(testTenantUserVO.getAccountId());
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            Assertions.assertThat(testAccount.getTenantId()).isNotNull();
            val testTenant = tenantService.getById(testTenantUserVO.getTenantId());
            Assertions.assertThat(testTenant).isNotNull();
            Assertions.assertThat(testTenant.getId()).isNotNull();
            Assertions.assertThat(testTenant.getName()).isEqualTo(TenantUtil.DEFAULT_NAME);
            val testRole = roleService.getById(testTenantUserVO.getRoleId());
            Assertions.assertThat(testRole).isNotNull();
            Assertions.assertThat(testRole.getId()).isNotNull();
            Assertions.assertThat(testRole.getName()).isEqualTo(RoleUtil.DEFAULT_NAME);
            val testRoleAssoc = roleAssocService.getOne(QueryWrapper.create()
                    .orderBy(ROLE_ASSOC.ID.desc()));
            Assertions.assertThat(testRoleAssoc).isNotNull();
            Assertions.assertThat(testRoleAssoc.getId()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getTenantId()).isEqualTo(testTenantAssoc.getTenantId());
            Assertions.assertThat(testRoleAssoc.getRoleId()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testRoleAssoc.getAssocId()).isEqualTo(testTenantAssoc.getAssocId());
            Assertions.assertThat(testRoleAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testRoleAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testRoleAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testRoleAssoc.getDesc()).isEqualTo(TenantUserUtil.DEFAULT_DESC);
            Assertions.assertThat(testRoleAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getUpdateTime()).isNotNull();
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
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val tenantAssoc = tenantAssocService.getOne(QueryWrapper.create()
                .orderBy(TENANT_ASSOC.ID.desc()));
        Assertions.assertThat(tenantAssoc).isNotNull();
        Assertions.assertThat(tenantAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("id", tenantAssoc.getId())
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())
                                .set("desc", TenantUserUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantUserVOS -> {
            Assertions.assertThat(tenantUserVOS).hasSize(Convert.toInt(beforeSize + 1));
            val testTenantUserVO = CollUtil.getFirst(tenantUserVOS);
            Assertions.assertThat(testTenantUserVO).isNotNull();
            Assertions.assertThat(testTenantUserVO.getId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getAccountId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getTenantId()).isNotNull();
            Assertions.assertThat(testTenantUserVO.getRoleId()).isNotNull();
            val testTenantAssoc = tenantAssocService.getById(testTenantUserVO.getId());
            Assertions.assertThat(testTenantAssoc).isNotNull();
            Assertions.assertThat(testTenantAssoc.getId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getTenantId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testTenantAssoc.getAssocId()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testTenantAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testTenantAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testTenantAssoc.getDesc()).isEqualTo(TenantUserUtil.UPDATED_DESC);
            Assertions.assertThat(testTenantAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testTenantAssoc.getUpdateTime()).isNotNull();
            val testAccount = accountService.getById(testTenantUserVO.getAccountId());
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            Assertions.assertThat(testAccount.getTenantId()).isNotNull();
            val testTenant = tenantService.getById(testTenantUserVO.getTenantId());
            Assertions.assertThat(testTenant).isNotNull();
            Assertions.assertThat(testTenant.getId()).isNotNull();
            Assertions.assertThat(testTenant.getName()).isEqualTo(TenantUtil.DEFAULT_NAME);
            val testRole = roleService.getById(testTenantUserVO.getRoleId());
            Assertions.assertThat(testRole).isNotNull();
            Assertions.assertThat(testRole.getId()).isNotNull();
            Assertions.assertThat(testRole.getName()).isEqualTo(RoleUtil.DEFAULT_NAME);
            val testRoleAssoc = roleAssocService.getOne(QueryWrapper.create()
                    .orderBy(ROLE_ASSOC.ID.desc()));
            Assertions.assertThat(testRoleAssoc).isNotNull();
            Assertions.assertThat(testRoleAssoc.getId()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getTenantId()).isEqualTo(testTenantAssoc.getTenantId());
            Assertions.assertThat(testRoleAssoc.getRoleId()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getAssoc()).isEqualTo(ACCOUNT.getTableName());
            Assertions.assertThat(testRoleAssoc.getAssocId()).isEqualTo(testTenantAssoc.getAssocId());
            Assertions.assertThat(testRoleAssoc.getEffective()).isEqualTo(TimeEffective.TYPE0);
            Assertions.assertThat(testRoleAssoc.getEffectiveTime()).isNull();
            Assertions.assertThat(testRoleAssoc.getCategory()).isEqualTo(AssocCategory.TYPE0);
            Assertions.assertThat(testRoleAssoc.getDesc()).isEqualTo(TenantUserUtil.UPDATED_DESC);
            Assertions.assertThat(testRoleAssoc.getCreateTime()).isNotNull();
            Assertions.assertThat(testRoleAssoc.getUpdateTime()).isNotNull();
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
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantAssocs -> Assertions.assertThat(tenantAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/user/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .param("tenantId", Convert.toStr(tenant.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.records.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.records.[*].mobile").value(hasItem(UserUtil.DEFAULT_MOBILE)))
                .andExpect(jsonPath("$.data.records.[*].tenantName").value(hasItem(TenantUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].roleName").value(hasItem(RoleUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(TenantUserUtil.DEFAULT_DESC)));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantAssocs -> Assertions.assertThat(tenantAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/user/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.[*].mobile").value(hasItem(UserUtil.DEFAULT_MOBILE)))
                .andExpect(jsonPath("$.data.[*].tenantName").value(hasItem(TenantUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].roleName").value(hasItem(RoleUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(TenantUserUtil.DEFAULT_DESC)));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantAssocs -> Assertions.assertThat(tenantAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/user/_count")
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
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantAssocs -> Assertions.assertThat(tenantAssocs)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val tenantAssoc = tenantAssocService.getOne(QueryWrapper.create()
                .orderBy(TENANT_ASSOC.ID.desc()));
        Assertions.assertThat(tenantAssoc).isNotNull();
        Assertions.assertThat(tenantAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/tenant/user/{id:.+}", tenantAssoc.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(UserUtil.DEFAULT_USERNAME))
                .andExpect(jsonPath("$.data.nickname").value(UserUtil.DEFAULT_NICKNAME))
                .andExpect(jsonPath("$.data.mobile").value(UserUtil.DEFAULT_MOBILE))
                .andExpect(jsonPath("$.data.tenantName").value(TenantUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.roleName").value(RoleUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.desc").value(TenantUserUtil.DEFAULT_DESC));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().countVO());
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/tenant/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(TenantUserUtil.createBO(new JSONObject()
                                .set("accountId", account.getId())
                                .set("tenantId", tenant.getId())
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val tenantAssoc = tenantAssocService.getOne(QueryWrapper.create()
                .orderBy(TENANT_ASSOC.ID.desc()));
        Assertions.assertThat(tenantAssoc).isNotNull();
        Assertions.assertThat(tenantAssoc.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/tenant/user/{id:.+}", tenantAssoc.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenantAssocs -> Assertions.assertThat(tenantAssocs)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<TenantUserVO>> assertion) {
        val tenantUserVOS = getService().listVO();
        assertion.accept(tenantUserVOS);
    }
}
