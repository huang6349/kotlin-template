package org.huangyalong;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;
import lombok.*;

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
        val packageName = packageConfig.getBasePackage().concat(".request");
        val className = table.buildEntityClassName().concat("BO");
        val logName = "BO";
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
