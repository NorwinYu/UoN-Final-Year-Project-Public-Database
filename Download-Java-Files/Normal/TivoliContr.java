package tivoli.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tivoli.application.TivoliService;
import tivoli.domain.*;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static tivoli.util.ParseDate.parseDate;

/**
 * Contains all the get and post request handling methods.
 */
@Controller
@Scope("session")
public class TivoliContr {
    static final String DEFAULT_PAGE_URL = "/";
    static final String LOGIN_PAGE_URL = "login";
    static final String SIGNUP_PAGE_URL = "signup";

    static final String APPLY_PAGE_URL = "profile";

    static final String ACCESS_DENIED_URL = "403";

    static final String DO_SIGNUP_URL = "do-signup";
    static final String DO_LOGIN_URL = "do-login";
    static final String DO_ADD_EXPERIENCE = "do-exp";
    static final String DO_ADD_AVAILABILITY = "do-avail";
    static final String DO_APPLY_URL = "do-apply";

    static final String APPL_LIST_PAGE_URL = "appllist";

    static final String SEARCH_APPLICATIONS =  "do-search";

    @Autowired
    private TivoliService service;

    /**
     * No page specified. Redirect to the home page.
     *
     * @return A redirect to the home page.
     */
    @GetMapping(DEFAULT_PAGE_URL)
    public String showDefaultView()
    {
        return "redirect:" + LOGIN_PAGE_URL;
    }

    /**
     * A get request for the login page.
     *
     * @param loginForm used in the login form.
     * @return the login page url.
     */
    @GetMapping("/" + LOGIN_PAGE_URL)
    public String showLoginView(LoginForm loginForm, Model model)
    {
        model.addAttribute(new Login());
        return LOGIN_PAGE_URL;
    }


    /**
     * A get request for the signup page.
     *
     * @param signupForm used in the signup form.
     * @return the signup page url.
     */
    @GetMapping("/" + SIGNUP_PAGE_URL)
    public String showSignupView(SignupForm signupForm)
    {
        return SIGNUP_PAGE_URL;
    }

    /**
     * A get request for the show profile page. This is where the client is redirected after successful login.
     *
     * @param availabilityForm used in the availability form.
     * @param experienceForm used in the experience form.
     * @param model Model objects used by profile page.
     * @param auth Authentication object used for getting userdetails.
     * @return the profile page url.
     */
    @GetMapping("/" + APPLY_PAGE_URL)
    public String showProfileView(AvailabilityForm availabilityForm, ExperienceForm experienceForm, Model model, Authentication auth) {

        CurrentUser currentUser =  auth == null ? null : (CurrentUser) auth.getPrincipal();

        List<Experience> experiences = service.getUserExperience(currentUser.getAccount());
        List<Position> posList = service.getPositions();
        List<Availability> availabilities = service.getApplicantAvailability(currentUser.getAccount());

        model.addAttribute("profile", new Profile());
        model.addAttribute("posList", posList);
        model.addAttribute("experiences", experiences);
        model.addAttribute("availabilities", availabilities);

        return APPLY_PAGE_URL;
    }

    /**
     * The add experience form has been submitted.
     *
     * @param experienceForm Content of the add experience form.
     * @param model Model objects used by the profile page.
     * @param bindingResult Validation results for the add experience form.
     * @param auth Authentication object containing the logged in user details.
     * @return A redirect to the profile page url.
     */
    @PostMapping("/" + DO_ADD_EXPERIENCE)
    public String doAddExperience(@Valid ExperienceForm experienceForm, Model model, BindingResult bindingResult, Authentication auth) {

        if (bindingResult.hasErrors())
            return APPLY_PAGE_URL;

        CurrentUser currentUser = auth == null ? null : (CurrentUser) auth.getPrincipal();

        service.addExperience(currentUser.getAccount(), experienceForm.getPosition(), experienceForm.getYearsOfExperience());
        return "redirect:" + APPLY_PAGE_URL;
    }

    /**
     * The add availability form has been submitted.
     *
     * @param availabilityForm Content of the availability form.
     * @param model Model objects used by the profile page.
     * @param bindingResult Validation results of the add availability form.
     * @param auth Authentication object containing user details.
     * @return A redirect to the profile page.
     */
    @PostMapping("/" + DO_ADD_AVAILABILITY)
    public String doAddAvailability(@Valid AvailabilityForm availabilityForm, Model model, BindingResult bindingResult, Authentication auth) {
        if (bindingResult.hasErrors()) return APPLY_PAGE_URL;

        CurrentUser currentUser =  auth == null ? null : (CurrentUser) auth.getPrincipal();

        service.addAvailability(currentUser.getAccount(), availabilityForm.getFromDate(), availabilityForm.getToDate());
        return "redirect:" + APPLY_PAGE_URL;
    }

    /**
     * A get request to the access denied page.
     *
     * @return the access denied page url.
     */
    @GetMapping("/" + ACCESS_DENIED_URL)
    public String showAccessDenied()
    {
        return ACCESS_DENIED_URL;
    }

    /**
     * A get request for the applications list page
     *
     * @param searchForm used in the search applicants form.
     * @param model Model objects used by the application list page.
     * @return the application list page url.
     */
    @GetMapping("/" + APPL_LIST_PAGE_URL)
    public String showApplicationListPage(SearchForm searchForm, Model model)
    {
        model.addAttribute("positions", service.getPositions());
        return APPL_LIST_PAGE_URL;
    }


