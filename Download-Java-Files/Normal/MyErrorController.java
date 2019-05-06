package propra2.leihOrDie.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model){
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());


            if(statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("error", "400");
                model.addAttribute("message", "Der Server kann die Anfrage nicht verstehen. Kontrolliere deine Eingabe!");
                return "error-page";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                model.addAttribute("error", "401");
                model.addAttribute("message", "Du bist nicht autorisiert um diese Aktion auszuführen. Logge dich zuerst ein!");
                return "error-page";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "404");
                model.addAttribute("message", "Diese Seite wurde nicht gefunden!");
                return "error-page";
            }


        }

        model.addAttribute("error", "500");
        model.addAttribute("message", "Der Server hat ein internes Problem. Versuche es später nocheinmal!");
        return "error-page";
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }
}