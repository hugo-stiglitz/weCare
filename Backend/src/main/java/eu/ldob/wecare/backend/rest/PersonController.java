package eu.ldob.wecare.backend.rest;

import eu.ldob.wecare.entity.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @RequestMapping("/person")
    public Person getPerson(
            @RequestParam(value = "id")
            long id
    ) {
        return new Person();
    }
}
