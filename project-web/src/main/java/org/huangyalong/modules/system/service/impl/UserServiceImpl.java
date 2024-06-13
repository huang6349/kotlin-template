package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.modules.system.domain.User;
import org.huangyalong.modules.system.mapper.UserMapper;
import org.huangyalong.modules.system.service.UserService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

import static org.huangyalong.modules.system.domain.table.UserTableDef.USER;

@AllArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public Optional<User> getByAccountIdOpt(Serializable id) {
        val user = getByAccountId(id);
        return Optional.ofNullable(user);
    }

    @Override
    public User getByAccountId(Serializable id) {
        val query = QueryWrapper.create()
                .where(USER.ACCOUNT_ID.eq(id));
        return super.getOne(query);
    }
}
