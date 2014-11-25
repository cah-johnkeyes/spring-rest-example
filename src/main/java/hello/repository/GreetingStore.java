package hello.repository;

import hello.domain.Greeting;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class GreetingStore {

    private HashMap<String, Greeting> greetings;

    public GreetingStore() {
        greetings = new HashMap<>();
    }

    public Greeting get(String id) {
        return greetings.get(id);
    }

    public void add(Greeting greeting) {
        if (greeting != null && greeting.getId() != null) {
            greetings.put(greeting.getId(), greeting);
        }
    }

    public void update(Greeting greeting) {
        if (greeting != null && greeting.getId() != null) {
            greetings.get(greeting.getId()).setContent(greeting.getContent());
        }
    }

    public List<Greeting> list() {
        return new ArrayList<>(greetings.values());
    }

    public String nextId() {
        return UUID.randomUUID().toString();
    }

}
