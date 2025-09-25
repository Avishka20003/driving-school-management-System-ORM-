package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "student_course_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseDetails {
    @Id
    private String studentCourseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private Date enrollmentDate;
    private String status;
    private String grade;
}
