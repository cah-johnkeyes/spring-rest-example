package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import hello.service.GreetingService;
import hello.domain.Greeting;

@Controller
@RequestMapping(value="/greeting")
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @ResponseBody
    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Greeting> list() {
        return greetingService.getAllGreetings();
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String create(@RequestParam String content) {
        Greeting greeting = greetingService.createGreeting(content);
        return redirectToGreeting(greeting.getId());
    }

    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Greeting get(@PathVariable String id) {
        return greetingService.getGreetingById(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String update(@PathVariable String id, @RequestParam String content) {
        greetingService.updateGreeting(id, content);
        return redirectToGreeting(id);
    }

    private String redirectToGreeting(String id) {
        return String.format("redirect:/greeting/%s", id);
    }

}
