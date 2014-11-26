package hello.service

import hello.domain.Greeting
import hello.repository.GreetingRepository
import spock.lang.Specification

class GreetingServiceSpec extends Specification {

    GreetingService service
    GreetingRepository greetingRepoMock

    def setup() {
        greetingRepoMock = Mock(GreetingRepository)
        service = new GreetingService(repository: greetingRepoMock)
    }

    def "it returns the proper greeting when given a greeting id"() {
        given:
        def greeting = new Greeting(1, 'content')
        greetingRepoMock.findOne(greeting.id) >> greeting

        when:
        def result = service.getGreetingById(greeting.id)

        then:
        result == greeting
    }

    def "it lists all the greetings"() {
        given:
        def greetings = [
            new Greeting(1, 'one'),
            new Greeting(2, 'two')
        ]
        greetingRepoMock.findAll() >> greetings

        when:
        def result = service.getAllGreetings()

        then:
        result == greetings
    }

    def "it creates new greetings"() {
        given:
        def greeting = new Greeting('content')

        when:
        service.createGreeting(greeting.content)

        then:
        1 * greetingRepoMock.save(greeting)
    }

    def "it updates greetings when given a greeting id and new content"() {
        given:
        def greeting = new Greeting(1, 'content')
        greetingRepoMock.findOne(greeting.id) >> greeting

        when:
        service.updateGreeting(greeting.id, greeting.content)

        then:
        1 * greetingRepoMock.save(greeting)
    }

    def "it deletes a greeting when given a greeting id"() {
        given:
        def greeting = new Greeting(1, 'content')

        when:
        service.deleteGreeting(greeting.id)

        then:
        1 * greetingRepoMock.delete(greeting.id)
    }

}

