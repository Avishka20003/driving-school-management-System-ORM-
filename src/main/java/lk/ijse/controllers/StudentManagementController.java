package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.dto.CourseDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.tm.StudentTM;
import lk.ijse.enums.ServiceTypes;
import lk.ijse.service.ServiceFactory;
import lk.ijse.service.custom.StudentService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentManagementController implements Initializable {

    @FXML
    private AnchorPane ancStudentManagement;

    @FXML
    private Button btnAddStudent;

    @FXML
    private TableColumn<StudentTM , String> colAddress;

    @FXML
    private TableColumn<StudentTM , String> colContact;

    @FXML
    private TableColumn<StudentTM , String> colCourses;

    @FXML
    private TableColumn<StudentTM , String> colName;

    @FXML
    private TableColumn<StudentTM , Pane> colStudentAction;

    @FXML
    private TableColumn<StudentTM , String> colStudentId;

    @FXML
    private TableView<StudentTM> tableStudent;

    @FXML
    private TextField txtStudentAddress;

    @FXML
    private TextField txtStudentContact;

    @FXML
    private TextField txtStudentDateOfBirth;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private TextField txtStudentFirstName;

    @FXML
    private Text txtStudentId;

    @FXML
    private TextField txtStudentLastName;

    @FXML
    private TextField txtStudentPayment;


    StudentService studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);


    @FXML
    void btnStudentAddOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCourses.setCellValueFactory(new PropertyValueFactory<>("courseIds"));
        colStudentAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed Load Data",ButtonType.OK).show();
        }
    }

    private void resetPage() {
//        loadNextId();
        loadAllStudents();

        txtStudentAddress.setText("");
        txtStudentContact.setText("");
        txtStudentDateOfBirth.setText("");
        txtStudentEmail.setText("");
        txtStudentFirstName.setText("");
        txtStudentId.setText("");
        txtStudentLastName.setText("");
        txtStudentPayment.setText("");


    }

//    private void loadNextId() {
//        txtStudentId.setText(studentService.generateNewStudentId());
//    }

    private void loadAllStudents() {
        try {
            tableStudent.setItems(FXCollections.observableArrayList(
                    studentService.getAllStudents().stream().map(studentDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> loadSideBarData(studentDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(studentDTO.getStudentId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new StudentTM(
                                studentDTO.getStudentId(),
                                studentDTO.getFirstName(),
                                null,
                                null,
                                studentDTO.getPhone(),
                                studentDTO.getAddress(),
                                null,
                                null,
                                studentDTO.getCourses().toString(),
                                action
                        );
                    }).toList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
                boolean isDeleted = studentService.deleteStudents(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
                    loadAllStudents();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the Student!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadSideBarData(StudentDTO student) {
        txtStudentAddress.setText(student.getAddress());
        txtStudentContact.setText(student.getPhone());
        txtStudentDateOfBirth.setText(student.getDob().toString());
        txtStudentEmail.setText(student.getEmail());
        txtStudentFirstName.setText(student.getFirstName());
        txtStudentId.setText(student.getStudentId());
        txtStudentLastName.setText(student.getLastName());
//        txtStudentPayment.setText(student.get);
    }

    public void onUpdate(StudentDTO studentDTO) {

    }
}
