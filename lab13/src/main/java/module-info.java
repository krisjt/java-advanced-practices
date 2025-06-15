module pl.edu.pwr.knowak.lab13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.openjdk.nashorn;

    opens pl.edu.pwr.knowak.lab13 to javafx.fxml;
    exports pl.edu.pwr.knowak.lab13;
}