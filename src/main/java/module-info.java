module org.shayvin.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.shayvin.tourplanner to javafx.fxml;
    opens org.shayvin.tourplanner.view to javafx.fxml;
    exports org.shayvin.tourplanner;
}