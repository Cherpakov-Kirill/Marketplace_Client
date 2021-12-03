package nsu.oop.marketplace.client.view.windows.chat;

public interface ChatWindowListener {
    void sendNewMessage(String newMessage, String receiverName, String senderName);
    void endChatSession();
}
