package org.shayvin.tourplanner.service;

public class ValidateInputService {

    public ValidateInputService() {
    }

    public boolean validateInput(String input){
        if(input.isBlank()){
            return false;
        }
        return !input.isEmpty();
    }


}
