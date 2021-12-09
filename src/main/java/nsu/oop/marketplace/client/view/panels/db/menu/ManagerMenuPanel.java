package nsu.oop.marketplace.client.view.panels.db.menu;

import nsu.oop.marketplace.client.view.ViewUtils;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class ManagerMenuPanel extends ClientMenuPanel {

    public ManagerMenuPanel(ClientMenuListener listener, String username, int width, int height) {
        super("ManagerMenu", username, width, height);
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.259), e -> listener.showChangeProductInfo()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.334), e -> listener.showAddNewProduct()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.409), null/*e -> listener.showAddNewUser()*/));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.484), e -> listener.openChat()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.559), e -> listener.showTasks()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.634), e -> listener.showProductTable()));
        add(ViewUtils.initButton(getPart(width,0.9), getPart(height,0.06), getPart(width,0.027), getPart(height,0.709), null/*e -> listener.showProductTable()*/));
    }
}
