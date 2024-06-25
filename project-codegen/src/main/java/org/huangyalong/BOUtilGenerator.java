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
public class BOUtilGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/boUtil.tpl";

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        val packageConfig = globalConfig.getPackageConfig();
        val sourceDir = StrUtil.replaceFirst(packageConfig.getSourceDir(), "main", "test");
        val packageName = packageConfig.getBasePackage().concat(".request");
        val className = table.buildEntityClassName().concat("Util");
        val logName = "BOUtil";
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
