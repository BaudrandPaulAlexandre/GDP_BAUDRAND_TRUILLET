module org.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    opens org.project to javafx.fxml;
    exports org.project;
    exports org.project.controllers;
    opens org.project.controllers to javafx.fxml;
    exports org.project.components.models;
    opens org.project.components.models to javafx.fxml;
    exports org.project.components.utils;
    opens org.project.components.utils to javafx.fxml;
}