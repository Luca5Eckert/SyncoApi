package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.ClassSearchProvider;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GetAllClassUseCase {

    private final ClassRepository classRepository;

    public GetAllClassUseCase(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    public Page<ClassEntity> execute(PageClass pageClass, ClassFilter classFilter){
        Specification<ClassEntity> classEntitySpecification = ClassSearchProvider.of(classFilter);

        PageRequest pageRequest =  PageRequest.of(
                pageClass.pageNumber(),
                pageClass.pageSize()
        );

        return classRepository.findAll(classEntitySpecification, pageRequest);
    }

}
