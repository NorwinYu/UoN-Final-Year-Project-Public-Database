package propra2.leihOrDie.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelpController {

    @GetMapping("/help")
    public String showHelp(){

        return "help-page";
    }
}
