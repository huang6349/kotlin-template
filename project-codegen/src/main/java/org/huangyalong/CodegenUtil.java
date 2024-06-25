package org.huangyalong;

import cn.hutool.setting.dialect.Props;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.val;
import org.huangyalong.generator.*;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class CodegenUtil {

    public static void generate(String basePackage, Set<String> generateTables) {

        // 注册生成器
        GeneratorFactory.registerGenerator("queries", new QueriesGenerator());
        GeneratorFactory.registerGenerator("queriesUtil", new QueriesUtilGenerator());
        GeneratorFactory.registerGenerator("bo", new BOGenerator());
        GeneratorFactory.registerGenerator("boUtil", new BOUtilGenerator());
        GeneratorFactory.registerGenerator("controllerTest", new ControllerTestGenerator());

        // 配置数据源
        val dataSource = new HikariDataSource();
        val props = new AtomicReference<>(new Props("codegen.properties")).get();
        dataSource.setDriverClassName(props.getStr("driver-class-name"));
        dataSource.setJdbcUrl(props.getStr("url"));
        dataSource.setUsername(props.getStr("username"));
        dataSource.setPassword(props.getStr("password"));

        // 配置生成器
        val globalConfig = new GlobalConfig();
        globalConfig.getPackageConfig()
                .setSourceDir(System.getProperty("user.dir") + "/project-web/src/main/java")
                .setBasePackage(basePackage)
                .setEntityPackage(basePackage.concat(".domain"))
                .setServicePackage(basePackage.concat(".service"))
                .setServiceImplPackage(basePackage.concat(".service.impl"))
                .setControllerPackage(basePackage.concat(".web"));
        globalConfig.getStrategyConfig()
                .setTablePrefix("tb_")
                .setGenerateTables(generateTables);
        globalConfig.disableEntity();
        globalConfig.disableMapper();
        globalConfig.enableService()
                .setOverwriteEnable(Boolean.FALSE);
        globalConfig.enableServiceImpl()
                .setOverwriteEnable(Boolean.FALSE);
        globalConfig.enableController()
                .setOverwriteEnable(Boolean.FALSE);

        // 执行生成器
        val generator = new Generator(dataSource, globalConfig);
        generator.generate();
    }
}
