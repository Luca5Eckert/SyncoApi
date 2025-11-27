package com.api.synco.module.class_entity.domain.filter;

import com.api.synco.module.class_entity.domain.enumerator.Shift;

public record ClassFilter(
        long courseIdContains
        , int classNumberContains
        , Shift shiftContains
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long courseIdContains;
        private int classNumberContains;
        private Shift shiftContains;

        private Builder() {
        }

        public Builder setCourseIdContains(long courseIdContains) {
            this.courseIdContains = courseIdContains;
            return this;
        }

        public Builder setClassNumberContains(int classNumberContains) {
            this.classNumberContains = classNumberContains;
            return this;
        }

        public Builder setShiftContains(Shift shiftContains){
            this.shiftContains = shiftContains;
            return this;
        }

        public ClassFilter build() {
            return new ClassFilter(
                    this.courseIdContains,
                    this.classNumberContains,
                    this.shiftContains
            );
        }

    }

}
