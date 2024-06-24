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
public class BOUtilGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/boUtil.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val tableDefConfig = globalConfig.getTableDefConfig();
        val javadocConfig = globalConfig.getJavadocConfig();
        val sourceDir = StrUtil.replaceFirst(packageConfig.getSourceDir(), "main", "test");
        val boUtilPackage = packageConfig.getBasePackage().concat(".request");
        val boUtilClassName = table.buildEntityClassName().concat("Util");
        val boUtilPackagePath = boUtilPackage.replace(".", "/");
        val boUtilJavaFile = new File(sourceDir, boUtilPackagePath + "/" + boUtilClassName + ".java");
        if (boUtilJavaFile.exists()) return;
        val params = new HashMap<String, Object>();
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("tableDefConfig", tableDefConfig);
        params.put("javadocConfig", javadocConfig);
        params.put("boUtilPackage", boUtilPackage);
        params.put("boUtilClassName", boUtilClassName);
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, boUtilJavaFile);
        System.out.println("BOUtil ---> " + boUtilJavaFile);
    }
}
