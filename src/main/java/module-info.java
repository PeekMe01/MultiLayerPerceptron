module com.example.multilayerperceptron {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.multilayerperceptron to javafx.fxml;
    exports com.example.multilayerperceptron;
}