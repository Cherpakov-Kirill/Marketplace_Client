package nsu.oop.marketplace.client.view.panels.chat.users;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import javax.swing.*;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class UserPanel extends WindowPanel {
    private final UsersPanel parentPanel;
    private final String filename;
    private final int width;
    private final int height;

    public UserPanel(UsersPanel panel, String filename, String username, int width, int height) {
        super("/chat/" + filename, width, height);
        this.width = width;
        this.height = height;
        this.filename = filename;
        parentPanel = panel;
        JLabel name = ViewUtils.initLabel(username, getPart(width, 0.0610), getPart(width, 0.7633), getPart(height, 0.7692), getPart(width, 0.2671), getPart(height, 0.0769));
        add(name);
        JButton button = ViewUtils.initButton(getPart(width, 0.9923), getPart(height, 0.9692), getPart(width, 0.0076), getPart(height, 0.0307), e -> {
            makeChosenBackground();
            parentPanel.chooseDialog(username);
        });
        add(button);
    }

    public void makeChosenBackground() {
        this.setImageIcon("/chat/" + filename.substring(0, filename.indexOf('.')) + "Chosen.png");
    }

    public void makeDefaultBackground() {
        this.setImageIcon("/chat/" + filename);
    }
}
