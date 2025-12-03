package com.api.synco.module.class_user.domain.filter;

import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public record ClassUserFilter(
        TypeUserClass typeUserClass,
        ClassUserId classUserId,
        long userId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private TypeUserClass typeUserClassContains;
        ClassUserId classUserIdContains;
        long userIdContains;

        private Builder() {
        }

        public Builder setTypeUserClassContains(TypeUserClass typeUserClassContains) {
            this.typeUserClassContains = typeUserClassContains;
            return this;
        }


        public Builder setClassUserIdContains(ClassUserId classUserIdContains) {
            this.classUserIdContains = classUserIdContains;
            return this;
        }

        public Builder setNameContains(long userIdContains) {
            this.userIdContains = userIdContains;
            return this;
        }

        public ClassUserFilter build() {
            return new ClassUserFilter(
                    this.typeUserClassContains,
                    this.classUserIdContains,
                    this.userIdContains
            );
        }

    }

}
