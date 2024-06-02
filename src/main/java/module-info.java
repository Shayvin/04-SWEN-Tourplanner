module org.shayvin.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.shayvin.tourplanner to javafx.fxml;
    opens org.shayvin.tourplanner.view to javafx.fxml;
    opens org.shayvin.tourplanner.viewmodel to javafx.fxml;
    opens org.shayvin.tourplanner.event to javafx.fxml;
    opens org.shayvin.tourplanner.entity;
    opens org.shayvin.tourplanner.service;
    opens org.shayvin.tourplanner.repository;



    exports org.shayvin.tourplanner;
}