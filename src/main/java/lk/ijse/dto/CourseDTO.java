package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private String courseId;
    private String courseName;
    private String duration;
    private double free;
    private String description;
    private String instructorId;

    @Builder.Default
    private List<StudentCourseDetailsDTO> studentCourseDetails = new ArrayList<>();

    @Builder.Default
    private List<LessonsDTO> lessons = new ArrayList<>();

    public CourseDTO(String courseName, String description, double courseFee, String duration, String instructorId, Object instructorId1, Object studentCourseDetails, Object lessons) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.duration = duration;
    this.free = courseFee;
    this.description = description;
    this.instructorId = instructorId;
    }
}
