package by.prus.socialnetwork.service;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.PublicUser;
import by.prus.socialnetwork.model.responsemodel.MessageResponse;
import by.prus.socialnetwork.repository.MessageDao;
import by.prus.socialnetwork.repository.PublicUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicUserService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private PublicUserDao publicUserDao;

    public PublicUser saveUser (PublicUser publicUser){
        return publicUserDao.save(publicUser);
    }

    public List<PublicUser> getAllUsers (){
        return publicUserDao.findAll();
    }


}
