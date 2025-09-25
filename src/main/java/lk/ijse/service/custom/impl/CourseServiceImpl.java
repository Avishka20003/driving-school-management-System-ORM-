package lk.ijse.service.custom.impl;


import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dto.CourseDTO;
import lk.ijse.entity.Course;
import lk.ijse.enums.DAOTypes;
import lk.ijse.mapper.CourseMapper;
import lk.ijse.service.custom.CourseService;
import lk.ijse.service.exception.DuplicateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        List<Course> courses = courseDAO.getAll();
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course course : courses) {
            dtos.add(CourseMapper.toDTO(course));
        }
        return dtos;
    }

    @Override
    public String getLastCourseId() throws Exception {
        return courseDAO.getLastId();
    }

    @Override
    public boolean saveCourses(CourseDTO t) throws Exception {
        Optional<Course> course = courseDAO.findById(t.getCourseId());
        if (course.isPresent()) {
            throw new DuplicateException("Course already exists");
        }
        return courseDAO.save(CourseMapper.toEntity(t));
    }

    @Override
    public boolean updateCourses(CourseDTO t) throws Exception {
        Optional<Course> course = courseDAO.findById(t.getCourseId());
        if (course.isEmpty()) {
            throw new DuplicateException("Course not Found");
        }
        return courseDAO.update(CourseMapper.toEntity(t));
    }

    @Override
    public boolean deleteCourses(String id) throws Exception {
        Optional<Course> course = courseDAO.findById(id);
        if (course.isEmpty()) {
            throw new DuplicateException("Course not Found");
        }
        return courseDAO.delete(id);
    }

    @Override
    public List<String> getAllCourseIds() throws Exception {
        return courseDAO.getAllIds();
    }

    @Override
    public Optional<CourseDTO> findByCourseId(String id) throws Exception {
        Optional<Course> course = courseDAO.findById(id);
        return course.map(CourseMapper::toDTO);
    }

    @Override
    public String generateNewCourseId() {
        return courseDAO.generateNewId();
    }
}
