package tivoli.presentation;

import javax.validation.constraints.*;

import tivoli.presentation.language.EN.English;

import java.util.Date;


/**
 * a form bean for the signup form.
 */
public class SignupForm {


    //@Pattern(regexp = "^[\\p{L}\\p{M}*]*$", message = "{general.invalid-char}")
    @Size(min = 2, max = 30, message = "{general.length}")
    @NotBlank(message = "{signup.password-missing}")
    private String password;

    //@Pattern(regexp = "^[\\p{L}\\p{M}*]*$", message = "{general.invalid-char}")
    @Size(min = 2, max = 30, message = "{general.length}")
    @NotBlank(message = "{signup.username-missing}")
    private String username;

    @NotBlank(message = "{signup.name-missing}")
    @Pattern(regexp = "^[\\p{L}\\p{M}*]*$", message = "{general.invalid-char}")
    @Size(min = 2, max = 30, message = "{general.length}")
    private String name;

    @NotBlank(message = "{signup.surname-missing}")
    @Pattern(regexp = "^[\\p{L}\\p{M}*]*$", message = "{general.invalid-char}")
    @Size(min = 2, max = 30, message = "{general.length}")
    private String surname;

    @Email(message = "{signup.email.invalid}")
    private String email;

    @NotBlank(message = "{signup.year-missing}")
    //@Pattern(regexp = "^\\d{4}$", message = "{signup.invalid-length}")
    private String yearOfBirth;

    @NotBlank(message = "{signup.month-missing}")
    //@Pattern(regexp = "^\\d{2}$", message = "{signup.invalid-length}")
    private String monthOfBirth;

    @NotBlank(message = "{signup.day-missing}")
    //@Pattern(regexp = "^\\d{2}$", message = "{signup.invalid-length}")
    private String dayOfBirth;

    @Pattern(regexp = "^\\d{4}$", message = "{signup.invalid-length}")
    @NotBlank(message = "{control.missing}")
    private String controlDigits;

    //@NotBlank(message = "Please specify your SSN")
    //private String ssn;
  

    private String firstNameLabel = English.firstName;
    private String lastNameLabel = English.lastName;
    private String usernameLabel = English.username;
    private String passwordLabel = English.password;
    private String emailLabel = English.email;
    private String dateOfBirthLabel = English.dateOfBirth;
    private String ssnLabel = English.ssn;
    private String controlDigitsLabel = English.controlDigits;
    private String signupLabel = English.signUp;

    public String getSignupLabel() {
        return signupLabel;
    }

    public String getFirstNameLabel()
    {
        return firstNameLabel;
    }

    public String getLastNameLabel()
    {
        return lastNameLabel;
    }

    public String getUsernameLabel()
    {
        return usernameLabel;
    }

    public String getPasswordLabel()
    {
        return passwordLabel;
    }

    public String getEmailLabel()
    {
        return emailLabel;
    }

    public String getControlDigitsLabel() {
        return controlDigitsLabel;
    }

    public String getYearOfBirth()
    {
        return yearOfBirth;
    }

    public String getMonthOfBirth()
    {
        return monthOfBirth;
    }

    public String getDayOfBirth()
    {
        return dayOfBirth;
    }

    public String getDateOfBirthLabel() {
        return dateOfBirthLabel;
    }

    public String getSsnLabel() {
        return ssnLabel;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getControlDigits()
    {
        return controlDigits;
    }

    public void setControlDigits(String controlDigits)
    {
        this.controlDigits = controlDigits;
    }

    /*public void setDateOfBirth(String yearOfBirth, String monthOfBirth, String dayOfBirth) {
        this.dateOfBirth = new Date(Integer.parseInt(yearOfBirth),Integer.parseInt(monthOfBirth),Integer.parseInt(dayOfBirth));
    }*/

    public void setYearOfBirth(String yearOfBirth)
    {
        this.yearOfBirth = yearOfBirth;
    }

    public void setMonthOfBirth(String monthOfBirth)
    {
        this.monthOfBirth = monthOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth)
    {
        this.dayOfBirth = dayOfBirth;
    }

    /*public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }*/


    @Override
    public String toString()
    {
        return "{ " + username + ", " + password + " }";
    }
}
