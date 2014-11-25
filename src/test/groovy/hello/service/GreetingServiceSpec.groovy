package hello.service

import hello.domain.Greeting
import hello.repository.GreetingStore
import spock.lang.Specification

class GreetingServiceSpec extends Specification {

    GreetingService service
    GreetingStore greetingStoreMock

    def setup() {
        greetingStoreMock = Mock(GreetingStore)
        service = new GreetingService(greetingStore: greetingStoreMock)
    }

    def "it returns the proper greeting when given a greeting id"() {
        given:
        def greeting = new Greeting('test', 'test')
        greetingStoreMock.get(greeting.id) >> greeting

        when:
        def result = service.getGreetingById(greeting.id)

        then:
        result == greeting
    }

    def "it lists all the greetings"() {
        given:
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
        given:
        def greeting = new Greeting('test', 'test')
        greetingStoreMock.nextId() >> greeting.id

        when:
        service.createGreeting(greeting.content)

        then:
        1 * greetingStoreMock.add(greeting)
    }

    def "it updates greetings when given a greeting id and new content"() {
        given:
        def greeting = new Greeting('test', 'test')
        greetingStoreMock.get(greeting.id) >> greeting

        when:
        service.updateGreeting(greeting.id, greeting.content)

        then:
        1 * greetingStoreMock.update(greeting)
    }

}

