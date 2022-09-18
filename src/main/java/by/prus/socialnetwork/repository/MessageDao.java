package by.prus.socialnetwork.repository;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.PublicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao {

    @Autowired
    private RedisTemplate template;
    private static final String HASH_KEY = "Message";

    public Message save(Message message) {
        Long idForNewMessage = template.opsForHash().size(HASH_KEY) + 1L;
        message.setId(idForNewMessage);
        message.setDate(new Date());
        if (message.getId() != null &&
                message.getIdSender() != null &&
                message.getIdReceiver() != null &&
                message.getText() != null
        ) {
            template.opsForHash().put(HASH_KEY, idForNewMessage, message);
        }
        return message;
    }

    public List<Message> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Message findPMessageById(Long id) {
        return (Message) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteMessage(Long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "User removed";
    }
}
