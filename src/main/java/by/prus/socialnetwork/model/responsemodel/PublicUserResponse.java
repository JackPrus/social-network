package by.prus.socialnetwork.model.responsemodel;

import java.util.List;

public class PublicUserResponse {
    private Long userId;
    private String userName;
    private List<MessageResponse> messages;

    public PublicUserResponse() {
    }

    public PublicUserResponse(Long userId, String userName, List<MessageResponse> messages) {
        this.userId = userId;
        this.userName = userName;
        this.messages = messages;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }
}
