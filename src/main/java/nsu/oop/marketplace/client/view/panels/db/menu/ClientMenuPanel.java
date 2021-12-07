package nsu.oop.marketplace.client.view.panels.db.menu;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.client.view.panels.chat.menu.ChatMenuListener;

import java.awt.*;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class ClientMenuPanel extends WindowPanel {
    public ClientMenuPanel(String fileName, String username, int width, int height) {
        super("/client/" + fileName + ".png", width, height);
        add(ViewUtils.initLabel(username, getPart(width, 0.045), getPart(width, 0.5725), getPart(height, 0.04), getPart(width, 0.27), getPart(height, 0.13)));
        setPreferredSize(new Dimension(width, height));
        Dimension usersPanelSize = getPreferredSize();
        setBounds(0, 0, usersPanelSize.width, usersPanelSize.height);
    }
}
