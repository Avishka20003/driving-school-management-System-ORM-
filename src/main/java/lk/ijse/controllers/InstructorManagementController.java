package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class InstructorManagementController {
    public AnchorPane ancInstructor;
    public Text txtInstructorId;
    public TextField txtInstructorFirstName;
    public TextField txtInstructorLastName;
    public TextField txtInstructorGmail;
    public TextField txtInstructorContact;
    public Button btnAddInstructor;
    public TextField txtInstructorSpecialization;
    public TextField txtInstructorAvailability;

    public TableView tableInstructor;
    public TableColumn colInstructorId;
    public TableColumn colInstructorName;
    public TableColumn colInstructorContact;
    public TableColumn colInstructorAvailability;
    public TableColumn colInstructorSpecialization;
    public TableColumn colInstructorAction;

    public void btnAddInstructorOnAction(ActionEvent actionEvent) {

    }
}
