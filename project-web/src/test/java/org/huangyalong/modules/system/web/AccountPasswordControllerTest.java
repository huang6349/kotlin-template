package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.AccountPasswordUtil;
import org.huangyalong.modules.system.request.UserUtil;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.web.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class AccountPasswordControllerTest extends AbstractControllerTest<AccountService, Account> {

    @Resource
    private MockMvc mvc;

    @Resource
    private AccountService accountService;

    @BeforeEach
    void initTest() throws Exception {
        val id = 10000000000000000L;
        StpUtil.login(id);
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
    void update() throws Exception {
        val account = accountService.getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        StpUtil.login(account.getId());
        mvc.perform(MockMvcRequestBuilders.put("/account/password")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(AccountPasswordUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }
}
