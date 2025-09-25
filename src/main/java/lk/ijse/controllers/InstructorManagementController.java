package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.dto.InstructorDTO;
import lk.ijse.dto.tm.InstructorTM;
import lk.ijse.dto.tm.StudentTM;
import lk.ijse.enums.ServiceTypes;
import lk.ijse.service.ServiceFactory;
import lk.ijse.service.custom.InstructorService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorManagementController implements Initializable {
    public AnchorPane ancInstructor;
    public Text txtInstructorId;
    public TextField txtInstructorFirstName;
    public TextField txtInstructorLastName;
    public TextField txtInstructorGmail;
    public TextField txtInstructorContact;
    public Button btnAddInstructor;
    public TextField txtInstructorSpecialization;
    public TextField txtInstructorAvailability;

    public TableView<InstructorTM> tableInstructor;
    public TableColumn<InstructorTM , String> colInstructorId;
    public TableColumn<InstructorTM , String> colInstructorName;
    public TableColumn<InstructorTM , String> colInstructorContact;
    public TableColumn<InstructorTM , String> colInstructorAvailability;
    public TableColumn<InstructorTM , String> colInstructorSpecialization;
    public TableColumn<InstructorTM , String> colInstructorAction;

    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);

    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String phoneRegex = "^07\\d{8}$";

    public void btnAddInstructorOnAction(ActionEvent actionEvent) {
        String firstName = txtInstructorFirstName.getText();
        String lastName = txtInstructorLastName.getText();
        String email = txtInstructorGmail.getText();
        String contact = txtInstructorContact.getText();
        String specialization = txtInstructorSpecialization.getText();
        String availability = txtInstructorAvailability.getText();

        boolean isValidEmail = email.matches(emailRegex);
        boolean isValidPhone = contact.matches(phoneRegex);

        txtInstructorGmail.setStyle(txtInstructorGmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtInstructorContact.setStyle(txtInstructorContact.getStyle() + ";-fx-border-color: #7367F0;");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        if (!isValidEmail) {
            txtInstructorGmail.setStyle(txtInstructorGmail.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid email format", ButtonType.OK).show();
            return;
        }
        if (!isValidPhone) {
            txtInstructorContact.setStyle(txtInstructorId.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid phone number format", ButtonType.OK).show();
            return;
        }
        try {
            boolean isSaved = instructorService.saveInstructors(InstructorDTO.builder()
                    .instructorId(txtInstructorId.getText())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .contact(contact)
                    .specialization(specialization)
                    .availability(availability)
                    .build());
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor saved successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colInstructorName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colInstructorContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colInstructorAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colInstructorSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colInstructorAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllInstructors();
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed Load Data",ButtonType.OK).show();
        }
    }

    private void resetPage() {
       // loadNextId();
        loadAllInstructors();

        txtInstructorId.setText("");
        txtInstructorFirstName.setText("");
        txtInstructorLastName.setText("");
        txtInstructorGmail.setText("");
        txtInstructorContact.setText("");
        txtInstructorAvailability.setText("");
        txtInstructorSpecialization.setText("");

    }

//    private void loadNextId() {
//        txtInstructorId.setText(instructorService.generateNewInstructorsId());
//    }

    private void loadAllInstructors() {
        try {
            tableInstructor.setItems(FXCollections.observableArrayList(
                    instructorService.getAllInstructors().stream().map(instructorDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> loadSideBarData(instructorDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(instructorDTO.getInstructorId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new InstructorTM(
                               instructorDTO.getInstructorId(),
                                instructorDTO.getFirstName(),
                                null,
                                null,
                                instructorDTO.getContact(),
                                instructorDTO.getSpecialization(),
                                instructorDTO.getAvailability(),
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
                "Are you sure whether you want to delete this Instructor?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Instructor");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = instructorService.deleteInstructors(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    loadAllInstructors();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the Instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadSideBarData(InstructorDTO instructorDTO) {
        txtInstructorId.setText(instructorDTO.getInstructorId());
        txtInstructorFirstName.setText(instructorDTO.getFirstName());
        txtInstructorLastName.setText(instructorDTO.getLastName());
        txtInstructorGmail.setText(instructorDTO.getEmail());
        txtInstructorContact.setText(instructorDTO.getContact());
        txtInstructorAvailability.setText(instructorDTO.getAvailability());
        txtInstructorSpecialization.setText(instructorDTO.getSpecialization());
    }
}
