package lk.ijse.service.custom.impl;



import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dao.custom.StudentCourseDetailDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.StudentCourseDetailsDTO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Student;
import lk.ijse.entity.StudentCourseDetails;
import lk.ijse.enums.DAOTypes;
import lk.ijse.mapper.StudentCourseDetailsMapper;
import lk.ijse.service.custom.StudentCourseDetailService;
import lk.ijse.service.exception.DuplicateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentCourseDetailServiceImpl implements StudentCourseDetailService {

    private final StudentCourseDetailDAO studentCourseDetailDAO = (StudentCourseDetailDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT_COURSE_DETAILS);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    @Override
    public List<StudentCourseDetailsDTO> getAllStudentCourseDetails() throws Exception {
        List<StudentCourseDetails> studentCourseDetails = studentCourseDetailDAO.getAll();
        List<StudentCourseDetailsDTO> studentCourseDetailsDTOs = new ArrayList<>();
        for (StudentCourseDetails studentCourseDetail : studentCourseDetails) {
            studentCourseDetailsDTOs.add(StudentCourseDetailsMapper.toDTO(studentCourseDetail));
        }
        return studentCourseDetailsDTOs;
    }

    @Override
    public String getLastStudentCourseDetailId() throws Exception {
        return studentCourseDetailDAO.getLastId();
    }

    @Override
    public boolean saveStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception {
        Optional<Student> studentExists = studentDAO.findById(t.getStudentId());
        Optional<Course> courseExists = courseDAO.findById(t.getCourseId());
        Optional<StudentCourseDetails> studentCourseDetailsExists = studentCourseDetailDAO.findById(t.getStudentCourseId());

        if (studentCourseDetailsExists.isPresent()) {
            throw new DuplicateException("Student Course Details already exists");
        }
        if (studentExists.isPresent() &&  courseExists.isPresent()) {
            return studentCourseDetailDAO.save(StudentCourseDetailsMapper.toEntity(t));
        }
        throw new Exception("Student or Course not found");
    }

    @Override
    public boolean updateStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception {
        Optional<StudentCourseDetails>  studentCourseDetailsExists = studentCourseDetailDAO.findById(t.getStudentCourseId());
        if (studentCourseDetailsExists.isEmpty()) {
            throw new Exception("Student Course not found");
        }
        return studentCourseDetailDAO.update(StudentCourseDetailsMapper.toEntity(t));
    }

    @Override
    public boolean deleteStudentCourseDetails(String id) throws Exception {
        Optional<StudentCourseDetails>  studentCourseDetailsExists = studentCourseDetailDAO.findById(id);
        if (studentCourseDetailsExists.isEmpty()) {
            throw new Exception("Student Course not found");
        }
        return studentCourseDetailDAO.delete(id);
    }

    @Override
    public List<String> getAllStudentCourseDetailIds() throws Exception {
        return studentCourseDetailDAO.getAllIds();
    }

    @Override
    public Optional<StudentCourseDetailsDTO> findByStudentCourseDetailId(String id) throws Exception {
        Optional<StudentCourseDetails> details = studentCourseDetailDAO.findById(id);
        return details.map(StudentCourseDetailsMapper::toDTO);
    }
}
