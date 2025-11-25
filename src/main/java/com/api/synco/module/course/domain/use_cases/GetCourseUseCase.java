package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import org.springframework.stereotype.Component;

@Component
public class GetCourseUseCase {

    private final CourseRepository courseRepository;

    public GetCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Get the course.
     *
     * <p>
     * The method performs the following steps:
     * <ol>
     * <li>Find the course by ID.</li>
     * </ol>
     *
     * @param id The id of course
     * @throws CourseNotFoundException If course is not found
     * @return The entity course retrieved from the database
     */
    public CourseEntity execute(long id){
        return courseRepository.findById(id).orElseThrow( () -> new CourseNotFoundException(id));
    }
}
