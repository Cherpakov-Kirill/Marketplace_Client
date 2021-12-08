package nsu.oop.marketplace.client.view.panels.db.menu;

import nsu.oop.marketplace.client.view.ViewUtils;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DirectorMenuPanel extends ClientMenuPanel {

    public DirectorMenuPanel(ClientMenuListener listener, String username, int width, int height) {
        super("DirectorMenu", username, width, height);
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.259), e -> listener.showLogs()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.334), e -> listener.showGlobalChanges()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.409), e -> listener.showLogs()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.484), e -> listener.openChat()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.559), e -> listener.showTasks()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.634), e -> listener.showLogs()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.709), e -> listener.showSales()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.784), e -> listener.showProductTable()));
        //add(ViewUtils.initButton(getPart(width,0.8015), getPart(height,0.0466), getPart(width,0.0763), getPart(height,0.24), e -> listener.closeTheChat()));
    }
}
