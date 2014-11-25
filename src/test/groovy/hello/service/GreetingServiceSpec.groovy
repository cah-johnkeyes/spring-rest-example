package hello.service

import hello.domain.Greeting
import hello.repository.GreetingStore
import spock.lang.Specification

//@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
class GreetingServiceSpec extends Specification {

    GreetingService service
    GreetingStore greetingStoreMock

    def setup() {
        service = new GreetingService()
        greetingStoreMock = Mock(GreetingStore)
        service.greetingStore = greetingStoreMock
    }

    def "it returns the proper greeting when given a greeting id"() {
        setup:
        def greeting = new Greeting('test', 'test')
        greetingStoreMock.get(greeting.id) >> greeting

        when:
        def result = service.getGreetingById(greeting.id)

        then:
        result == greeting
    }

    def "it lists all the greetings"() {
        setup:
        def greetings = [
            new Greeting('one', 'one'),
            new Greeting('two', 'two')
        ]
        greetingStoreMock.list() >> greetings

        when:
        def result = service.getAllGreetings()

        then:
        result == greetings
    }

    def "it creates new greetings"() {
        setup:
        def greeting = new Greeting('test', 'test')

        when:
        service.createGreeting(greeting.content)

        then:
        1 * greetingStoreMock.nextId() >> greeting.id
        1 * greetingStoreMock.add(greeting)
    }

    def "it updates greetings when given a greeting id and new content"() {
        setup:
        def greeting = new Greeting('test', 'test')
        greetingStoreMock.get(greeting.id) >> greeting

        when:
        service.updateGreeting(greeting.id, greeting.content)

        then:
        1 * greetingStoreMock.update(greeting)
    }

}

