package nsu.oop.marketplace.client.view.windows;

public interface ViewCoreListener {
    void logIn(String name, String password);
    void launchChat();
    void sendChatMessage(String newMessage, String receiverName, String senderName);
    void exit(String message);
}
