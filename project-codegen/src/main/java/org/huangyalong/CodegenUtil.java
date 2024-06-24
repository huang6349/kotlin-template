package org.huangyalong;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.val;

import java.util.Set;

public class CodegenUtil {

    public static void generate(String basePackage, Set<String> generateTables) {

        // 注册生成器
        GeneratorFactory.registerGenerator("queries", new QueriesGenerator());
        GeneratorFactory.registerGenerator("queriesUtil", new QueriesUtilGenerator());
        GeneratorFactory.registerGenerator("bo", new BOGenerator());
        GeneratorFactory.registerGenerator("controllerTest", new ControllerTestGenerator());

        // 配置数据源
        val dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://100.100.100.252:3308/project?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("pwd123456");

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
