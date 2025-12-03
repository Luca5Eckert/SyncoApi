package com.api.synco.module.class_user.domain.filter;

public record PageClassUser(
        int pageNumber,
        int pageSize
) {

    private final static int MIN_PAGE_NUMBER = 0;
    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int MAX_PAGE_SIZE = 50;

    public PageClassUser {
        if (pageNumber < MIN_PAGE_NUMBER) {
            pageNumber = MIN_PAGE_NUMBER;
        }

        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

    }

}
