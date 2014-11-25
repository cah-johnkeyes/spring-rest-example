package hello.service;

import hello.domain.Greeting;
import hello.repository.GreetingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {

    @Autowired
    private GreetingStore greetingStore;

    public void setGreetingStore(GreetingStore greetingStore) {
        this.greetingStore = greetingStore;
    }

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
        Greeting greeting = new Greeting(greetingStore.nextId(), content);
        greetingStore.add(greeting);
        return greeting;
    }


}
