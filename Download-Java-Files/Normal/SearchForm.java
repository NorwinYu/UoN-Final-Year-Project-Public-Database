package tivoli.presentation;

import tivoli.presentation.language.EN.English;

/**
 * A form bean for the search applicants form.
 */
public class SearchForm {

    private String fname;

    private String lname;

    private String yearOfApp;

    private String monthOfApp;

    private String dayOfApp;

    private String yearOfStart;

    private String monthOfStart;

    private String dayOfStart;

    private String yearOfEnd;

    private String monthOfEnd;

    private String dayOfEnd;

    private String position;

    private String firstName = English.firstName;

    private String lastName = English.lastName;

    private String applicationDate = English.applicationDate;

    private String startDate = English.startDate;

    private String endDate = English.endDate;

    private String competence = English.competence;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCompetence() {
        return competence;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getYearOfApp() {
        return yearOfApp;
    }

    public String getDayOfApp() {
        return dayOfApp;
    }

    public String getDayOfEnd() {
        return dayOfEnd;
    }

    public String getDayOfStart() {
        return dayOfStart;
    }

    public String getMonthOfApp() {
        return monthOfApp;
    }

    public String getMonthOfEnd() {
        return monthOfEnd;
    }

    public String getMonthOfStart() {
        return monthOfStart;
    }

    public String getYearOfEnd() {
        return yearOfEnd;
    }

    public String getYearOfStart() {
        return yearOfStart;
    }

    public void setDayOfApp(String dayOfApp) {
        this.dayOfApp = dayOfApp;
    }

    public void setDayOfEnd(String dayOfEnd) {
        this.dayOfEnd = dayOfEnd;
    }

    public void setDayOfStart(String dayOfStart) {
        this.dayOfStart = dayOfStart;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setMonthOfApp(String monthOfApp) {
        this.monthOfApp = monthOfApp;
    }

    public void setMonthOfEnd(String monthOfEnd) {
        this.monthOfEnd = monthOfEnd;
    }

    public void setMonthOfStart(String monthOfStart) {
        this.monthOfStart = monthOfStart;
    }

    public void setYearOfApp(String yearOfApp) {
        this.yearOfApp = yearOfApp;
    }

    public void setYearOfEnd(String yearOfEnd) {
        this.yearOfEnd = yearOfEnd;
    }

    public void setYearOfStart(String yearOfStart) {
        this.yearOfStart = yearOfStart;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAppDate() {
        return String.format("%s-%s-%s", yearOfApp, monthOfApp, dayOfApp);
    }

    public String getFromDate() {
        return String.format("%s-%s-%s", yearOfStart, monthOfStart, dayOfStart);
    }

    public String getToDate() {
        return String.format("%s-%s-%s", yearOfEnd, monthOfEnd, dayOfEnd);
    }
}
