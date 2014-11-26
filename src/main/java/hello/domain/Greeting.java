package hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Greeting {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date dateCreated;

    protected Greeting() { }

    public Greeting(String content) {
        this.content = content;
        this.dateCreated = new Date();
    }

    public Greeting(Long id, String content) {
        this(content);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
            && obj instanceof Greeting
            && equals((Greeting) obj);
    }

    public boolean equals(Greeting greeting) {
        if (greeting.getId() == null && getId() == null) {
            return greeting.getContent().equals(getContent());
        }
        return greeting.getId().equals(getId());
    }

}
