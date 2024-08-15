module org.example.battleshipjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.battleshipjava to javafx.fxml;
    exports org.example.battleshipjava;
}