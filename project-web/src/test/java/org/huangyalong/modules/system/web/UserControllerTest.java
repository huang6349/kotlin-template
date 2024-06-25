package org.huangyalong.modules.system.web;

import cn.dev33.satoken.secure.BCrypt;
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
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.enums.AccountStatus;
import org.huangyalong.modules.system.enums.UserGender;
import org.huangyalong.modules.system.request.UserUtil;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.modules.system.service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class UserControllerTest extends AbstractControllerTest<AccountService, Account> {

    @Resource
    private MockMvc mvc;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private UserService userService;

    @BeforeEach
    void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> {
            Assertions.assertThat(accounts).hasSize(Convert.toInt(beforeSize + 1));
            val testAccount = CollUtil.getFirst(accounts);
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            Assertions.assertThat(BCrypt.checkpw(UserUtil.DEFAULT_PASSWORD, testAccount.getPassword())).isTrue();
            Assertions.assertThat(testAccount.getMobile()).isEqualTo(UserUtil.DEFAULT_MOBILE);
            Assertions.assertThat(testAccount.getEmail()).isEqualTo(UserUtil.DEFAULT_EMAIL);
            Assertions.assertThat(testAccount.getStatus()).isEqualTo(AccountStatus.TYPE0);
            Assertions.assertThat(testAccount.getTenantId()).isNull();
            Assertions.assertThat(testAccount.getCreateTime()).isNotNull();
            Assertions.assertThat(testAccount.getUpdateTime()).isNotNull();
            Assertions.assertThat(testAccount.getVersion()).isNotNull();
            Assertions.assertThat(testAccount.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
            val testUser = userService.getByAccountId(testAccount.getId());
            Assertions.assertThat(testUser).isNotNull();
            Assertions.assertThat(testUser.getId()).isNotNull();
            Assertions.assertThat(testUser.getNickname()).isEqualTo(UserUtil.DEFAULT_NICKNAME);
            Assertions.assertThat(testUser.getGender()).isEqualTo(UserGender.TYPE0);
            Assertions.assertThat(testUser.getCreateTime()).isNotNull();
            Assertions.assertThat(testUser.getUpdateTime()).isNotNull();
            Assertions.assertThat(testUser.getVersion()).isNotNull();
            Assertions.assertThat(testUser.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val account = getService().getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO(new JSONObject()
                                .set("id", account.getId())
                                .set("password", UserUtil.UPDATED_PASSWORD)
                                .set("nickname", UserUtil.UPDATED_NICKNAME)
                                .set("mobile", UserUtil.UPDATED_MOBILE)
                                .set("email", UserUtil.UPDATED_EMAIL)
                                .set("gender", UserGender.TYPE1.getValue())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> {
            Assertions.assertThat(accounts).hasSize(Convert.toInt(beforeSize + 1));
            val testAccount = CollUtil.getFirst(accounts);
            Assertions.assertThat(testAccount).isNotNull();
            Assertions.assertThat(testAccount.getId()).isNotNull();
            Assertions.assertThat(testAccount.getUsername()).isEqualTo(UserUtil.DEFAULT_USERNAME);
            Assertions.assertThat(BCrypt.checkpw(UserUtil.UPDATED_PASSWORD, testAccount.getPassword())).isTrue();
            Assertions.assertThat(testAccount.getMobile()).isEqualTo(UserUtil.UPDATED_MOBILE);
            Assertions.assertThat(testAccount.getEmail()).isEqualTo(UserUtil.UPDATED_EMAIL);
            Assertions.assertThat(testAccount.getStatus()).isEqualTo(AccountStatus.TYPE0);
            Assertions.assertThat(testAccount.getTenantId()).isNull();
            Assertions.assertThat(testAccount.getCreateTime()).isNotNull();
            Assertions.assertThat(testAccount.getUpdateTime()).isNotNull();
            Assertions.assertThat(testAccount.getVersion()).isNotNull();
            Assertions.assertThat(testAccount.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
            val testUser = userService.getByAccountId(testAccount.getId());
            Assertions.assertThat(testUser).isNotNull();
            Assertions.assertThat(testUser.getId()).isNotNull();
            Assertions.assertThat(testUser.getNickname()).isEqualTo(UserUtil.UPDATED_NICKNAME);
            Assertions.assertThat(testUser.getGender()).isEqualTo(UserGender.TYPE1);
            Assertions.assertThat(testUser.getCreateTime()).isNotNull();
            Assertions.assertThat(testUser.getUpdateTime()).isNotNull();
            Assertions.assertThat(testUser.getVersion()).isNotNull();
            Assertions.assertThat(testUser.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/user/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.records.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.records.[*].gender").value(hasItem(UserGender.TYPE0.getValue())))
                .andExpect(jsonPath("$.data.records.[*].status").value(hasItem(AccountStatus.TYPE0.getValue())));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/user/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].username").value(hasItem(UserUtil.DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.data.[*].nickname").value(hasItem(UserUtil.DEFAULT_NICKNAME)))
                .andExpect(jsonPath("$.data.[*].gender").value(hasItem(UserGender.TYPE0.getValue())))
                .andExpect(jsonPath("$.data.[*].status").value(hasItem(AccountStatus.TYPE0.getValue())));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/user/_count")
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
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val account = getService().getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/user/{id:.+}", account.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(UserUtil.DEFAULT_USERNAME))
                .andExpect(jsonPath("$.data.nickname").value(UserUtil.DEFAULT_NICKNAME))
                .andExpect(jsonPath("$.data.gender").value(UserGender.TYPE0.getValue()))
                .andExpect(jsonPath("$.data.status").value(AccountStatus.TYPE0.getValue()));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val account = getService().getOne(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc()));
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/user/{id:.+}", account.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(accounts -> Assertions.assertThat(accounts)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<Account>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(ACCOUNT.ID.desc())));
    }
}
