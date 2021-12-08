package nsu.oop.marketplace.client.view;

import nsu.oop.marketplace.inet.MarketplaceProto;

import java.util.List;

public interface View {
    void showErrorAuthMessage();
    void launchChat();
    void launchDBClient(MarketplaceProto.UserType type, String firstName, String secondName);
    void closeView(String closeInfo);
    void updateChatField(String chatName, String senderName, String message);
    void updateUserList(List<String> list);
}
