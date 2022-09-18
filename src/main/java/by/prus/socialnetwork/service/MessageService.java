package by.prus.socialnetwork.service;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.PublicUser;
import by.prus.socialnetwork.model.responsemodel.MessageResponse;
import by.prus.socialnetwork.repository.MessageDao;
import by.prus.socialnetwork.repository.PublicUserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private PublicUserDao publicUserDao;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

    @Transactional
    public MessageResponse sendMessage(Message message, Long idSender, Long idReceiver){
        message.setIdSender(idSender);
        message.setIdReceiver(idReceiver);
        Message savedMessage  = messageDao.save(message);

        PublicUser sender = publicUserDao.findPublicUserById(idSender);
        PublicUser receiver = publicUserDao.findPublicUserById(idReceiver);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageId(savedMessage.getId());
        messageResponse.setReceiverName(receiver.getName());
        messageResponse.setSenderName(sender.getName());
        messageResponse.setText(savedMessage.getText());
        messageResponse.setEventTime(dateFormat.format(savedMessage.getDate()));
        newMessageNotice(messageResponse, receiver);
        return messageResponse;
    }

    public void newMessageNotice(MessageResponse message, PublicUser recepient) {
        //some service doing Notice of recepient
    }

    public List<MessageResponse> getAllSentMessages(Long userId){
        List<MessageResponse> returnValue = new ArrayList<>();
        List<Message> allMessages = messageDao.findAll();
        List<PublicUser> allUsers = publicUserDao.findAll();
        List<Message> messagesOfUser = allMessages.stream()
                .filter(m-> m.getIdSender().equals(userId))
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());
        PublicUser senderUser = allUsers.stream().filter(user-> user.getId().equals(userId)).findFirst().orElse(null);

        for (Message message : messagesOfUser){
            MessageResponse mr = new MessageResponse();
            PublicUser receverUser = allUsers.stream().filter(m->m.getId().equals(message.getIdReceiver())).findFirst().orElse(null);
            if (senderUser!=null&&receverUser!=null){
                mr.setMessageId(message.getId());
                mr.setSenderName(senderUser.getName());
                mr.setReceiverName(receverUser.getName());
                mr.setText(message.getText());
                mr.setEventTime(dateFormat.format(message.getDate()));
            }
            returnValue.add(mr);
        }
        return returnValue;
    }

    //page 'Messages' of current user
    public List<MessageResponse> getAllReceivedMessages(Long userId){
        List<MessageResponse> returnValue = new ArrayList<>();
        List<Message> allMessages = messageDao.findAll();
        List<PublicUser> allUsers = publicUserDao.findAll();
        List<Message> messagesOfUser = allMessages.stream()
                .filter(m-> m.getIdReceiver().equals(userId))
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());
        PublicUser receverUser = allUsers.stream().filter(user-> user.getId().equals(userId)).findFirst().orElse(null);

        for (Message message : messagesOfUser){
            MessageResponse mr = new MessageResponse();
            PublicUser senderUser = allUsers.stream().filter(m-> m.getId().equals(message.getIdSender())).findFirst().orElse(null);

            if (senderUser!=null&&receverUser!=null){
                mr.setMessageId(message.getId());
                mr.setSenderName(senderUser.getName());
                mr.setReceiverName(receverUser.getName());
                mr.setText(message.getText());
                mr.setEventTime(dateFormat.format(message.getDate()));
            }
            returnValue.add(mr);
        }
        return returnValue;
    }

    //method shows dialog
    public List<MessageResponse> getDialog (Long senderId, Long receiverId){
        List<MessageResponse> returnValue = new ArrayList<>();
        List<Message> allMessages = messageDao.findAll();
        PublicUser sender = publicUserDao.findPublicUserById(senderId);
        PublicUser receiver = publicUserDao.findPublicUserById(receiverId);
        List<Message> filteredMessages = allMessages.stream()
                .filter(m-> m.getIdReceiver().equals(receiverId) || m.getIdReceiver().equals(senderId))
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());
        for (Message message : filteredMessages){
            MessageResponse mr = new MessageResponse();
            if (sender!=null&&receiver!=null){
                mr.setMessageId(message.getId());
                mr.setSenderName(sender.getName());
                mr.setReceiverName(receiver.getName());
                mr.setText(message.getText());
                mr.setEventTime(dateFormat.format(message.getDate()));
            }
            returnValue.add(mr);
        }
        return returnValue;
    }

    public String downLoadDialog(Long sender, Long receiver) throws JsonProcessingException {
        List<MessageResponse> messages = getDialog(sender, receiver);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(messages);
        return json;
    }

    public String deleteMessage(Long id){
        return messageDao.deleteMessage(id);
    }
    public Message getMessageById(Long id){
        return messageDao.findPMessageById(id);
    }
}
