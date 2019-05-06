package pl.michal.olszewski.rssaggregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

  @GetMapping("/")
  String mainPage() {
    log.debug("GET main page");
    return "index";
  }

}
