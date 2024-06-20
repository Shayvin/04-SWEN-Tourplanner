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

    /*
    @PostLoad
    @PostPersist
    @PostUpdate
    private void synchronizeProperties() {
        dateProperty.set(date);
        durationProperty.set(duration);
        distanceProperty.set(distance);
        commentProperty.set(comment);
        difficultyProperty.set(difficulty);
        ratingProperty.set(rating);
    }

    @PrePersist
    @PreUpdate
    private void synchronizeFields() {
        date = dateProperty.get();
        duration = durationProperty.get();
        distance = distanceProperty.get();
        comment = commentProperty.get();
        difficulty = difficultyProperty.get();
        rating = ratingProperty.get();
    }

     */

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

    public String getDate() {
        return dateProperty.get();
    }

    public void setDate(String date) {
        this.dateProperty.set(date);
        this.date = date;
    }

    public Double getDuration() {
        return durationProperty.get();
    }

    public void setDuration(Double duration) {
        this.durationProperty.set(duration);
        this.duration = duration;
    }

    public Double getDistance() {
        return distanceProperty.get();
    }

    public void setDistance(Double distance) {
        this.distanceProperty.set(distance);
        this.distance = distance;
    }

    public String getComment() {
        return commentProperty.get();
    }

    public void setComment(String comment) {
        this.commentProperty.set(comment);
        this.comment = comment;
    }

    public Double getDifficulty() {
        return difficultyProperty.get();
    }

    public void setDifficulty(Double difficulty) {
        this.difficultyProperty.set(difficulty);
        this.difficulty = difficulty;
    }

    public Integer getRating() {
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
}
