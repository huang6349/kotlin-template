package org.huangyalong.modules.system.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.IntegrationTest;
import org.huangyalong.core.web.AbstractControllerTest;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.LoginQueries;
import org.huangyalong.modules.system.request.UserUtil;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.web.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class UserJWTControllerTest extends AbstractControllerTest<AccountService, Account> {

    @Resource
    private MockMvc mvc;

    @BeforeEach
    void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Test
    void authorizeByUsername() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val queries = new LoginQueries();
        queries.setUsername(UserUtil.DEFAULT_USERNAME);
        queries.setPassword(UserUtil.DEFAULT_PASSWORD);
        mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(queries)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void authorizeByMobile() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val queries = new LoginQueries();
        queries.setUsername(UserUtil.DEFAULT_MOBILE);
        queries.setPassword(UserUtil.DEFAULT_PASSWORD);
        mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(queries)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void authorizeByEmail() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val queries = new LoginQueries();
        queries.setUsername(UserUtil.DEFAULT_EMAIL);
        queries.setPassword(UserUtil.DEFAULT_PASSWORD);
        mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(queries)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    void assertPersisted(Consumer<List<Account>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc())));
    }
}
