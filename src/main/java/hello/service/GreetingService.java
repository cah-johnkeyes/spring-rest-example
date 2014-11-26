package hello.service;

import hello.domain.Greeting;
import hello.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Autowired
    private GreetingRepository repository;

    public Iterable<Greeting> getAllGreetings() {
        return repository.findAll();
    }

    public Greeting getGreetingById(Long id) {
        return repository.findOne(id);
    }

    public void updateGreeting(Long id, String content) {
        Greeting greeting = repository.findOne(id);

        if (greeting != null) {
            greeting.setContent(content);
            repository.save(greeting);
        }
    }

    public Greeting createGreeting(String content) {
        Greeting greeting = new Greeting(content);
        repository.save(greeting);
        return greeting;
    }

    public void deleteGreeting(Long id) {
        repository.delete(id);
    }

}
