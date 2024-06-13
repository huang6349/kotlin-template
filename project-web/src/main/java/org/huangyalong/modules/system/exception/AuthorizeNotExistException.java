package org.huangyalong.modules.system.exception;

import org.huangyalong.core.commons.exception.DataNotExistException;

public class AuthorizeNotExistException extends DataNotExistException {

    private static final String message = "帐号或密码错误";

    public AuthorizeNotExistException() {
        super(message);
    }
}
