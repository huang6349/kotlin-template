#set(controllerPackage = packageConfig.controllerPackage)
#set(servicePackage = packageConfig.servicePackage)
#set(entityPackage = packageConfig.entityPackage)
#set(basePackage = packageConfig.basePackage)
#set(boPackage = basePackage.concat(".request"))
#set(boUtilPackage = basePackage.concat(".request"))
#set(tableDefClassName = table.buildTableDefClassName())
#set(controllerClassName = table.buildControllerClassName())
#set(serviceClassName = table.buildServiceClassName())
#set(entityClassName = table.buildEntityClassName())
#set(boClassName = entityClassName.concat("BO"))
#set(boUtilClassName = entityClassName.concat("Util"))
#set(tableDefVarName = tableDefConfig.buildFieldName(entityClassName + tableDefConfig.instanceSuffix))
#set(entitysVarName = firstCharToLowerCase(entityClassName).concat("s"))
#set(entityVarName = firstCharToLowerCase(entityClassName))
#set(boVarName = firstCharToLowerCase(boClassName))
package #(packageName);

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
import #(entityPackage).#(entityClassName);
import #(boUtilPackage).#(boUtilClassName);
import #(servicePackage).#(serviceClassName);
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

import static #(basePackage).domain.table.#(tableDefClassName).#(tableDefVarName);
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class #(className) extends AbstractControllerTest<#(serviceClassName), #(entityClassName)> {

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
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> {
            Assertions.assertThat(#(entitysVarName)).hasSize(Convert.toInt(beforeSize + 1));
            val test#(entityClassName) = CollUtil.getFirst(#(entitysVarName));
            Assertions.assertThat(test#(entityClassName)).isNotNull();
            Assertions.assertThat(test#(entityClassName).getId()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getCreateTime()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getUpdateTime()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getVersion()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).ID.desc()));
        Assertions.assertThat(#(entityVarName)).isNotNull();
        Assertions.assertThat(#(entityVarName).getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO(new JSONObject()
                                .set("id", #(entityVarName).getId())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> {
            Assertions.assertThat(#(entitysVarName)).hasSize(Convert.toInt(beforeSize + 1));
            val test#(entityClassName) = CollUtil.getFirst(#(entitysVarName));
            Assertions.assertThat(test#(entityClassName)).isNotNull();
            Assertions.assertThat(test#(entityClassName).getId()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getCreateTime()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getUpdateTime()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getVersion()).isNotNull();
            Assertions.assertThat(test#(entityClassName).getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/#(entityVarName)/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/#(entityVarName)/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/#(entityVarName)/_count")
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
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize + 1)));
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).ID.desc()));
        Assertions.assertThat(#(entityVarName)).isNotNull();
        Assertions.assertThat(#(entityVarName).getId()).isNotNull();
        val id = #(entityVarName).getId();
        mvc.perform(MockMvcRequestBuilders.get("/#(entityVarName)/{id:.+}", id)
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(#(boUtilClassName).createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).ID.desc()));
        Assertions.assertThat(#(entityVarName)).isNotNull();
        Assertions.assertThat(#(entityVarName).getId()).isNotNull();
        val id = #(entityVarName).getId();
        mvc.perform(MockMvcRequestBuilders.delete("/#(entityVarName)/{id:.+}", id)
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<#(entityClassName)>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(#(tableDefVarName).ID.desc())));
    }
}
