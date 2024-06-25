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
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.domain.TenantPropertiesData;
import org.huangyalong.modules.system.enums.TenantCategory;
import org.huangyalong.modules.system.enums.TenantStatus;
import org.huangyalong.modules.system.request.TenantUtil;
import org.huangyalong.modules.system.service.TenantPropertyService;
import org.huangyalong.modules.system.service.TenantService;
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

import static com.mybatisflex.core.query.QueryMethods.max;
import static com.mybatisflex.core.query.QueryMethods.select;
import static org.hamcrest.Matchers.hasItem;
import static org.huangyalong.modules.system.domain.table.TenantPropertyTableDef.TENANT_PROPERTY;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class TenantControllerTest extends AbstractControllerTest<TenantService, Tenant> {

    @Resource
    private MockMvc mvc;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private TenantPropertyService propertyService;

    @BeforeEach
    void initTest() {
        val id = 10000000000000000L;
        StpUtil.login(id);
    }

    @Order(1)
    @Test
    void add() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> {
            Assertions.assertThat(tenants).hasSize(Convert.toInt(beforeSize + 1));
            val testTenant = CollUtil.getFirst(tenants);
            Assertions.assertThat(testTenant).isNotNull();
            Assertions.assertThat(testTenant.getId()).isNotNull();
            Assertions.assertThat(testTenant.getName()).isEqualTo(TenantUtil.DEFAULT_NAME);
            Assertions.assertThat(testTenant.getCategory()).isEqualTo(TenantCategory.TYPE0);
            Assertions.assertThat(testTenant.getAddress()).isEqualTo(TenantUtil.DEFAULT_ADDRESS);
            Assertions.assertThat(testTenant.getDesc()).isEqualTo(TenantUtil.DEFAULT_DESC);
            Assertions.assertThat(testTenant.getStatus()).isEqualTo(TenantStatus.TYPE0);
            Assertions.assertThat(testTenant.getCreateTime()).isNotNull();
            Assertions.assertThat(testTenant.getUpdateTime()).isNotNull();
            Assertions.assertThat(testTenant.getVersion()).isNotNull();
            Assertions.assertThat(testTenant.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
            val testProperties = propertyService.list(QueryWrapper.create()
                    .select(TENANT_PROPERTY.ALL_COLUMNS)
                    .from(TENANT_PROPERTY)
                    .where(TENANT_PROPERTY.ID.in(select(max(TENANT_PROPERTY.ID))
                            .from(TENANT_PROPERTY)
                            .where(TENANT_PROPERTY.TENANT_ID.eq(testTenant.getId()))
                            .and(TENANT_PROPERTY.GROUP.in(TenantPropertiesData.GROUP_TENANT))
                            .groupBy(TENANT_PROPERTY.GROUP,
                                    TENANT_PROPERTY.NAME))));
            Assertions.assertThat(testProperties).isNotNull();
            Assertions.assertThat(testProperties).hasSize(2);
            val testAbbr = CollUtil.findOneByField(testProperties, "name", TenantPropertiesData.NAME_ABBR);
            Assertions.assertThat(testAbbr.getId()).isNotNull();
            Assertions.assertThat(testAbbr.getTenantId()).isEqualTo(testTenant.getId());
            Assertions.assertThat(testAbbr.getGroup()).isEqualTo(TenantPropertiesData.GROUP_TENANT);
            Assertions.assertThat(testAbbr.getName()).isEqualTo(TenantPropertiesData.NAME_ABBR);
            Assertions.assertThat(testAbbr.getData()).isEqualTo(TenantUtil.DEFAULT_ABBR);
            Assertions.assertThat(testAbbr.getCreateTime()).isNotNull();
            Assertions.assertThat(testAbbr.getUpdateTime()).isNotNull();
            val testArea = CollUtil.findOneByField(testProperties, "name", TenantPropertiesData.NAME_AREA);
            Assertions.assertThat(testArea.getId()).isNotNull();
            Assertions.assertThat(testArea.getTenantId()).isEqualTo(testTenant.getId());
            Assertions.assertThat(testArea.getGroup()).isEqualTo(TenantPropertiesData.GROUP_TENANT);
            Assertions.assertThat(testArea.getName()).isEqualTo(TenantPropertiesData.NAME_AREA);
            Assertions.assertThat(testArea.getData()).isEqualTo(TenantUtil.DEFAULT_AREA);
            Assertions.assertThat(testArea.getCreateTime()).isNotNull();
            Assertions.assertThat(testArea.getUpdateTime()).isNotNull();
        });
    }

    @Order(2)
    @Test
    void update() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val tenant = getService().getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.put("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO(new JSONObject()
                                .set("id", tenant.getId())
                                .set("name", TenantUtil.UPDATED_NAME)
                                .set("abbr", TenantUtil.UPDATED_ABBR)
                                .set("category", TenantCategory.TYPE1.getValue())
                                .set("area", TenantUtil.UPDATED_AREA)
                                .set("address", TenantUtil.UPDATED_ADDRESS)
                                .set("desc", TenantUtil.UPDATED_DESC)))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> {
            Assertions.assertThat(tenants).hasSize(Convert.toInt(beforeSize + 1));
            val testTenant = CollUtil.getFirst(tenants);
            Assertions.assertThat(testTenant).isNotNull();
            Assertions.assertThat(testTenant.getId()).isNotNull();
            Assertions.assertThat(testTenant.getName()).isEqualTo(TenantUtil.UPDATED_NAME);
            Assertions.assertThat(testTenant.getCategory()).isEqualTo(TenantCategory.TYPE1);
            Assertions.assertThat(testTenant.getAddress()).isEqualTo(TenantUtil.UPDATED_ADDRESS);
            Assertions.assertThat(testTenant.getDesc()).isEqualTo(TenantUtil.UPDATED_DESC);
            Assertions.assertThat(testTenant.getStatus()).isEqualTo(TenantStatus.TYPE0);
            Assertions.assertThat(testTenant.getCreateTime()).isNotNull();
            Assertions.assertThat(testTenant.getUpdateTime()).isNotNull();
            Assertions.assertThat(testTenant.getVersion()).isNotNull();
            Assertions.assertThat(testTenant.getIsDeleted()).isEqualTo(IsDeleted.TYPE0);
            val testProperties = propertyService.list(QueryWrapper.create()
                    .select(TENANT_PROPERTY.ALL_COLUMNS)
                    .from(TENANT_PROPERTY)
                    .where(TENANT_PROPERTY.ID.in(select(max(TENANT_PROPERTY.ID))
                            .from(TENANT_PROPERTY)
                            .where(TENANT_PROPERTY.TENANT_ID.eq(testTenant.getId()))
                            .and(TENANT_PROPERTY.GROUP.in(TenantPropertiesData.GROUP_TENANT))
                            .groupBy(TENANT_PROPERTY.GROUP,
                                    TENANT_PROPERTY.NAME))));
            Assertions.assertThat(testProperties).isNotNull();
            Assertions.assertThat(testProperties).hasSize(2);
            val testAbbr = CollUtil.findOneByField(testProperties, "name", TenantPropertiesData.NAME_ABBR);
            Assertions.assertThat(testAbbr.getId()).isNotNull();
            Assertions.assertThat(testAbbr.getTenantId()).isEqualTo(testTenant.getId());
            Assertions.assertThat(testAbbr.getGroup()).isEqualTo(TenantPropertiesData.GROUP_TENANT);
            Assertions.assertThat(testAbbr.getName()).isEqualTo(TenantPropertiesData.NAME_ABBR);
            Assertions.assertThat(testAbbr.getData()).isEqualTo(TenantUtil.UPDATED_ABBR);
            Assertions.assertThat(testAbbr.getCreateTime()).isNotNull();
            Assertions.assertThat(testAbbr.getUpdateTime()).isNotNull();
            val testArea = CollUtil.findOneByField(testProperties, "name", TenantPropertiesData.NAME_AREA);
            Assertions.assertThat(testArea.getId()).isNotNull();
            Assertions.assertThat(testArea.getTenantId()).isEqualTo(testTenant.getId());
            Assertions.assertThat(testArea.getGroup()).isEqualTo(TenantPropertiesData.GROUP_TENANT);
            Assertions.assertThat(testArea.getName()).isEqualTo(TenantPropertiesData.NAME_AREA);
            Assertions.assertThat(testArea.getData()).isEqualTo(TenantUtil.UPDATED_AREA);
            Assertions.assertThat(testArea.getCreateTime()).isNotNull();
            Assertions.assertThat(testArea.getUpdateTime()).isNotNull();
        });
    }

    @Order(3)
    @Test
    void queryPage() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> Assertions.assertThat(tenants)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/_query/paging")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .param("pageSize", Convert.toStr(Long.MAX_VALUE))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records.[*].name").value(hasItem(TenantUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.records.[*].abbr").value(hasItem(TenantUtil.DEFAULT_ABBR)))
                .andExpect(jsonPath("$.data.records.[*].category").value(hasItem(TenantCategory.TYPE0.getValue())))
                .andExpect(jsonPath("$.data.records.[*].area").value(hasItem(TenantUtil.DEFAULT_AREA)))
                .andExpect(jsonPath("$.data.records.[*].address").value(hasItem(TenantUtil.DEFAULT_ADDRESS)))
                .andExpect(jsonPath("$.data.records.[*].desc").value(hasItem(TenantUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.records.[*].status").value(hasItem(TenantStatus.TYPE0.getValue())));
    }

    @Order(4)
    @Test
    void query() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> Assertions.assertThat(tenants)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/_query")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.[*].name").value(hasItem(TenantUtil.DEFAULT_NAME)))
                .andExpect(jsonPath("$.data.[*].abbr").value(hasItem(TenantUtil.DEFAULT_ABBR)))
                .andExpect(jsonPath("$.data.[*].category").value(hasItem(TenantCategory.TYPE0.getValue())))
                .andExpect(jsonPath("$.data.[*].area").value(hasItem(TenantUtil.DEFAULT_AREA)))
                .andExpect(jsonPath("$.data.[*].address").value(hasItem(TenantUtil.DEFAULT_ADDRESS)))
                .andExpect(jsonPath("$.data.[*].desc").value(hasItem(TenantUtil.DEFAULT_DESC)))
                .andExpect(jsonPath("$.data.[*].status").value(hasItem(TenantStatus.TYPE0.getValue())));
    }

    @Order(5)
    @Test
    void count() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> Assertions.assertThat(tenants)
                .hasSize(Convert.toInt(beforeSize + 1)));
        mvc.perform(MockMvcRequestBuilders.get("/tenant/_count")
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
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> Assertions.assertThat(tenants)
                .hasSize(Convert.toInt(beforeSize + 1)));
        val tenant = getService().getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.get("/tenant/{id:.+}", tenant.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(TenantUtil.DEFAULT_NAME))
                .andExpect(jsonPath("$.data.abbr").value(TenantUtil.DEFAULT_ABBR))
                .andExpect(jsonPath("$.data.category").value(TenantCategory.TYPE0.getValue()))
                .andExpect(jsonPath("$.data.area").value(TenantUtil.DEFAULT_AREA))
                .andExpect(jsonPath("$.data.address").value(TenantUtil.DEFAULT_ADDRESS))
                .andExpect(jsonPath("$.data.desc").value(TenantUtil.DEFAULT_DESC))
                .andExpect(jsonPath("$.data.status").value(TenantStatus.TYPE0.getValue()));
    }

    @Order(7)
    @Test
    void delete() throws Exception {
        val beforeSize = Convert.toInt(getService().count());
        mvc.perform(MockMvcRequestBuilders.post("/tenant")
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(TenantUtil.createBO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        val tenant = getService().getOne(QueryWrapper.create()
                .orderBy(TENANT.ID.desc()));
        Assertions.assertThat(tenant).isNotNull();
        Assertions.assertThat(tenant.getId()).isNotNull();
        mvc.perform(MockMvcRequestBuilders.delete("/tenant/{id:.+}", tenant.getId())
                        .header(StpUtil.getTokenName(), StpUtil.getTokenValue())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true));
        assertPersisted(tenants -> Assertions.assertThat(tenants)
                .hasSize(Convert.toInt(beforeSize)));
    }

    void assertPersisted(Consumer<List<Tenant>> assertion) {
        val mapper = getService().getMapper();
        assertion.accept(mapper.selectListByQuery(QueryWrapper.create()
                .orderBy(TENANT.ID.desc())));
    }
}
