package org.huangyalong.modules.system.service.impl;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.BadRequestException;
import org.huangyalong.core.commons.exception.DataAlreadyExistException;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.domain.User;
import org.huangyalong.modules.system.mapper.AccountMapper;
import org.huangyalong.modules.system.request.UserBO;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.modules.system.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;

@AllArgsConstructor
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {

    private final StringRedisTemplate redisTemplate;

    private final UserService userService;

    @Override
    public String loadNickname(Object id) {
        if (!SpringMVCUtil.isWeb() || ObjectUtil.isNull(id)) return null;
        val key = StrUtil.format("account_nickname_{}", id);
        val nickname = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(nickname)
                .orElse(null);
    }

    @Override
    public String loadNickname() {
        if (!SpringMVCUtil.isWeb()) return null;
        val loginId = loadLoginId();
        return this.loadNickname(Optional.ofNullable(loginId)
                .orElse(null));
    }

    @Override
    public String loadLoginIdAsString() {
        if (!SpringMVCUtil.isWeb()) return null;
        val loginId = StpUtil.getLoginIdDefaultNull();
        return Optional.ofNullable(loginId)
                .map(Convert::toStr)
                .orElse(null);
    }

    @Override
    public Long loadLoginId() {
        if (!SpringMVCUtil.isWeb()) return null;
        val loginId = StpUtil.getLoginIdDefaultNull();
        return Optional.ofNullable(loginId)
                .map(Convert::toLong)
                .orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean add(UserBO payload) {
        if (ObjectUtil.notEqual(payload.getPassword1(), payload.getPassword2()))
            throw new BadRequestException("两次密码不一致");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getUsername()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.USERNAME.eq(userBO.getUsername()))
                        .and(ACCOUNT.USERNAME.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("帐号已存在");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getMobile()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.MOBILE.eq(userBO.getMobile()))
                        .and(ACCOUNT.MOBILE.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("手机号码已存在");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getEmail()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.EMAIL.eq(userBO.getEmail()))
                        .and(ACCOUNT.EMAIL.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("邮箱已存在");
        val account = Account.create()
                .setUsername(payload.getUsername())
                .with(payload);
        super.save(account);
        val user = User.create()
                .setAccountId(account.getId())
                .with(payload);
        userService.save(user);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(UserBO payload) {
        if (ObjectUtil.notEqual(payload.getPassword1(), payload.getPassword2()))
            throw new BadRequestException("两次密码不一致");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getUsername()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.USERNAME.eq(userBO.getUsername()))
                        .and(ACCOUNT.USERNAME.isNotNull())
                        .and(ACCOUNT.ID.ne(userBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("帐号已存在");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getMobile()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.MOBILE.eq(userBO.getMobile()))
                        .and(ACCOUNT.MOBILE.isNotNull())
                        .and(ACCOUNT.ID.ne(userBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("手机号码已存在");
        if (Opt.ofNullable(payload).filter(userBO ->
                        ObjectUtil.isNotEmpty(userBO.getEmail()))
                .map(userBO -> super.exists(QueryWrapper.create()
                        .where(ACCOUNT.EMAIL.eq(userBO.getEmail()))
                        .and(ACCOUNT.EMAIL.isNotNull())
                        .and(ACCOUNT.ID.ne(userBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("邮箱已存在");
        val account = super.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        if (ObjectUtil.notEqual(payload.getUsername(), account.getUsername()))
            throw new BadRequestException("帐号不允许修改");
        val user = userService.getByAccountIdOpt(account.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        userService.updateById(user);
        super.updateById(account);
        return Boolean.TRUE;
    }
}
