package org.shayvin.tourplanner.entity;

public class Tour {

    private String tourName;

    private String tourDescription;

    private String tourStart;

    private String tourDestination;

    private String tourType;

    private String tourDistance;

    private String tourDuration;

    public Tour(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration) {
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.tourStart = tourStart;
        this.tourDestination = tourDestination;
        this.tourType = tourType;
        this.tourDistance = tourDistance;
        this.tourDuration = tourDuration;
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
}
