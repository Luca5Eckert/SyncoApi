package com.api.synco.module.class_entity.application.dto.update;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.ClassDomainException;
import com.api.synco.module.course.domain.CourseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

public record UpdateClassRequest(
        @NotNull @Size(max = 10, message = "The total hours exceeds the maximum allowed (10000).") @Min(value = 0, message = "The total hours cannot be negative.") int totalHours,
        @NotNull Shift shift
) {
}
