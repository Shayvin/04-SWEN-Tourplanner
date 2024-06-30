package org.shayvin.tourplanner.viewmodel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NotificationPopupViewModel {

    public final StringProperty notificationTextProperty = new SimpleStringProperty("");

    public NotificationPopupViewModel() {
    }

    public void setNotificationText(String text) {
        notificationTextProperty.set(text);
    }

    public String getNotificationText() {
        return notificationTextProperty.get();
    }
}
