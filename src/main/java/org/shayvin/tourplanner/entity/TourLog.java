package org.shayvin.tourplanner.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;

import java.util.UUID;

@Entity
public class TourLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    private String date;
    private double duration;
    private double distance;
    private String comment;
    private double difficulty;
    private int rating;

    @Transient
    private final SimpleStringProperty dateProperty = new SimpleStringProperty();

    @Transient
    private final SimpleDoubleProperty durationProperty = new SimpleDoubleProperty();

    @Transient
    private final SimpleDoubleProperty distanceProperty = new SimpleDoubleProperty();

    @Transient
    private final SimpleStringProperty commentProperty = new SimpleStringProperty();

    @Transient
    private final SimpleDoubleProperty difficultyProperty = new SimpleDoubleProperty();

    @Transient
    private final SimpleIntegerProperty ratingProperty = new SimpleIntegerProperty();

    public TourLog() {
        // Default constructor for JPA
    }

    public TourLog(String date, Double duration, Double distance, String comment, Double difficulty, Integer rating) {
        setDate(date);
        setDuration(duration);
        setDistance(distance);
        setComment(comment);
        setDifficulty(difficulty);
        setRating(rating);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {this.id = id;}

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getDateProperty() {
        return dateProperty.get();
    }

    public void setDate(String date) {
        this.dateProperty.set(date);
        this.date = date;
    }

    public Double getDurationProperty() {
        return durationProperty.get();
    }

    public void setDuration(Double duration) {
        this.durationProperty.set(duration);
        this.duration = duration;
    }

    public Double getDistanceProperty() {
        return distanceProperty.get();
    }

    public void setDistance(Double distance) {
        this.distanceProperty.set(distance);
        this.distance = distance;
    }

    public String getCommentProperty() {
        return commentProperty.get();
    }

    public void setComment(String comment) {
        this.commentProperty.set(comment);
        this.comment = comment;
    }

    public Double getDifficultyProperty() {
        return difficultyProperty.get();
    }

    public void setDifficulty(Double difficulty) {
        this.difficultyProperty.set(difficulty);
        this.difficulty = difficulty;
    }

    public Integer getRatingProperty() {
        return ratingProperty.get();
    }

    public void setRating(Integer rating) {
        this.ratingProperty.set(rating);
        this.rating = rating;
    }

    public StringProperty dateProperty() {
        return dateProperty;
    }

    public SimpleDoubleProperty durationProperty() {
        return durationProperty;
    }

    public SimpleDoubleProperty distanceProperty() {
        return distanceProperty;
    }

    public StringProperty commentProperty() {
        return commentProperty;
    }

    public SimpleDoubleProperty difficultyProperty() {
        return difficultyProperty;
    }

    public SimpleIntegerProperty ratingProperty() {
        return ratingProperty;
    }

    public String getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public String getComment() {
        return comment;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public int getRating() {
        return rating;
    }
}
