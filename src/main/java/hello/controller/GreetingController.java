package hello.controller;

import hello.domain.Greeting;
import hello.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/greeting")
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @ResponseBody
    @RequestMapping(value="/", method=RequestMethod.GET)
    public Iterable<Greeting> index() {
        return greetingService.getAllGreetings();
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String create(@RequestParam String content) {
        Greeting greeting = greetingService.createGreeting(content);
        return redirectToGreeting(greeting.getId());
    }

    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Greeting show(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String update(@PathVariable Long id, @RequestParam String content) {
        greetingService.updateGreeting(id, content);
        return redirectToGreeting(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        greetingService.deleteGreeting(id);
    }

    private String redirectToGreeting(Long id) {
        return String.format("redirect:/greeting/%s", id);
    }

}
