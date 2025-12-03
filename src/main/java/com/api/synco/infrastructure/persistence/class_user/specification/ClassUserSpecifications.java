package com.api.synco.infrastructure.persistence.class_user.specification;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import org.springframework.data.jpa.domain.Specification;

public class ClassUserSpecifications {

    /**
     * Filtra pelo tipo de usuário na turma (ex: TEACHER, STUDENT).
     */
    public static Specification<ClassUser> typeUserClassContains(TypeUserClass typeUserClass) {
        return (root, query, builder) -> {
            if (typeUserClass == null) {
                return null;
            }
            return builder.equal(root.get("typeUserClass"), typeUserClass);
        };
    }

    /**
     * Filtra pela igualdade exata do ID Composto completo.
     */
    public static Specification<ClassUser> classUserIdContains(ClassUserId classUserId) {
        return (root, query, builder) -> {
            if (classUserId == null) {
                return null;
            }
            return builder.equal(root.get("id"), classUserId);
        };
    }

    /**
     * Filtra apenas pela parte "userId" de dentro do ID Composto.
     * Útil para buscar "todas as turmas de um usuário específico".
     */
    public static Specification<ClassUser> userIdContains(long userId) {
        return (root, query, builder) -> {
            return builder.equal(root.get("id").get("userId"), userId);
        };
    }
}