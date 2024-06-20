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
public class BOGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/bo.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val sourceDir = packageConfig.getSourceDir();
        val boPackage = packageConfig.getBasePackage().concat(".request");
        val boClassName = table.buildEntityClassName().concat("BO");
        val boPackagePath = boPackage.replace(".", "/");
        val boJavaFile = new File(sourceDir, boPackagePath + "/" + boClassName + ".java");
        if (boJavaFile.exists()) return;
        val params = new HashMap<String, Object>(3);
        params.put("boPackage", boPackage);
        params.put("boClassName", boClassName);
        params.put("boComment", table.getComment());
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, boJavaFile);
        System.out.println("BO ---> " + boJavaFile);
    }
}
