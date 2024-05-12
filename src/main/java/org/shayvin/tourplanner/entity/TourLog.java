package org.shayvin.tourplanner.entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;

public class TourLog {
    private final SimpleStringProperty date;
    private final SimpleStringProperty duration;
    private final SimpleStringProperty distance;
    private final SimpleStringProperty comment;
    private final SimpleStringProperty difficulty;
    private final SimpleStringProperty rating;

    public TourLog(String date, String duration, String distance, String comment, String difficulty, String rating) {
        this.date = new SimpleStringProperty(date);
        this.duration = new SimpleStringProperty(duration);
        this.distance = new SimpleStringProperty(distance);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleStringProperty(difficulty);
        this.rating = new SimpleStringProperty(rating);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getDistance() {
        return distance.get();
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public SimpleStringProperty difficultyProperty() {
        return difficulty;
    }

    public String getRating() {
        return rating.get();
    }

    public SimpleStringProperty ratingProperty() {
        return rating;
    }

}
