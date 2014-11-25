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

    public Greeting(String content) {
        this(UUID.randomUUID().toString(), content);
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

}
