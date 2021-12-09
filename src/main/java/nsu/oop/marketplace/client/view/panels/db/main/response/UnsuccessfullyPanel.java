package nsu.oop.marketplace.client.view.panels.db.main.response;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import javax.swing.*;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class UnsuccessfullyPanel extends WindowPanel {
    public UnsuccessfullyPanel(ServerResponseListener listener, int width, int height, int posX, int posY) {
        super("/client/UnsuccessfulResponse.png", width, height);
        JButton setButton = ViewUtils.initButton(getPart(width, 0.3), getPart(height, 0.15), getPart(width, 0.646), getPart(height, 0.754), e -> listener.backToStart());
        add(setButton);
        setBounds(posX, posY, width, height);
    }
}
