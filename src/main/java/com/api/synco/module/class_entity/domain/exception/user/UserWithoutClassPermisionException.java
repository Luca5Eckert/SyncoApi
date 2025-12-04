package com.api.synco.module.class_entity.domain.exception.user;

import com.api.synco.module.class_entity.domain.exception.ClassDomainException;

public class UserWithoutClassPermisionException extends ClassDomainException {
    public UserWithoutClassPermisionException(String message) {
        super(message);
    }
}
