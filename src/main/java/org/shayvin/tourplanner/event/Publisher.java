package org.shayvin.tourplanner.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    private final Map<Event, List<Subscriber>> subscriberMap;

    public Publisher() {
        subscriberMap = new HashMap<>();
    }

    // subscribe(event, subscriber)
    public void subscribe(Event event, Subscriber subscriber) {
        List<Subscriber> subscribers = subscriberMap.get(event);

        if (null == subscribers) {
            subscribers = new ArrayList<>();
        }

        subscribers.add(subscriber);

        subscriberMap.put(event, subscribers);
    }

    // publish(event, message)
    public void publish(Event event, String message) {
        List<Subscriber> subscribers = subscriberMap.get(event);

        if (null == subscribers) {
            // TODO: Log this event
            return;
        }

        for (Subscriber subscriber: subscribers) {
            subscriber.notify(message);
        }
    }
}
