package lk.ijse.service.custom;


import lk.ijse.dto.CourseDTO;
import lk.ijse.service.SuperService;

import java.util.List;
import java.util.Optional;

public interface CourseService extends SuperService {
    List<CourseDTO> getAllCourses() throws Exception;

    String getLastCourseId() throws Exception;

    boolean saveCourses(CourseDTO t) throws Exception;

    boolean updateCourses(CourseDTO t) throws Exception;

    boolean deleteCourses(String id) throws Exception;

    List<String> getAllCourseIds() throws Exception;

    Optional<CourseDTO> findByCourseId(String id) throws Exception;

    String generateNewCourseId();

}
