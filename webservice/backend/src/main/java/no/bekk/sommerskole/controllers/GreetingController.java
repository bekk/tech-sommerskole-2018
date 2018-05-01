package no.bekk.sommerskole.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping(value = "/greeting", produces = "text/plain")
    @ResponseBody
    public String greeting(@RequestParam(name="name", defaultValue="World") String name) {
        return String.format("Hello, %s!", name);
    }
}
