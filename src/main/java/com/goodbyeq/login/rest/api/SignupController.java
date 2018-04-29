package com.goodbyeq.login.rest.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("message", "GoodByeQ");
        model.addAttribute("title", "GoodByeQ");
        return "signup";
    }
}
