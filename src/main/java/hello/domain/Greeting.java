package hello.domain;

import java.util.Date;
import java.util.UUID;

public class Greeting {

    private String id;
    private String content;
    private Date dateCreated;

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
        this.dateCreated = new Date();
    }

    public String getId() {
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
        return greeting.getId().equals(getId());
    }

}
