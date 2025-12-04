package com.api.synco.module.class_user.domain.service;

import com.api.synco.module.class_user.domain.use_cases.*;
import org.springframework.stereotype.Service;

@Service
public class ClassUserService {

    private final CreateClassUserUseCase createClassUserUseCase;
    private final UpdateClassUserUseCase updateClassUserUseCase;
    private final DeleteClassUserUseCase deleteClassUserUseCase;
    private final GetClassUserUseCase getClassUserUseCase;
    private final GetAllClassUserUseCase getAllClassUserUseCase;

    public ClassUserService(CreateClassUserUseCase createClassUserUseCase, UpdateClassUserUseCase updateClassUserUseCase, DeleteClassUserUseCase deleteClassUserUseCase, GetClassUserUseCase getClassUserUseCase, GetAllClassUserUseCase getAllClassUserUseCase) {
        this.createClassUserUseCase = createClassUserUseCase;
        this.updateClassUserUseCase = updateClassUserUseCase;
        this.deleteClassUserUseCase = deleteClassUserUseCase;
        this.getClassUserUseCase = getClassUserUseCase;
        this.getAllClassUserUseCase = getAllClassUserUseCase;
    }


}
