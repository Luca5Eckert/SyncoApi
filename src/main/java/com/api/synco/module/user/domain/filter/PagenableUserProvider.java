package com.api.synco.module.user.domain.filter;

/**
 * Utility class for creating pagination instances.
 *
 * <p>This class provides a factory method for creating {@link PageUser}
 * instances with validated parameters.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PageUser
 */
public class PagenableUserProvider {

    /**
     * Creates a new {@link PageUser} instance.
     *
     * @param pageNumber the zero-based page number
     * @param pageSize the number of items per page
     * @return a validated PageUser instance
     */
    public static PageUser toInstance(int pageNumber, int pageSize) {
        return new PageUser(pageNumber, pageSize);
    }
}
