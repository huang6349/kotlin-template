package org.huangyalong;

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
public class QueriesUtilGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/queriesUtil.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val sourceDir = packageConfig.getSourceDir();
        val queriesUtilPackage = packageConfig.getBasePackage().concat(".request");
        val queriesClassName = table.buildEntityClassName().concat("Queries");
        val queriesUtilClassName = table.buildEntityClassName().concat("QueriesUtil");
        val queriesUtilPackagePath = queriesUtilPackage.replace(".", "/");
        val queriesJavaFile = new File(sourceDir, queriesUtilPackagePath + "/" + queriesUtilClassName + ".java");
        if (queriesJavaFile.exists()) return;
        val params = new HashMap<String, Object>(3);
        params.put("queriesUtilPackage", queriesUtilPackage);
        params.put("queriesClassName", queriesClassName);
        params.put("queriesUtilClassName", queriesUtilClassName);
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, queriesJavaFile);
        System.out.println("QueriesUtil ---> " + queriesJavaFile);
    }
}
