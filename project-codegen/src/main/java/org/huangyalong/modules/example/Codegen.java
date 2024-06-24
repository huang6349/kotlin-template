package org.huangyalong.modules.example;

import cn.hutool.core.collection.CollUtil;
import org.huangyalong.CodegenUtil;

public class Codegen {

    public static void main(String[] args) {
        CodegenUtil.generate("org.huangyalong.modules.example",
                CollUtil.newHashSet("tb_example"));
    }
}
