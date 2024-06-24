package org.huangyalong;

import cn.hutool.core.collection.CollUtil;

public class Codegen {

    public static void main(String[] args) {
        CodegenUtil.generate("org.huangyalong",
                CollUtil.newHashSet());
    }
}
