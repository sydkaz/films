package ae.innovativesolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping(value = "/")
    public String redirect() {
        return "redirect:/film";
    }
}