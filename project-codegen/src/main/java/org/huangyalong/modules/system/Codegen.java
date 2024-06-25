package org.huangyalong.modules.system;

import cn.hutool.core.collection.CollUtil;
import org.huangyalong.CodegenUtil;

public class Codegen {

    public static void main(String[] args) {
        CodegenUtil.generate("org.huangyalong.modules.system",
                CollUtil.newHashSet("tb_tenant",
                        "tb_perm",
                        "tb_role",
                        "tb_dept",
                        "tb_account"));
    }
}
