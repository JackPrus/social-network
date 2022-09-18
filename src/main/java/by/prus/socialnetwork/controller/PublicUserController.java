package by.prus.socialnetwork.controller;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.PublicUser;
import by.prus.socialnetwork.model.responsemodel.MessageResponse;
import by.prus.socialnetwork.service.PublicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class PublicUserController {

    @Autowired
    PublicUserService publicUserService;

    @PostMapping
    public PublicUser saveUser(@RequestBody PublicUser publicUser) {
        return publicUserService.saveUser(publicUser);
    }

    @GetMapping()
    public List<PublicUser> getAllUsers() {
        return publicUserService.getAllUsers();
    }


}
