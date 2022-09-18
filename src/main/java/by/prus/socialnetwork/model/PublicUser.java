package by.prus.socialnetwork.model;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash("PublicUser")
public class PublicUser implements Serializable {

    public static final long serialVersionUID = 1235357345234L;

    @Id
    private Long id;
    private String name;

    public PublicUser() {
    }

    public PublicUser(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
