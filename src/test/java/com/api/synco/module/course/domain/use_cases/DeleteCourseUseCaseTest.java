package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.delete.DeleteCourseRequest;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.exception.UserWithoutDeleteCoursePermissionException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PermissionPolicy permissionPolicy;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteCourseUseCase deleteCourseUseCase;

    private DeleteCourseRequest deleteCourseRequest;

    private UserEntity user;
    private long idUser;

    @BeforeEach
    public void setup(){
        idUser = 1;
        deleteCourseRequest = new DeleteCourseRequest(idUser);

        user = new UserEntity(idUser, null, null, null, RoleUser.ADMIN);
    }


    @Test
    public void shouldDeleteCourseWhenExistAndUserHavePermission(){
        //arrange
        when(courseRepository.existById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(permissionPolicy.canDelete(any(RoleUser.class))).thenReturn(true);

        //act
        deleteCourseUseCase.execute(deleteCourseRequest, idUser);

        //assert
        verify(courseRepository).deleteById(any(Long.class));

    }

    @Test
    public void shouldThrowExceptionCourseWhenExistAndUserDoNotHavePermission(){
        //arrange
        user.setRole(RoleUser.USER);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        //act and assert
        assertThatThrownBy(() -> deleteCourseUseCase.execute(deleteCourseRequest, idUser))
                .isExactlyInstanceOf(UserWithoutDeleteCoursePermissionException.class);

        //assert
        verify(courseRepository, never()).deleteById(any(Long.class));

    }

    @Test
    public void shouldThrowExceptionCourseWhenCourseNotExist(){
        //arrange
        when(courseRepository.existById(any(Long.class))).thenReturn(false);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(permissionPolicy.canDelete(any(RoleUser.class))).thenReturn(true);

        // act and assert
        assertThatThrownBy( () -> deleteCourseUseCase.execute(deleteCourseRequest, idUser))
                .isExactlyInstanceOf(CourseNotFoundException.class);

        //assert
        verify(courseRepository, never()).deleteById(any(Long.class));

    }


}