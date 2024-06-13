package org.huangyalong.core.tree;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TreeUtil {

    String SEPARATOR = "-";

    String ROOT = "ROOT";

    static String calculatePath(String parentPath,
                                Long parentId) {
        if (StrUtil.isNotBlank(parentPath))
            return StrUtil.join(SEPARATOR,
                    parentPath,
                    parentId);
        else return ROOT;
    }

    static <T extends TreeEntity<T>> List<T> list2Tree(List<T> list,
                                                       Long parentId) {
        Map<Long, List<T>> groupMap = list.stream()
                .sorted(Comparator.comparing(TreeEntity::getSort))
                .collect(Collectors.groupingBy(TreeEntity::getParentId));
        list.forEach(treeEntity -> treeEntity
                .setChildren(groupMap.get(treeEntity
                        .getId())));
        return groupMap.get(Opt.ofNullable(parentId)
                .orElse(0L));
    }
}
