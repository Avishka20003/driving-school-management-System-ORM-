module Driving.School.Management.System.ORM {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires java.naming;
    requires static lombok;
    requires java.management;

    // 🔑 FXML controller classes reflection access
    opens lk.ijse.controllers to javafx.fxml;

    // Export main packages if other modules need them
    exports lk.ijse;
    exports lk.ijse.controllers;
}
