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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
@WebAppConfiguration
class GreetingControllerSpec extends Specification {

    static String path(Long id = null) {
        return "/greeting/${id ?: ''}"
    }

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
            new Greeting(1, 'one'),
            new Greeting(2, 'two')
        ]
        greetingServiceMock.getAllGreetings() >> greetings

        when:
        def response = mockMvc.perform(get(path()))

        then:
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath('$', hasSize(2)))
            .andExpect(jsonPath('$[0].id', is(greetings[0].id.toInteger())))
            .andExpect(jsonPath('$[0].content', is(greetings[0].content)))
            .andExpect(jsonPath('$[1].id', is(greetings[1].id.toInteger())))
            .andExpect(jsonPath('$[1].content', is(greetings[1].content)))
    }

    def "it has a show action that shows a single greeting"() {
        given:
        def greeting = new Greeting(1, 'content')
        greetingServiceMock.getGreetingById(greeting.id) >> greeting

        when:
        def response = mockMvc.perform(get(path(greeting.id)))

        then:
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.id', is(greeting.id.toInteger())))
            .andExpect(jsonPath('$.content', is(greeting.content)))

    }

    def "it has an add action that creates a greeting"() {
        given:
        def greeting = new Greeting(1, 'content')
        greetingServiceMock.createGreeting(greeting.content) >> greeting

        when:
        def response = mockMvc.perform(post(path())
            .param('content', greeting.content))

        then:
        response
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(path(greeting.id)))
    }

    def "it has an update action that updates a greeting's content"() {
        given:
        def greeting = new Greeting(1, 'content')
        greetingServiceMock.updateGreeting(greeting.id, greeting.content)

        when:
        def response = mockMvc.perform(put(path(greeting.id))
            .param('content', greeting.content))

        then:
        response
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(path(greeting.id)))
    }

    def "it has a delete action that deletes a greeting"() {
        given:
        def greeting = new Greeting(1, 'content')

        when:
        def response = mockMvc.perform(delete(path(greeting.id)))

        then:
        response.andExpect(status().isOk());
    }

}
