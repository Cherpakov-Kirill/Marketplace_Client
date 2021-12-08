package nsu.oop.marketplace.client.view.windows.chat;

import nsu.oop.marketplace.client.view.panels.chat.dialogue.DialogueListener;
import nsu.oop.marketplace.client.view.panels.chat.dialogue.DialoguePanel;
import nsu.oop.marketplace.client.view.panels.chat.menu.ChatMenuListener;
import nsu.oop.marketplace.client.view.panels.chat.menu.ChatMenuPanel;
import nsu.oop.marketplace.client.view.panels.chat.users.UsersListener;
import nsu.oop.marketplace.client.view.panels.chat.users.UsersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class ChatWindow extends JFrame implements DialogueListener, UsersListener, ChatMenuListener {
    private static final String NAME = "Chat";
    private static final String MENU = "Menu";
    private static final String CLOSE = "Close";
    private String chosenDialog;
    private final String username;

    private final Map<String, DialoguePanel> allDialoguePanels;
    private final UsersPanel usersPanel;
    private final ChatMenuPanel chatMenuPanel;
    private final ChatWindowListener listener;

    private final int widthChatWindow;
    private final int heightChatWindow;
    private final int widthUsersOrMenu;
    private final int heightUsersOrMenu;
    private final int widthDialogue;
    private final int heightDialogue;


    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU);
        menu.add(createMenuItem(CLOSE, e -> closeTheChat()));
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }



    public ChatWindow(ChatWindowListener listener, int windowSize, String username) {
        super(NAME);
        this.listener = listener;
        this.username = username;
        this.widthChatWindow = windowSize;
        this.heightChatWindow = widthChatWindow + 58;
        this.widthUsersOrMenu = getPart(widthChatWindow, 0.349);
        this.heightUsersOrMenu = windowSize;
        this.widthDialogue = getPart(widthChatWindow, 0.6509);
        this.heightDialogue = windowSize;
        this.setSize(widthChatWindow, heightChatWindow);
        setupMenu();
        chatMenuPanel = new ChatMenuPanel(this, username, widthUsersOrMenu, heightUsersOrMenu);
        usersPanel = new UsersPanel(this, username, widthUsersOrMenu, heightUsersOrMenu);
        allDialoguePanels = new HashMap<>();

        DialoguePanel dialoguePanel = initChatPanel("Group Chat");
        allDialoguePanels.put("Group Chat", dialoguePanel);
        setContentOnFrame(getContentPanel(usersPanel, dialoguePanel));
        chosenDialog = "Group Chat";
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeTheChat();
            }
        });
        this.setResizable(false);
        this.setVisible(true);
    }

    private DialoguePanel initChatPanel(String destinationUser) {
        DialoguePanel dialoguePanel = new DialoguePanel(this, username, destinationUser, widthDialogue, heightDialogue);
        dialoguePanel.setPreferredSize(new Dimension(widthDialogue, heightDialogue));
        Dimension chatPanelSize = dialoguePanel.getPreferredSize();
        dialoguePanel.setBounds(widthUsersOrMenu, 0, chatPanelSize.width, chatPanelSize.height);
        return dialoguePanel;
    }

    private JPanel getContentPanel(JPanel bar, DialoguePanel dialoguePanel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(bar);
        panel.add(dialoguePanel);
        return panel;
    }

    private void setContentOnFrame(Container pane) {
        this.setContentPane(pane);
        this.repaint();
        this.setVisible(true);
    }

    ///View
    public void updateChatField(String chatName, String senderName, String message) {
        DialoguePanel dialoguePanel = allDialoguePanels.get(chatName);
        if (dialoguePanel != null) {
            dialoguePanel.addNewMessage(senderName, message);
        } else System.err.println("updateChatField: Dialogue panel not found");
    }

    public void handleAnError(String error) {
        this.setVisible(false);
        JOptionPane.showMessageDialog(this,
                error,
                "Error", JOptionPane.INFORMATION_MESSAGE,
                null);
        closeTheChat();
    }

    public void updateUserList(List<String> list) {
        usersPanel.updateUserList(list);
        List<String> users = allDialoguePanels.keySet().stream().toList();
        for (String name : list) {
            if (!allDialoguePanels.containsKey(name)) {
                allDialoguePanels.put(name, initChatPanel(name));
            } else {
                if (!name.equals(username)) users.remove(name);
            }

        }
        for (String name : users) {
            if (name.equals("Group Chat")) continue;
            allDialoguePanels.remove(name);
        }
    }

    //MenuListener
    @Override
    public void closeMenuBar() {
        setContentOnFrame(getContentPanel(usersPanel, allDialoguePanels.get(chosenDialog)));
    }

    @Override
    public void closeTheChat() {
        this.setVisible(false);
        dispose();
        listener.sendNewMessage("","End the session", username);
    }

    ///ChatPanel
    @Override
    public void sendNewMessage(String newMessage, String receiverName, String senderName) {
        closeMenuBar();
        listener.sendNewMessage(newMessage, receiverName, senderName);
    }

    ///UsersListener
    @Override
    public void chooseTheDialog(String username) {
        setContentOnFrame(getContentPanel(usersPanel, allDialoguePanels.get(username)));
        chosenDialog = username;
    }

    @Override
    public void openMenuBar() {
        setContentOnFrame(getContentPanel(chatMenuPanel, allDialoguePanels.get(chosenDialog)));
    }
}
