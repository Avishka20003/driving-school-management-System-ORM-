package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class CourseManagementController {


    public AnchorPane ancCourseManagement;
    public TextField txtCourseName;
    public Button btnAddStudent;
    public TextField txtDescription;
    public TextField txtCourseFree;
    public ComboBox comDuration;
    public ComboBox comInstructorId;

    public TableView tableCourse;
    public TableColumn colAction;
    public TableColumn colDescription;
    public TableColumn colCourseFree;
    public TableColumn colDuration;
    public TableColumn colName;
    public TableColumn colId;

    public void btnAddStudentOnAction(ActionEvent actionEvent) {

    }
}
