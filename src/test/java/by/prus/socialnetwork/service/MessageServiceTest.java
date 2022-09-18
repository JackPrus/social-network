package by.prus.socialnetwork.service;

import by.prus.socialnetwork.model.Message;
import by.prus.socialnetwork.model.PublicUser;
import by.prus.socialnetwork.model.responsemodel.MessageResponse;
import by.prus.socialnetwork.repository.PublicUserDao;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    PublicUserDao publicUserDao;
    @Autowired
    MessageService messageService;
    PublicUser publicUser1;
    PublicUser publicUser2;
    List<PublicUser> allUsers;
    MessageResponse messageResponse1;
    MessageResponse messageResponse2;
    List<Message> messageList = new ArrayList<>();

    final String NAME_FIRST = "Первый Тест Тестович";
    final String NAME_SECOND = "Второй Тест Тестович";
    final String TEXT_MESSAGE_TEST1 = "Первое тектстовое тестовое сообщение";
    final String TEXT_MESSAGE_TEST2 = "Первое тектстовое тестовое сообщение";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        allUsers = publicUserDao.findAll();

        publicUser1 = createUser(NAME_FIRST, allUsers);
        publicUser2 = createUser(NAME_SECOND, allUsers);

        messageResponse1 = createMessage(TEXT_MESSAGE_TEST1, publicUser1.getId(), publicUser2.getId());
        messageResponse2 = createMessage(TEXT_MESSAGE_TEST2, publicUser2.getId(), publicUser1.getId());
    }

    @AfterEach
    public void tearDown() {
        deleteUser(publicUser1);
        deleteUser(publicUser2);

        deleteMessage(messageResponse1.getMessageId());
        deleteMessage(messageResponse2.getMessageId());
    }

    @Test
    void sendMessage() throws InterruptedException {
        assertNotNull(messageResponse1);
        assertNotNull(messageResponse2);
        assertNotNull(messageResponse1.getMessageId());
        assertNotNull(messageResponse2.getMessageId());
        assertTrue(messageResponse2.getMessageId() > messageResponse1.getMessageId());
        assertEquals(messageResponse1.getSenderName(), NAME_FIRST);
        assertEquals(messageResponse2.getReceiverName(), NAME_FIRST);
    }

    @Test
    void getAllSentMessages() {
        List<MessageResponse> allMessages1 = messageService.getAllSentMessages(publicUser1.getId());
        List<MessageResponse> allMessages2 = messageService.getAllSentMessages(publicUser2.getId());
        assertEquals(allMessages1.size(), 1);
        assertEquals(allMessages2.size(), 1);
    }

    @Test
    void getAllReceivedMessages() {
        List<MessageResponse> allMessages1 = messageService.getAllReceivedMessages(publicUser1.getId());
        List<MessageResponse> allMessages2 = messageService.getAllReceivedMessages(publicUser2.getId());
        assertEquals(allMessages1.size(), 1);
        assertEquals(allMessages2.size(), 1);
    }

    @Test
    void getDialog() {
        List<MessageResponse> allDialog = messageService.getDialog(publicUser1.getId(), publicUser2.getId());
        List<MessageResponse> allRevertDialog = messageService.getDialog(publicUser2.getId(), publicUser1.getId());
        assertEquals(allDialog.size(), 2);
        assertEquals(allRevertDialog.size(), 2);
    }

    private MessageResponse createMessage(String text, Long idSender, Long idReceiver) {
        Message message = new Message();
        message.setText(text);
        MessageResponse messageResponse = messageService.sendMessage(message, idSender, idReceiver);
        return messageResponse;
    }

    private PublicUser createUser(String name, List<PublicUser> allUsers) {
        PublicUser returnUser = allUsers.stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
        if (returnUser == null) {
            returnUser = publicUserDao.save(new PublicUser(name));
            System.out.println(String.format("Создан новый пользователь. Id: %d, name: %s", returnUser.getId(), returnUser.getName()));
        }
        return returnUser;
    }

    private void deleteUser(PublicUser publicUser) {
        if (publicUser != null) {
            Long idToDelete = publicUser.getId();
            publicUserDao.deletePublicUser(idToDelete);
            if (publicUserDao.findPublicUserById(idToDelete) == null) {
                System.out.println(String.format("Пользователь с Id: %d удален", idToDelete));
            } else {
                System.out.println(String.format("Пользователь с Id: %d НЕ УДАЛЕН", idToDelete));
            }
        }
    }

    private void deleteMessage(Long id) {
        messageService.deleteMessage(id);
        Message message = messageService.getMessageById(id);
        if (message == null) {
            System.out.println(String.format("Сообщение с Id: %d удалено", id));
        } else {
            System.out.println(String.format("Пользователь с Id: %d НЕ УДАЛЕНО", id));
        }
    }
}