package Models;

public class PersonRecord {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String jobTitle;
    private String university;

    public PersonRecord(String id, String firstName, String lastName, String email, String company, String jobTitle, String university) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.jobTitle = jobTitle;
        this.university = university;
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCompany(){
        return this.company;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public String getUniversity() {
        return this.university;
    }


    public String toString() {
        return id + " " + firstName + " " + lastName + " " + email + " " + company + " " + jobTitle + " " + university;

    }
    
}
