package nsu.oop.marketplace.client.view.panels.chat.users;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class UsersPanel extends WindowPanel {
    private final String username;
    private final UsersListener listener;
    private final JScrollPane scrollPane;
    private final Map<String, UserPanel> userPanelsMap;
    private String chosenDialog;
    private final int width;
    private final int height;

    private final int widthUserPanel;
    private final int heightUserPanel;

    public UsersPanel(UsersListener listener, String username, int width, int height) {
        super("/chat/UsersField.png", width, height);
        this.username = username;
        this.width = width;
        this.height = height;
        this.widthUserPanel = width;
        this.heightUserPanel = getPart(height,0.0866);
        userPanelsMap = new HashMap<>();
        this.listener = listener;
        scrollPane = ViewUtils.initScrollPane(width - 3, height - 77, getPart(width,0.0190), getPart(height,0.1));
        add(scrollPane);
        add(ViewUtils.initButton(getPart(width,0.1526), getPart(height,0.0533),getPart(width,0.0458), getPart(height,0.016), e -> listener.openMenuBar()));
        setPreferredSize(new Dimension(width, height));
        Dimension usersPanelSize = getPreferredSize();
        setBounds(0, 0, usersPanelSize.width, usersPanelSize.height);
    }

    public void chooseDialog(String name) {
        if (chosenDialog != null) {
            if (chosenDialog.equals(name)) return;
            userPanelsMap.get(chosenDialog).makeDefaultBackground();
        }
        chosenDialog = name;
        listener.chooseTheDialog(name);
    }

    public void updateUserList(List<String> list) {
        boolean isChosenAnyDialog = false;
        JPanel usersPanel = new JPanel();
        usersPanel.setOpaque(false);
        usersPanel.setLayout(null);
        UserPanel group = new UserPanel(this, "Group.png", "Group Chat", widthUserPanel, heightUserPanel);
        if (chosenDialog == null || chosenDialog.equals("Group Chat")) {
            chosenDialog = "Group Chat";
            group.makeChosenBackground();
            isChosenAnyDialog = true;
        }
        userPanelsMap.put("Group Chat", group);
        group.setPreferredSize(new Dimension(widthUserPanel, heightUserPanel));
        Dimension groupSize = group.getPreferredSize();
        group.setBounds(0, 0, groupSize.width, groupSize.height);
        usersPanel.add(group);
        int number = 1;
        for (String name : list) {
            if(name.equals(username)) continue;
            UserPanel user = new UserPanel(this, "User.png", name, widthUserPanel, heightUserPanel);
            if (chosenDialog.equals(name)) {
                user.makeChosenBackground();
                isChosenAnyDialog = true;
            }
            userPanelsMap.put(name, user);
            user.setPreferredSize(new Dimension(widthUserPanel, heightUserPanel));
            Dimension userSize = user.getPreferredSize();
            user.setBounds(0, number * heightUserPanel, userSize.width, userSize.height);
            usersPanel.add(user);
            number++;
        }
        usersPanel.setPreferredSize(new Dimension(widthUserPanel-5, heightUserPanel*number));
        usersPanel.revalidate();
        scrollPane.setViewportView(usersPanel);
        if (!isChosenAnyDialog) {
            chosenDialog = "Group Chat";
            group.makeChosenBackground();
            listener.chooseTheDialog(chosenDialog);
        }
    }
}
