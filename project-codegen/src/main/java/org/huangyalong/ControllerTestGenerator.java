package org.huangyalong;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ControllerTestGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/controllerTest.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val sourceDir = StrUtil.replaceFirst(packageConfig.getSourceDir(), "main", "test");
        val packageName = packageConfig.getControllerPackage();
        val className = table.buildControllerClassName().concat("Test");
        val logName = "ControllerTest";
        val comment = table.getComment();
        GeneratorUtil.generate(table,
                packageConfig,
                globalConfig,
                sourceDir,
                getTemplatePath(),
                packageName,
                className,
                logName,
                comment);
    }
}
