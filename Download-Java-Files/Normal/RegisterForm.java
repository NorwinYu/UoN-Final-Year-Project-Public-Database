package recruitment.presentation.recr;

import recruitment.util.Util;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A form bean for the register form.
 */
class RegisterForm {

    @NotEmpty
    @NotBlank(message = "First name may not be blank.")
    private String fname;

    @NotEmpty
    @NotBlank(message = "Last name may not be blank.")
    private String lname;

    @NotEmpty
    @Email(message = "Please submit a valid email address.")
    @NotBlank(message = "Please fill in your email address.")
    private String email;

    @NotEmpty
    @Size(min = 13, max = 13, message = "Social security number needs 13 characters: YYYYMMDD-NNNN.")
    @NotBlank(message = "Social security number may not be blank.")
    private String ssn;

    @NotEmpty
    @NotBlank(message = "Please choose a username.")
    private String username;

    @NotEmpty
    @NotBlank(message = "Please choose a password.")
    private String password;

    @NotEmpty
    @NotBlank(message = "Please confirm your password.")
    private String confirmPwd;

    public String getFname() { return fname; }

    public void setFname(String fname) { this.fname = fname; }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
    
    @Override
    public String toString() {
        return Util.toString(this);
    }
}
