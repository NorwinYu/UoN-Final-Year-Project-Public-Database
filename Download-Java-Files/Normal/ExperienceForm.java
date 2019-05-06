package tivoli.presentation;


import tivoli.presentation.language.EN.English;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.*;


/**
 * A form bean for the experience form.
 */
public class ExperienceForm {


    @NotBlank(message = "{experience.position-missing}")
    private String position;

    @NotNull(message = "{experience.years.empty}")
    private double yearsOfExperience;

    private String yearsOfExperienceLabel = English.yearsOfExperience;

    private String fieldOfExperience = English.fieldOfExperience;

    public String getYearsOfExperienceLabel() {
        return yearsOfExperienceLabel;
    }

    public String getFieldOfExperience() {
        return fieldOfExperience;
    }

    public String getPosition() {
        return position;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }


}
