package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.request.*;
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

import static org.hamcrest.Matchers.hasItem;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.PermTableDef.PERM;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class AccountPermControllerTest extends AbstractControllerTest<AccountPermService, Perm> {

    @Resource
    private MockMvc mvc;

    @Resource
    private AccountService accountService;

    @Resource
    private TenantService tenantService;

    @Resource
    private PermService permService;

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
        val tenant = tenantService.getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val perm = permService.getOne(QueryWrapper.create()
                .orderBy(PERM.ID.desc()));
        Assertions.assertThat(perm).isNotNull();
        Assertions.assertThat(perm.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/role")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(RoleUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val role = roleService.getOne(QueryWrapper.create()
                .orderBy(ROLE.ID.desc()));
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.post("/role/perm")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(PermAssocUtil.createBO(new JSONObject()
                                .set("assocId", role.getId())
                                .set("permId", perm.getId())))))
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
                                .set("roleId", role.getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        StpUtil.login(account.getId());
    }

    @Order(1)
    @Test
    void query() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/account/perm/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*]").value(hasItem(PermUtil.DEFAULT_CODE)));
    }
}
