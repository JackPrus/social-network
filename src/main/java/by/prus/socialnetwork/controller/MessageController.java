package by.prus.socialnetwork.controller;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.responsemodel.MessageResponse;
import by.prus.socialnetwork.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/sendMessage/{sender}/{receiver}")
    public MessageResponse sendMessage(@RequestBody Message message, @PathVariable Long sender, @PathVariable Long receiver) {
        return messageService.sendMessage(message, sender, receiver);
    }

    @GetMapping("/usermessages/{sender}")
    public List<MessageResponse> getAllUserMessages(@PathVariable Long sender) {
        return messageService.getAllSentMessages(sender);
    }

    @GetMapping("/dialog/{sender}/{receiver}")
    public List<MessageResponse> getDialog(@PathVariable Long sender, @PathVariable Long receiver) {
        return messageService.getDialog(sender, receiver);
    }

    @GetMapping("/dialog/{sender}/{receiver}/download")
    public String downladDialog(@PathVariable Long sender, @PathVariable Long receiver) throws JsonProcessingException {
        return messageService.downLoadDialog(sender, receiver);
        //скачается файл на стороне клиента на фронте.
    }

}
