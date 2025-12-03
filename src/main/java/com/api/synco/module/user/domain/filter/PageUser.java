package com.api.synco.module.user.domain.filter;

/**
 * Record representing pagination parameters for user queries.
 *
 * <p>This record provides validated pagination parameters with sensible defaults
 * and constraints. It ensures page numbers and sizes are within acceptable bounds.</p>
 *
 * <p>Constraints:</p>
 * <ul>
 *   <li>Page number must be at least 0</li>
 *   <li>Page size must be between 1 and 50</li>
 *   <li>Default page size is 10</li>
 * </ul>
 *
 * @param pageNumber the zero-based page number
 * @param pageSize the number of items per page
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record PageUser(int pageNumber,
                       int pageSize) {

    /** Minimum allowed page number (0-based). */
    public final static int MIN_PAGE_NUMBER = 0;
    
    /** Default page size when an invalid size is provided. */
    public final static int DEFAULT_PAGE_SIZE = 10;
    
    /** Maximum allowed page size. */
    public final static int MAX_PAGE_SIZE = 50;

    /**
     * Constructs a new pagination record with validation.
     *
     * <p>Invalid values are automatically corrected to defaults:</p>
     * <ul>
     *   <li>Negative page numbers are set to 0</li>
     *   <li>Invalid page sizes are set to the default of 10</li>
     * </ul>
     *
     * @param pageNumber the requested page number
     * @param pageSize the requested page size
     */
    public PageUser {
        if (pageNumber < MIN_PAGE_NUMBER) {
            pageNumber = MIN_PAGE_NUMBER;
        }

        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

    }

}