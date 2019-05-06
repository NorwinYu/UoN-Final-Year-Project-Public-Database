package org.orcid.frontend.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Declan Newman (declan)
 *         Date: 18/10/2012
 */
@Controller
public class ErrorController extends BaseController {

    @Value("${org.orcid.core.aboutUri:http://about.orcid.org}")
    private String aboutUri;
    
    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/error")
    public ModelAndView error500Page(ModelAndView mav) {
        mav.setViewName("error-500");
        mav.addObject("aboutUri", aboutUri);
        return mav;
    }

    @RequestMapping(value = "/not-found")
    public ModelAndView error404Page(ModelAndView mav) {
        mav.setViewName("error-404");
        return mav;
    }

    @RequestMapping(value = "/oauth/error/redirect-uri-mismatch")
    public ModelAndView oauthErrorInvalidRedirectUri(ModelAndView mav) {
        mav.setViewName("oauth-error-mismatch");      
        return mav;
    }
    
    @RequestMapping(value = "/oauth/error")
    public ModelAndView oauthError(ModelAndView mav) {
        mav.setViewName("oauth-error");      
        return mav;
    }
    
}
