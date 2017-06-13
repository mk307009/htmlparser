package pl.m4.html.htmlparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "To use scanner send via HTTP GET (in URL) message e.g.: localhost:8080/scan?url=http://google.pl&depth=5";
    }
}
