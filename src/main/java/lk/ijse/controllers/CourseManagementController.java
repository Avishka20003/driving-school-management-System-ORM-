package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.dto.CourseDTO;
import lk.ijse.dto.tm.CourseTM;
import lk.ijse.dto.tm.StudentTM;
import lk.ijse.enums.ServiceTypes;
import lk.ijse.service.ServiceFactory;
import lk.ijse.service.custom.CourseService;
import lk.ijse.service.custom.InstructorService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseManagementController implements Initializable {


    public AnchorPane ancCourseManagement;
    public TextField txtCourseName;
    public Button btnAddStudent;
    public TextField txtDescription;
    public TextField txtCourseFree;
    public ComboBox comDuration;
    public ComboBox comInstructorId;

    public TableView<CourseTM> tableCourse;
    public TableColumn<CourseTM , String> colAction;
    public TableColumn<CourseTM , String> colDescription;
    public TableColumn<CourseTM , Double> colCourseFree;
    public TableColumn<CourseTM , String> colDuration;
    public TableColumn<CourseTM , String> colName;
    public TableColumn<CourseTM , String> colId;

    CourseService courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);

    public void btnAddStudentOnAction(ActionEvent actionEvent) {
        String courseName = txtCourseName.getText();
        String description = txtDescription.getText();
        double courseFee = Double.parseDouble(txtCourseFree.getText());
        String duration = (String) comDuration.getValue();
        String instructorId = comInstructorId.getValue().toString();

        CourseDTO courseDTO = new CourseDTO(
            courseName , description , courseFee , duration , instructorId , null , null , null
        );

        try {
            courseService.saveCourses(courseDTO);
            loadAllCourses();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Course Saved Failed").show();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCourseFree.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try{
            comDuration.setItems(FXCollections.observableArrayList("01 month", "03 month", "06 month", "12 month"));
            comInstructorId.setItems(FXCollections.observableArrayList(instructorService.getAllInstructorIds()));

            loadAllCourses();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            e.printStackTrace();
        }
    }

    private void loadAllCourses() {
        try {
            tableCourse.setItems(FXCollections.observableArrayList(
                    courseService.getAllCourses().stream().map(courseDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> loadSideBarData(courseDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(courseDTO.getCourseId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new CourseTM(
                                courseDTO.getCourseId(),
                                courseDTO.getCourseName(),
                                courseDTO.getDuration(),
                                courseDTO.getFree(),
                                courseDTO.getDescription(),
                                courseDTO.getInstructorId(),
                                action

                        );
                    }).toList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadSideBarData(CourseDTO courseDTO) {
        txtCourseName.setText(courseDTO.getCourseName());
        comDuration.setValue(courseDTO.getDuration());
        txtCourseFree.setText(String.valueOf(courseDTO.getFree()));
        txtDescription.setText(courseDTO.getDescription());
        comInstructorId.setValue(courseDTO.getInstructorId());
    }

    public void onDelete(String id) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this course?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Course");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = courseService.deleteCourses(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully!").show();
                    loadAllCourses();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the Course!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
