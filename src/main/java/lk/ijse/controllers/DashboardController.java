package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {
    public AnchorPane ancDashboard;


    private void loadPage(String fxmlFile) {
        try {

            var resource = getClass().getResource("/view/" + fxmlFile + ".fxml");
            if (resource == null) {
                System.err.println("FXML file not found: " + fxmlFile);
                return;
            }

            Parent root = FXMLLoader.load(resource);


            ancDashboard.getChildren().setAll(root);

            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnStudentManagementOnAction(ActionEvent actionEvent) {
        loadPage("StudentManagement");
    }

    public void btnPaymentManagementOnAction(ActionEvent actionEvent) {
        loadPage("PaymentManagement");
    }

    public void btnInstructorManagementOnAction(ActionEvent actionEvent) {
        loadPage("InstructorManagement");
    }

    public void btnCourseManagementOnAction(ActionEvent actionEvent) {
        loadPage("CourseManagement");
    }
}
