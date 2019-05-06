package tivoli.presentation.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains all exception handling methods.
 */
@Controller
@ControllerAdvice
public class ExceptionHandlers implements ErrorController {
    public static final String ERROR_PAGE_URL = "error";
    public static final String ERROR_PATH = "failure";

    public static final String ERROR_TYPE = "errorType";
    public static final String ERROR_INFO = "errorInfo";

    public static final String ERROR_LOGIN_FAILED = "Login failed";
    public static final String ERROR_SIGNUP_FAILED = "Signup failed";

    public static final String ERROR_GENERIC_TYPE = "Operation Failed";
    public static final String ERROR_GENERIC_INFO = "Sorry, something went wrong. Please try again.";

    /**
     * Generic exception handler. Will be used if no more specific exception was produced.
     *
     * @param model Model objects usd by the error page.
     * @return the error page url.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Model model) {
        model.addAttribute(ERROR_TYPE, ERROR_GENERIC_TYPE);
        model.addAttribute(ERROR_INFO, ERROR_GENERIC_INFO);
        return ERROR_PAGE_URL;
    }

    /**
     * Handles http errors.
     * @param request the request object.
     * @param response the response object.
     * @param model Model objects used by the error page.
     * @return the error page url.
     */
    @GetMapping("/" + ERROR_PATH)
    public String handleHttpError(HttpServletRequest request, HttpServletResponse response, Model model) {
        int statusCode = Integer.parseInt(extractHttpStatusCode(request));
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute(ERROR_TYPE, "The page could not be found.");
            model.addAttribute(ERROR_INFO, "Sorry,  the page you requested could not be found.");
            response.setStatus(statusCode);
        } else {
            model.addAttribute(ERROR_TYPE, ERROR_GENERIC_TYPE);
            model.addAttribute(ERROR_INFO, ERROR_GENERIC_INFO);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ERROR_PAGE_URL;
    }

    private String extractHttpStatusCode(HttpServletRequest request) {
        return request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
    }

    @Override
    public String getErrorPath() {
        return "/" + ERROR_PATH;
    }
}
