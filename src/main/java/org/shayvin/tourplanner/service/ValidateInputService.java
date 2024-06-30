package org.shayvin.tourplanner.service;

import org.shayvin.tourplanner.entity.TourLog;

public class ValidateInputService {

    public ValidateInputService() {
    }

    public boolean validateInput(String input){
        if(input.isBlank()){
            return false;
        }
        return !input.isEmpty();
    }

    public boolean isValidDoubleString(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
