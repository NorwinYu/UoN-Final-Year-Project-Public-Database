package org.orcid.pojo.ajaxForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.orcid.pojo.Redirect;

public class Claim extends Redirect implements ErrorsInterface, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> errors;

    private Checkbox sendChangeNotifications;

    private Checkbox sendOrcidNews;

    private Checkbox termsOfUse;

    private Visibility activitiesVisibilityDefault;

    private Text password;

    private Text passwordConfirm;

    public Claim() {
        errors = new ArrayList<String>();
        password = new Text();
        passwordConfirm = new Text();
        sendChangeNotifications = new Checkbox();
        sendOrcidNews = new Checkbox();
        termsOfUse = new Checkbox();
        activitiesVisibilityDefault = new Visibility();

    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Text getPassword() {
        return password;
    }

    public void setPassword(Text password) {
        this.password = password;
    }

    public Text getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(Text passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Checkbox getSendChangeNotifications() {
        return sendChangeNotifications;
    }

    public void setSendChangeNotifications(Checkbox sendChangeNotifications) {
        this.sendChangeNotifications = sendChangeNotifications;
    }

    public Visibility getActivitiesVisibilityDefault() {
        return activitiesVisibilityDefault;
    }

    public void setActivitiesVisibilityDefault(Visibility activitiesVisibilityDefault) {
        this.activitiesVisibilityDefault = activitiesVisibilityDefault;
    }

    public Checkbox getSendOrcidNews() {
        return sendOrcidNews;
    }

    public void setSendOrcidNews(Checkbox sendOrcidNews) {
        this.sendOrcidNews = sendOrcidNews;
    }

    public Checkbox getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(Checkbox termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

}
