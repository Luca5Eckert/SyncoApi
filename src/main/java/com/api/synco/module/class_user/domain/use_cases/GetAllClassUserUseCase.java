package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.filter.ClassUserFilter;
import com.api.synco.module.class_user.domain.filter.ClassUserSearchProvider;
import com.api.synco.module.class_user.domain.filter.PageClassUser;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllClassUserUseCase {

    private final ClassUserRepository classUserRepository;

    public GetAllClassUserUseCase(ClassUserRepository classUserRepository) {
        this.classUserRepository = classUserRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClassUser> execute(ClassUserFilter classUserFilter, PageClassUser pageClassUser){
        Specification<ClassUser> classUserSpecification = ClassUserSearchProvider.of(classUserFilter);

        PageRequest pageRequest = PageRequest.of(
                pageClassUser.pageNumber(),
                pageClassUser.pageSize()
        );

        return classUserRepository.findAll(
                classUserSpecification,
                pageRequest
        );
    }

}
