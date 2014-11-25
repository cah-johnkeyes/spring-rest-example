package hello.service;

import java.util.UUID;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import hello.repository.GreetingStore;
import hello.domain.Greeting;

@Service
public class GreetingService {

    @Autowired
    private GreetingStore greetingStore;

    public List<Greeting> getAllGreetings() {
        return greetingStore.list();
    }

    public Greeting getGreetingById(String id) {
        return greetingStore.get(id);
    }

    public void updateGreeting(String id, String content) {
        Greeting greeting = greetingStore.get(id);
        greeting.setContent(content);
        greetingStore.update(greeting);
    }

    public Greeting createGreeting(String content) {
        Greeting greeting = new Greeting(content);
        greetingStore.add(greeting);
        return greeting;
    }


}
