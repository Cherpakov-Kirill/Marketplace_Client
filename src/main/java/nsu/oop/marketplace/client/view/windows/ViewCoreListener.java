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
    void requestAddNewUser(String firstName, String secondName, String role, String login, String password);
    void requestSetTask(int id, String task);
    void requestAddNewProduct(String name, String price, String description);
    void requestChangeProductInfo(int id, String name, String price, String description);

    void requestUserList();
    void requestProductList();
}
