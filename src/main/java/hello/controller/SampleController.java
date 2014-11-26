package hello.controller;

import hello.domain.Book;
import hello.repository.BookRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @Autowired
    private BookRepository repository;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        Book book = new Book("Of Mice and Men");

        repository.save(book);

        return "done";
    }

}
