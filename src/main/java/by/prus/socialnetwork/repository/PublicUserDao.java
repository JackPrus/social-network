package by.prus.socialnetwork.repository;

import by.prus.socialnetwork.model.PublicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublicUserDao {

    @Autowired
    private RedisTemplate template;
    private static final String HASH_KEY = "PublicUser";

    public PublicUser save(PublicUser publicUser) {
        Long idForNewUser = template.opsForHash().size(HASH_KEY) + 1L;
        publicUser.setId(idForNewUser);
        if (publicUser.getId() != null && publicUser.getName() != null)
            template.opsForHash().put(HASH_KEY, idForNewUser, publicUser);
        return publicUser;
    }

    public List<PublicUser> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public PublicUser findPublicUserById(Long id) {
        return (PublicUser) template.opsForHash().get(HASH_KEY, id);
    }

    public String deletePublicUser(Long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "User removed";
    }

}
