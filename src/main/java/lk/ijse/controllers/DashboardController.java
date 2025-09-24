package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {
    public AnchorPane ancDashboard;

    /**
     * Sub-page load කරන method එක
     * @param fxmlFile - FXML file name (without .fxml)
     */
    private void loadPage(String fxmlFile) {
        try {
            // FXML path check
            var resource = getClass().getResource("/view/" + fxmlFile + ".fxml");
            if (resource == null) {
                System.err.println("FXML file not found: " + fxmlFile);
                return;
            }

            // Sub-page load කරන්න
            Parent root = FXMLLoader.load(resource);

            // AnchorPane clear & load
            ancDashboard.getChildren().setAll(root);

            // Anchor constraints set කරන්න (auto-resize)
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
        loadPage("PaymentsManagement");
    }

    public void btnInstructorManagementOnAction(ActionEvent actionEvent) {
        loadPage("InstructorManagement");
    }

    public void btnCourseManagementOnAction(ActionEvent actionEvent) {
       loadPage("CourseManagement");
    }
}
