module proyectoFinal.SuperSteveBros {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    opens proyectoFinal.SuperSteveBros to javafx.fxml;
    opens proyectoFinal.SuperSteveBros.FXMLcontrollers to javafx.fxml;
    exports proyectoFinal.SuperSteveBros;
    exports proyectoFinal.SuperSteveBros.FXMLcontrollers;
}
