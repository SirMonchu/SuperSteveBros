module proyectoFinal.SuperSteveBros {
    requires javafx.controls;
    requires javafx.fxml;

    opens proyectoFinal.SuperSteveBros to javafx.fxml;
    exports proyectoFinal.SuperSteveBros;
}
