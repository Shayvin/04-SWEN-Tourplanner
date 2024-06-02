package org.shayvin.tourplanner.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;

import java.util.UUID;

public class TourLog {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Tour tour;

    private final SimpleStringProperty date;
    private final SimpleDoubleProperty duration;
    private final SimpleDoubleProperty distance;
    private final SimpleStringProperty comment;
    private final SimpleDoubleProperty difficulty;
    private final SimpleIntegerProperty rating;

    public TourLog(String date, Double duration, Double distance, String comment, Double difficulty, Integer rating) {
        this.date = new SimpleStringProperty(date);
        this.duration = new SimpleDoubleProperty(duration);
        this.distance = new SimpleDoubleProperty(distance);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleDoubleProperty(difficulty);
        this.rating = new SimpleIntegerProperty(rating);
    }

    public UUID getId() {
        return id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public Double getDuration() {
        return duration.get();
    }

    public void setDuration(Double duration) {
        this.duration.set(duration);
    }

    public Double getDistance() {
        return distance.get();
    }

    public void setDistance(Double distance) {
        this.distance.set(distance);
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public Double getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty.set(difficulty);
    }

    public Integer getRating() {
        return rating.get();
    }

    public void setRating(Integer rating) {
        this.rating.set(rating);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public SimpleDoubleProperty durationProperty() {
        return duration;
    }

    public SimpleDoubleProperty distanceProperty() {
        return distance;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public SimpleDoubleProperty difficultyProperty() {
        return difficulty;
    }

    public SimpleIntegerProperty ratingProperty() {
        return rating;
    }
}
