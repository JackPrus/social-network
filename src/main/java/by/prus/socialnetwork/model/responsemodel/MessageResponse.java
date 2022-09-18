package by.prus.socialnetwork.model.responsemodel;

public class MessageResponse {
    private Long messageId;
    private String senderName;
    private String receiverName;
    private String text;
    private String eventTime;

    public MessageResponse() {
    }

    public MessageResponse(Long messageId, String senderName, String receiverName, String text, String eventTime) {
        this.messageId = messageId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        this.eventTime = eventTime;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
