package org.shayvin.tourplanner.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String start;
    private String destination;
    private String type;
    private String distance;
    private String duration;
    private String information;
    private String image;

    @OneToMany(
            targetEntity = TourLog.class,
            mappedBy = "tour",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<TourLog> tourLogList;

    public Tour(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation, String tourImage) {
        this.name = tourName;
        this.description = tourDescription;
        this.start = tourStart;
        this.destination = tourDestination;
        this.type = tourType;
        this.distance = tourDistance;
        this.duration = tourDuration;
        this.information = tourInformation;
        this.image = tourImage;
        this.tourLogList = new ArrayList<>();
    }

    public Tour() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String tourDescription) {
        this.description = tourDescription;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String tourStart) {
        this.start = tourStart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String tourDestination) {
        this.destination = tourDestination;
    }

    public String getType() {
        return type;
    }

    public void setType(String tourType) {
        this.type = tourType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String tourDistance) {
        this.distance = tourDistance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String tourDuration) {
        this.duration = tourDuration;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String tourInformation) {
        this.information = tourInformation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String tourImage) {
        this.image = tourImage;
    }

    public List<TourLog> getTourLogList() {
        if (tourLogList == null) {
            tourLogList = new ArrayList<>();
        }
        return tourLogList;
    }

    public void setTourLogList(List<TourLog> tourLogList) {
        this.tourLogList = tourLogList;
    }
}
