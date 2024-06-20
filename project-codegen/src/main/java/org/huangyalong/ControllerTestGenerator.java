package org.huangyalong;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;
import lombok.*;

import java.io.File;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ControllerTestGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/controllerTest.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val tableDefConfig = globalConfig.getTableDefConfig();
        val javadocConfig = globalConfig.getJavadocConfig();
        val sourceDir = StrUtil.replaceFirst(packageConfig.getSourceDir(), "main", "test");
        val controllerPackage = packageConfig.getControllerPackage();
        val controllerClassName = table.buildControllerClassName().concat("Test");
        val controllerPackagePath = controllerPackage.replace(".", "/");
        val controllerJavaFile = new File(sourceDir, controllerPackagePath + "/" + controllerClassName + ".java");
        if (controllerJavaFile.exists()) return;
        val params = new HashMap<String, Object>(4);
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("tableDefConfig", tableDefConfig);
        params.put("javadocConfig", javadocConfig);
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, controllerJavaFile);
        System.out.println("ControllerTest ---> " + controllerJavaFile);
    }
}
