package dev.roniel.hello;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@RestController
// we allow cors requests from our frontend environment
// note the curly braces that creates an array of strings ... required by the annotation
@CrossOrigin(origins =  {"${app.dev.frontend.local}"})
public class HelloController {

    // simple GET response for our example purpose, we return a JSON structure
    @RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> index() {
        return Collections.singletonMap("message", "Greetings from Spring Boot!");
    }

}
