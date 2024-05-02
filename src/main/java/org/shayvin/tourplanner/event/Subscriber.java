package org.shayvin.tourplanner.event;

public interface Subscriber {
    void notify(String message);
}
