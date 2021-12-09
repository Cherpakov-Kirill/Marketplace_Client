package nsu.oop.marketplace.client.view.windows;

public interface ViewCoreListener {
    void logIn(String name, String password);
    void launchChat();
    void sendChatMessage(String newMessage, String receiverName, String senderName);
    void exit(String message);
    void requestFullProductTable();
    void requestFullLogTable();
    void requestFullTaskTable();
    void requestFullSalesTable();
    void requestFullGlobalChangesTable();

    void requestAcceptTheChange(int id);
    void requestCompleteTheTask(int id);
}
