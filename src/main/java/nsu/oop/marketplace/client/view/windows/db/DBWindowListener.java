package nsu.oop.marketplace.client.view.windows.db;

public interface DBWindowListener {
    void closeClientSession(String closeInfo);
    void logOut();
    void openChat();
    void requestFullProductTable();
    void requestFullLogTable();
    void requestFullTaskTable();
    void requestFullSalesTable();
    void requestFullGlobalChangesTable();

    void requestAcceptTheChange(int id);
    void requestCompleteTheTask(int id);

    void requestAddNewUser(String firstName, String secondName, String role, String login, String password);
    void requestUserList();
    void requestSetTask(int id, String task);
    void requestAddNewProduct(String name, String price, String description);
    void requestChangeProductInfo(int id, String name, String price, String description);
    void requestProductList();
}
