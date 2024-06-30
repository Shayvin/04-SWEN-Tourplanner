module org.shayvin.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires org.apache.pdfbox;
    requires org.apache.logging.log4j.core;


    opens org.shayvin.tourplanner to javafx.fxml;
    opens org.shayvin.tourplanner.view to javafx.fxml;
    opens org.shayvin.tourplanner.viewmodel to javafx.fxml;
    opens org.shayvin.tourplanner.event to javafx.fxml;
    opens org.shayvin.tourplanner.entity;
    opens org.shayvin.tourplanner.service;
    opens org.shayvin.tourplanner.repository;
    opens org.shayvin.tourplanner.pdf;

    exports org.shayvin.tourplanner;
    exports org.shayvin.tourplanner.viewmodel;
    exports org.shayvin.tourplanner.view;
    exports org.shayvin.tourplanner.event;

}