package org.huangyalong.core.dict;

import cn.hutool.core.lang.Opt;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.val;

public class DictCache {

    private static final Cache<String, DictDefine> cache = Caffeine.newBuilder()
            .build();

    public static DictDefine query(String id) {
        return cache.getIfPresent(id);
    }

    public static void add(DictDefine payload) {
        val opt = Opt.ofNullable(payload);
        opt.ifPresent(vo -> cache
                .put(vo.getCategory(), vo));
    }
}
