package org.huangyalong.generator;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Table;
import lombok.val;

import java.io.File;
import java.util.HashMap;

public class GeneratorUtil {

    public static void generate(Table table,
                                PackageConfig packageConfig,
                                GlobalConfig globalConfig,
                                String sourceDir,
                                String templatePath,
                                String packageName,
                                String className,
                                String logName,
                                String comment) {
        val tableDefConfig = globalConfig.getTableDefConfig();
        val javadocConfig = globalConfig.getJavadocConfig();
        val packagePath = packageName.replace(".", "/");
        val javaFile = new File(sourceDir, packagePath + "/" + className + ".java");
        if (javaFile.exists()) return;
        val params = new HashMap<String, Object>(7);
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("tableDefConfig", tableDefConfig);
        params.put("javadocConfig", javadocConfig);
        params.put("packageName", packageName);
        params.put("className", className);
        params.put("comment", comment);
        val template = globalConfig.getTemplateConfig().getTemplate();
        template.generate(params, templatePath, javaFile);
        System.out.println(logName + " ---> " + javaFile);
    }
}
