#set(controllerPackage = packageConfig.controllerPackage)
#set(servicePackage = packageConfig.servicePackage)
#set(entityPackage = packageConfig.entityPackage)
#set(basePackage = packageConfig.basePackage)
#set(boPackage = basePackage.concat(".request"))
#set(tableDefClassName = table.buildTableDefClassName())
#set(controllerClassName = table.buildControllerClassName().concat("Test"))
#set(serviceClassName = table.buildServiceClassName())
#set(entityClassName = table.buildEntityClassName())
#set(boClassName = entityClassName.concat("BO"))
#set(tableDefVarName = tableDefConfig.buildFieldName(entityClassName + tableDefConfig.instanceSuffix))
#set(entitysVarName = firstCharToLowerCase(entityClassName).concat("s"))
#set(entityVarName = firstCharToLowerCase(entityClassName))
#set(boVarName = firstCharToLowerCase(boClassName))
package #(controllerPackage);

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.huangyalong.core.enums.IsDeleted;
import org.huangyalong.core.web.AbstractControllerTest;
import #(entityPackage).#(entityClassName);
import #(boPackage).#(boClassName);
import #(servicePackage).#(serviceClassName);
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

import static org.huangyalong.domain.table.#(tableDefClassName).#(tableDefVarName);
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DirtiesContext
@Transactional(propagation = Propagation.REQUIRED)
class #(controllerClassName) extends AbstractControllerTest<#(serviceClassName), #(entityClassName)> {

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
                        .content(TestUtil.convertObjectToJsonBytes(createBO())))
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
            Assertions.assertThat(test#(entityClassName).getIsDeleted()).isEqualTo(IsDeleted.UNDELETED);
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        getService().add(createBO());
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).CREATE_TIME.desc())
                .limit(1));
        Assertions.assertThat(#(entityVarName)).isNotNull();
        val id = #(entityVarName).getId();
        mvc.perform(MockMvcRequestBuilders.put("/#(entityVarName)")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(createUpdatedBO(id))))
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
            Assertions.assertThat(test#(entityClassName).getIsDeleted()).isEqualTo(IsDeleted.UNDELETED);
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        getService().add(createBO());
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
        getService().add(createBO());
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
        getService().add(createBO());
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
        getService().add(createBO());
        assertPersisted(#(entitysVarName) -> Assertions.assertThat(#(entitysVarName))
                .hasSize(Convert.toInt(beforeSize + 1)));
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).CREATE_TIME.desc())
                .limit(1));
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
        getService().add(createBO());
        val #(entityVarName) = getService().getOne(QueryWrapper.create()
                .orderBy(#(tableDefVarName).CREATE_TIME.desc())
                .limit(1));
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
                .orderBy(#(tableDefVarName).CREATE_TIME.desc())));
    }
}
