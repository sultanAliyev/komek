package kz.iitu.iitu.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountAlreadyExistsException extends AuthenticationException {
    public AccountAlreadyExistsException(
            String msg,
            Throwable cause) {
        super(msg, cause);
    }

    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }
}
