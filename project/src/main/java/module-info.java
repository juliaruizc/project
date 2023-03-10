module isen.project {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires org.xerial.sqlitejdbc;
	requires junit;

    opens isen.project to javafx.fxml;
    exports isen.project;
}
