package nsu.oop.marketplace.client.view.panels.chat.menu;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import java.awt.*;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class MenuPanel extends WindowPanel {
    public MenuPanel(MenuListener listener, String username, int width, int height) {
        super("/chat/Menu.png", width, height);
        add(ViewUtils.initButton(getPart(width,0.1526), getPart(height,0.0533), getPart(width,0.027), getPart(height,0.0093), e -> listener.closeMenuBar()));
        add(ViewUtils.initButton(getPart(width,0.8015), getPart(height,0.0466), getPart(width,0.0763), getPart(height,0.24), e -> listener.closeTheChat()));
        add(ViewUtils.initLabel(username, getPart(width,0.0687), getPart(width,0.5725), getPart(height,0.04), getPart(width,0.3435), getPart(height,0.12)));
        setPreferredSize(new Dimension(width, height));
        Dimension usersPanelSize = getPreferredSize();
        setBounds(0, 0, usersPanelSize.width, usersPanelSize.height);
    }
}
