package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllClassUseCase {

    private final ClassRepository classRepository;

    public GetAllClassUseCase(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Page<ClassEntity> execute(int pageNumber, int pageSize, long courseId, int classNumber, Shift shift){

        ClassFilter classFilter = ClassFilter.builder()
                .setCourseIdContains(courseId)
                .setClassNumberContains(classNumber)
                .setShiftContains(shift)
                .build();

        PageClass pageClass = new PageClass(pageNumber, pageSize);

        return classRepository.findAll(classFilter, pageClass);
    }

}
