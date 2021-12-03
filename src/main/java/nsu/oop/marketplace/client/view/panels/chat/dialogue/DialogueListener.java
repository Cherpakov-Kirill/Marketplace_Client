package nsu.oop.marketplace.client.view.panels.chat.dialogue;

public interface DialogueListener {
    void sendNewMessage(String newMessage, String receiverName, String senderName);
}
