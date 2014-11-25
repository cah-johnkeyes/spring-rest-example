package hello.controller

import hello.Application
import hello.domain.Greeting
import hello.service.GreetingService
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
@WebAppConfiguration
//@IntegrationTest(["server.port=3000", "management.port=3001"])
class GreetingControllerSpec extends Specification {

    GreetingService greetingServiceMock
    GreetingController controller
    MockMvc mockMvc

    def setup() {
        greetingServiceMock = Mock(GreetingService)
        controller = new GreetingController(greetingService: greetingServiceMock)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "it has an index action that lists all greetings"() {
        given:
        def greetings = [
            new Greeting('one', 'one'),
            new Greeting('two', 'two')
        ]
        greetingServiceMock.getAllGreetings() >> greetings

        when:
        def response = mockMvc.perform(get('/greeting/'))

        then:
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath('$', hasSize(2)))
            .andExpect(jsonPath('$[0].id', is(greetings[0].id)))
            .andExpect(jsonPath('$[0].content', is(greetings[0].content)))
            .andExpect(jsonPath('$[1].id', is(greetings[1].id)))
            .andExpect(jsonPath('$[1].content', is(greetings[1].content)))
    }

    def "it has a show action that shows a single greeting"() {
        given:
        def greeting = new Greeting('test', 'test')
        greetingServiceMock.getGreetingById(greeting.id) >> greeting

        when:
        def response = mockMvc.perform(get("/greeting/${greeting.id}"))

        then:
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.id', is(greeting.id)))
            .andExpect(jsonPath('$.content', is(greeting.content)))

    }

}
