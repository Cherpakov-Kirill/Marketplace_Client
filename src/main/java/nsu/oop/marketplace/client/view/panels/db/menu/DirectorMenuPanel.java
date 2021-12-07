package nsu.oop.marketplace.client.view.panels.db.menu;

import nsu.oop.marketplace.client.view.ViewUtils;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DirectorMenuPanel extends ClientMenuPanel {

    public DirectorMenuPanel(ClientMenuListener listener, String username, int width, int height) {
        super("DirectorMenu", username, width, height);
        add(ViewUtils.initButton(getPart(width,0.1526), getPart(height,0.0533), getPart(width,0.027), getPart(height,0.0093), e -> listener.showLogs()));
        //add(ViewUtils.initButton(getPart(width,0.8015), getPart(height,0.0466), getPart(width,0.0763), getPart(height,0.24), e -> listener.closeTheChat()));
    }
}
