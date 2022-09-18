package by.prus.socialnetwork.model;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@RedisHash("Message")
public class Message implements Serializable {

    public static final long serialVersionUID = 1245658657345234L;

    @Id
    private Long id;
    private Long idSender;
    private Long idReceiver;
    private String text;
    private Date date;

    public Message() {
    }

    public Message(Long idSender, Long idReceiver, String text, Date date) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSender() {
        return idSender;
    }

    public void setIdSender(Long idSender) {
        this.idSender = idSender;
    }

    public Long getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(Long idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
