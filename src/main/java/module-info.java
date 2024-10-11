module com.example.project1.Application{
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project1.Application to javafx.fxml;
    exports com.example.project1.Application;
    exports com.example.project1.Controller;
    opens com.example.project1.Controller to javafx.fxml;
    exports com.example.project1.Model;
    opens com.example.project1.Model to javafx.fxml;
    exports com.example.project1.Util;
    opens com.example.project1.Util to javafx.fxml;

}