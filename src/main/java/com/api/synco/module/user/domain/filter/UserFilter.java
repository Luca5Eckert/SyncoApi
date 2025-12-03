package com.api.synco.module.user.domain.filter;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import java.time.Instant;

/**
 * Record representing filter criteria for user queries.
 *
 * <p>This record encapsulates all possible filter parameters for searching
 * users. It uses the builder pattern for convenient construction.</p>
 *
 * <p>Available filters:</p>
 * <ul>
 *   <li>Name substring matching</li>
 *   <li>Email substring matching</li>
 *   <li>Role exact matching</li>
 *   <li>Creation date range</li>
 * </ul>
 *
 * @param nameContains substring to search within user names
 * @param emailContains substring to search within email addresses
 * @param role the role to filter by
 * @param createAt start of creation date range
 * @param createTo end of creation date range
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserFilter(String nameContains,
                         String emailContains,
                         RoleUser role,
                         Instant createAt,
                         Instant createTo
) {

    /**
     * Creates a new builder instance.
     *
     * @return a new {@link Builder} for constructing UserFilter instances
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing {@link UserFilter} instances.
     *
     * <p>Provides a fluent API for setting filter parameters.</p>
     */
    public static class Builder {

        private String nameContains;
        private String emailContains;
        private RoleUser role;
        private Instant createAt;
        private Instant createTo;

        /**
         * Private constructor to enforce builder pattern usage.
         */
        private Builder() {
        }

        /**
         * Sets the name filter substring.
         *
         * @param nameContains the substring to match in names
         * @return this builder instance
         */
        public Builder setNameContains(String nameContains) {
            this.nameContains = nameContains;
            return this;
        }

        /**
         * Sets the email filter substring.
         *
         * @param emailContains the substring to match in emails
         * @return this builder instance
         */
        public Builder setEmailContains(String emailContains) {
            this.emailContains = emailContains;
            return this;
        }

        /**
         * Sets the role filter.
         *
         * @param role the role to filter by
         * @return this builder instance
         */
        public Builder setRole(RoleUser role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the start of the creation date range.
         *
         * @param createAt the start date (inclusive)
         * @return this builder instance
         */
        public Builder setCreatedFrom(Instant createAt) {
            this.createAt = createAt;
            return this;
        }

        /**
         * Sets the end of the creation date range.
         *
         * @param createTo the end date (inclusive)
         * @return this builder instance
         */
        public Builder setCreatedTo(Instant createTo) {
            this.createTo = createTo;
            return this;
        }

        /**
         * Builds the {@link UserFilter} instance.
         *
         * @return a new UserFilter with the configured criteria
         */
        public UserFilter build() {
            return new UserFilter(
                    this.nameContains,
                    this.emailContains,
                    this.role,
                    this.createAt,
                    this.createTo
            );
        }
    }
}