package org.shayvin.tourplanner.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue
    @Column(name = "tour_id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String tourName;
    @Column(name = "description", nullable = false)
    private String tourDescription;
    @Column(name = "start", nullable = false)
    private String tourStart;
    @Column(name = "destination", nullable = false)
    private String tourDestination;
    @Column(name = "transport_type", nullable = false)
    private String tourType;
    @Column(name = "distance", nullable = false)
    private String tourDistance;
    @Column(name = "estimated_time", nullable = false)
    private String tourDuration;
    @Column(name = "information", nullable = false)
    private String tourInformation;
    @Column(name = "map", nullable = false)
    private String tourImage;

    public Tour(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation, String tourImage) {
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.tourStart = tourStart;
        this.tourDestination = tourDestination;
        this.tourType = tourType;
        this.tourDistance = tourDistance;
        this.tourDuration = tourDuration;
        this.tourInformation = tourInformation;
        this.tourImage = tourImage;
    }

    public Tour() {

    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public String getTourStart() {
        return tourStart;
    }

    public void setTourStart(String tourStart) {
        this.tourStart = tourStart;
    }

    public String getTourDestination() {
        return tourDestination;
    }

    public void setTourDestination(String tourDestination) {
        this.tourDestination = tourDestination;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getTourDistance() {
        return tourDistance;
    }

    public void setTourDistance(String tourDistance) {
        this.tourDistance = tourDistance;
    }

    public String getTourDuration() {
        return tourDuration;
    }

    public void setTourDuration(String tourDuration) {
        this.tourDuration = tourDuration;
    }

    public String getTourInformation() {
        return tourInformation;
    }

    public void setTourInformation(String tourInformation) {
        this.tourInformation = tourInformation;
    }

    public String getTourImage() {
        return tourImage;
    }

    public void setTourImage(String tourImage) {
        this.tourImage = tourImage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
