package org.huangyalong.modules.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.BadRequestException;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.mapper.AccountMapper;
import org.huangyalong.modules.system.request.AccountPasswordBO;
import org.huangyalong.modules.system.service.AccountPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AccountPasswordServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountPasswordService {

    @Transactional(rollbackFor = Exception.class)
    public boolean update(AccountPasswordBO payload) {
        if (ObjectUtil.notEqual(payload.getNewPassword(), payload.getConfirm()))
            throw new BadRequestException("两次密码不一致");
        val loginId = StpUtil.getLoginIdAsLong();
        val account = super.getByIdOpt(loginId)
                .orElseThrow(DataNotExistException::new);
        if (!BCrypt.checkpw(payload.getOldPassword(), account.getPassword()))
            throw new BadRequestException("旧的密码不正确");
        account.setPassword(BCrypt.hashpw(payload.getNewPassword(), account.getSalt()));
        super.updateById(account);
        return Boolean.TRUE;
    }
}
