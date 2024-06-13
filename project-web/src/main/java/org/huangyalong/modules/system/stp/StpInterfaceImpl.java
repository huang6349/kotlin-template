package org.huangyalong.modules.system.stp;

import cn.dev33.satoken.stp.StpInterface;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.service.PermService;
import org.huangyalong.modules.system.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.PermAssocTableDef.PERM_ASSOC;
import static org.huangyalong.modules.system.domain.table.PermTableDef.PERM;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;

@RequiredArgsConstructor
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    private final PermService permService;

    private final RoleService roleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        val roles = roleService.getByAccountId(loginId)
                .stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        val query = QueryWrapper.create()
                .select(PERM.CODE)
                .from(PERM)
                .leftJoin(PERM_ASSOC)
                .on(PERM.ID.eq(PERM_ASSOC.PERM_ID))
                .where(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(PERM_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(PERM_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(PERM_ASSOC.ASSOC.eq(ACCOUNT.getTableName())
                        .and(PERM_ASSOC.ASSOC_ID.eq(loginId)))
                .or(PERM_ASSOC.ASSOC.eq(ROLE.getTableName())
                        .and(PERM_ASSOC.ASSOC_ID.in(roles)));
        return permService.listAs(query, String.class);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.getByAccountId(loginId)
                .stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
    }
}
