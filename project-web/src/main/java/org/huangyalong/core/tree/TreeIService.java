package org.huangyalong.core.tree;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.lang.func.LambdaUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import lombok.val;

import java.util.List;

public interface TreeIService<T extends TreeEntity<T>> extends IService<T> {

    default String getPath(Long parentId) {
        val parentPath = Opt.ofNullable(parentId)
                .map(this::getById)
                .map(T::getPath)
                .orElse(null);
        return TreeUtil.calculatePath(parentPath, parentId);
    }

    default List<T> listToTree(Long parentId) {
        val fieldName = LambdaUtil.getFieldName(T::getPath);
        val query = QueryWrapper.create()
                .like(fieldName, getPath(parentId));
        return TreeUtil.list2Tree(list(query), Opt.ofNullable(parentId)
                .orElse(0L));
    }
}
