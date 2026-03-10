package com.cs.sms.cntrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthCntrl {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

