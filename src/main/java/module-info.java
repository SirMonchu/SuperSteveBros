module proyectoFinal.SuperSteveBros {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires transitive javafx.graphics;
	
    opens proyectoFinal.SuperSteveBros to javafx.fxml;
    exports proyectoFinal.SuperSteveBros;

}
