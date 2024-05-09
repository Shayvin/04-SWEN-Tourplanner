package org.shayvin.tourplanner.event;

public enum Event {
    ADD_TOUR,
    REMOVE_TOUR,
    EDIT_TOUR,

    ENABLE_ADD_BUTTON,
    DISABLE_ADD_BUTTON,

    TOUR_NAME_ADDED,
    TOUR_DESCRIPTION_ADDED,
    TOUR_START_ADDED,
    TOUR_DESTINATION_ADDED,
    TOUR_TYPE_ADDED,
    TOUR_DISTANCE_ADDED,
    TOUR_TIME_ADDED,
    TOUR_INFORMATION_ADDED,

    TOUR_SELECTED,

    //TODO add this functionality if route is deselected in tourListViewModel
    TOUR_UNSELECTED

}
