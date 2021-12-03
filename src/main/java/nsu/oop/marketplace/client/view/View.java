package nsu.oop.marketplace.client.view;

import java.util.List;

public interface View {
    void showErrorAuthMessage();
    void launchChat();
    void launchDBClient();
    void closeView(String closeInfo);
    void updateChatField(String chatName, String senderName, String message);
    void updateUserList(List<String> list);
}
