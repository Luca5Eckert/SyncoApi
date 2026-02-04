package com.api.synco.module.course.infrastructure.repository;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.filter.PageCourse;
import com.api.synco.module.course.domain.port.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CourseRepositoryAdapter implements CourseRepository {

    private final CourseRepositoryJpa courseRepositoryJpa;

    public CourseRepositoryAdapter(CourseRepositoryJpa courseRepositoryJpa) {
        this.courseRepositoryJpa = courseRepositoryJpa;
    }

    @Override
    public boolean existsByNameOrAcronym(String name, String acronym) {
        return courseRepositoryJpa.existsByNameOrAcronym(name, acronym);
    }

    @Override
    public void save(CourseEntity course) {
        courseRepositoryJpa.save(course);
    }

    @Override
    public boolean existById(long id) {
        return courseRepositoryJpa.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        courseRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<CourseEntity> findById(long id) {
        return courseRepositoryJpa.findById(id);
    }

    @Override
    public Page<CourseEntity> findAll(Specification<CourseEntity> search, PageCourse pageCourse) {
        PageRequest pageRequest = PageRequest.of(
                pageCourse.pageNumber(),
                pageCourse.pageSize()
        );

        return courseRepositoryJpa.findAll(search, pageRequest);
    }

}
