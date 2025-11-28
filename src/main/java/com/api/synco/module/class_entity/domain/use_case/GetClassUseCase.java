package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Use case responsible for get the class
 *
 * @author lucas_eckert
 * @version 1.0
 */
@Component
public class GetClassUseCase {

    private final ClassRepository classRepository;

    public GetClassUseCase(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    /**
     * Get the class
     *
     * @param classEntityId The unique identifier of the class to be got
     * @return Return the Class Entity
     * @throws ClassNotFoundException If class is not found
     */
    public ClassEntity execute(ClassEntityId classEntityId){
        return classRepository.findById(classEntityId)
                .orElseThrow(ClassNotFoundException::new);
    }

}
