package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.modules.system.domain.PermAssoc;
import org.huangyalong.modules.system.domain.PermAssocsData;
import org.huangyalong.modules.system.mapper.PermAssocMapper;
import org.huangyalong.modules.system.request.PermAssocBO;
import org.huangyalong.modules.system.service.PermAssocService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.huangyalong.modules.system.domain.table.PermAssocTableDef.PERM_ASSOC;

@AllArgsConstructor
@Service
public class PermAssocServiceImpl extends ServiceImpl<PermAssocMapper, PermAssoc>
        implements PermAssocService {

    @Transactional(rollbackFor = Exception.class)
    public boolean assoc(PermAssocBO payload, String assoc) {
        val query = QueryWrapper.create()
                .where(PERM_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(PERM_ASSOC.ASSOC.eq(assoc))
                .and(PERM_ASSOC.ASSOC_ID.eq(payload.getAssocId()));
        super.remove(query);
        val data = PermAssocsData.create()
                .setAssoc(assoc)
                .setAssocId(payload.getAssocId())
                .addPermIds(payload.getPermIds())
                .getPermAssocs();
        return super.saveBatch(data);
    }
}