    /**
     * The apply for job form has been submitted.
     *
     * @param auth Authentication object containing the users details.
     * @param model Model objects used by the profile page.
     * @return the profile page url.
     */
    @PostMapping("/" + DO_APPLY_URL)
    public String doApply(Authentication auth, Model model) {
        CurrentUser currentUser =  auth == null ? null : (CurrentUser) auth.getPrincipal();

        List<Application> applications = service.getApplications(currentUser.getAccount());

        boolean appExists = false;
        for (Application app : applications) {
            if (app.getStatus().equals(Application.unhandled))
                appExists = true;
        }

        if (appExists)
            model.addAttribute("submission", "You already have an application submitted.");
        else {
            model.addAttribute("submission", "Application successfully submitted.");
            service.addApplication(currentUser.getAccount());
        }

        AvailabilityForm availabilityForm = new AvailabilityForm();
        ExperienceForm experienceForm = new ExperienceForm();

        model.addAttribute("availabilityForm", availabilityForm);
        model.addAttribute("experienceForm", experienceForm);

        return showProfileView(availabilityForm, experienceForm, model, auth);
    }


    /**
     * The signup form has been submitted.
     *
     * @param signupForm contents of the signup form.
     * @param bindingResult validation results of the signup form.
     * @param model Model objects used by the signup page.
     * @return the login page url upon successful registration, otherwise the signup page url.
     */
    @PostMapping("/" + DO_SIGNUP_URL)
    public String doSignup(@Valid SignupForm signupForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return SIGNUP_PAGE_URL;

        Account usernameExists = service.findAccountByUsername(signupForm.getUsername());
        if (usernameExists != null) {
            bindingResult.rejectValue("username" ,"username", "This username is not available");
        }

        Account emailExists = service.findAccountByEmail(signupForm.getEmail());
        if (emailExists != null) {
            bindingResult.rejectValue("email" ,"email", "This email is already in use");
        }
        //default value
        String month="01";
        String day="01";

        if(signupForm.getMonthOfBirth().length()==1){
            month="0"+signupForm.getMonthOfBirth();
        }else if(signupForm.getMonthOfBirth().length()==2){
            month=signupForm.getMonthOfBirth();
        }
        if(signupForm.getDayOfBirth().length()==1){
            day="0"+signupForm.getDayOfBirth();
        }else if(signupForm.getDayOfBirth().length()==2){
            day=signupForm.getDayOfBirth();
        }

        String ssn = String.format("%s%s%s-%s",
                signupForm.getYearOfBirth(),
                month,
                day,
                signupForm.getControlDigits());

        Account ssnExists = service.findAccountBySsn(ssn);
        if (ssnExists != null) {
            bindingResult.rejectValue("ssn" ,"ssn", "This ssn already has an account");
        }

        if (ssnExists != null || emailExists != null || usernameExists != null)
        {
            return SIGNUP_PAGE_URL;
        }

        service.registerAccount(signupForm.getName(),
                signupForm.getSurname(),
                signupForm.getUsername(),
                ssn,
                signupForm.getEmail(),
                signupForm.getPassword()
        );

        return "redirect:" + LOGIN_PAGE_URL;
    }


    /**
     * The search applications form has been submitted.
     *
     * @param searchForm contents of the search applications form.
     * @param bindingResult validation results of the form.
     * @param model Model objects used by the application list page.
     * @return the application page url.
     */
    @PostMapping("/" + SEARCH_APPLICATIONS)
    public String searchApplications(@Valid SearchForm searchForm, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            return APPL_LIST_PAGE_URL;
        }

        List<Application> apps = service.getApplications();
        List<Account> byPeriod = null;
        List<Account> byPosition = null;
        List<Application> applications = new ArrayList<>();

        for (Application application : apps) {

            if (!searchForm.getAppDate().equals("--")) {
                Date appDate = parseDate(searchForm.getAppDate());
                if (application.getDate().equals(appDate)) {
                    continue;
                }
            }

            if (!searchForm.getFromDate().equals("--")) {
                System.out.println(searchForm.getFromDate());

                Date fromDate = parseDate(searchForm.getFromDate());
                Date toDate = parseDate(searchForm.getFromDate());

                byPeriod = (byPeriod == null ? service.getApplicantsByPeriod(fromDate, toDate) : byPeriod);
                if (!byPeriod.contains(application.getAccount())) {
                    continue;
                }
            }

            if (!searchForm.getPosition().isEmpty()) {

                byPosition = (byPosition == null ? service.getApplicantsByPosition(searchForm.getPosition()) : byPosition);
                if (!byPosition.contains(application.getAccount())) {
                    continue;
                }
            }

            if (!searchForm.getFname().isEmpty()) {
                if (!searchForm.getLname().isEmpty()) {
                    if (!application.getAccount().getName().equals(searchForm.getFname()) ||
                            !application.getAccount().getSurname().equals(searchForm.getLname())) {
                        continue;
                    }
                } else {
                    if (!application.getAccount().getName().equals(searchForm.getFname())) {
                        continue;
                    }
                }
            }

            if (!searchForm.getLname().isEmpty()) {
                if (!application.getAccount().getSurname().equals(searchForm.getLname())) {
                    continue;
                }
            }

            applications.add(application);
        }

        model.addAttribute("applications", applications);
        return showApplicationListPage(new SearchForm(), model);
    }
}
