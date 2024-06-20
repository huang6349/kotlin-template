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
public class QueriesGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/queries.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val sourceDir = packageConfig.getSourceDir();
        val queriesPackage = packageConfig.getBasePackage().concat(".request");
        val queriesClassName = table.buildEntityClassName().concat("Queries");
        val queriesPackagePath = queriesPackage.replace(".", "/");
        val queriesJavaFile = new File(sourceDir, queriesPackagePath + "/" + queriesClassName + ".java");
        if (queriesJavaFile.exists()) return;
        val params = new HashMap<String, Object>(3);
        params.put("queriesPackage", queriesPackage);
        params.put("queriesClassName", queriesClassName);
        params.put("queriesComment", table.getComment().replace("信息", "查询"));
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, queriesJavaFile);
        System.out.println("Queries ---> " + queriesJavaFile);
    }
}
