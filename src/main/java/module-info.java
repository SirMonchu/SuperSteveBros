module proyectoFinal.SuperSteveBros {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;

    opens proyectoFinal.SuperSteveBros to javafx.fxml;
    exports proyectoFinal.SuperSteveBros;

}
