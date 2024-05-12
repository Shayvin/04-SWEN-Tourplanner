module org.shayvin.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens org.shayvin.tourplanner to javafx.fxml;
    opens org.shayvin.tourplanner.view to javafx.fxml;
    opens org.shayvin.tourplanner.viewmodel to javafx.fxml;
    opens org.shayvin.tourplanner.event to javafx.fxml;
    opens org.shayvin.tourplanner.entity to javafx.base;

    exports org.shayvin.tourplanner;
}