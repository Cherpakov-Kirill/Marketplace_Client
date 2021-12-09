package nsu.oop.marketplace.client.view.panels.db.main.tasks;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class TaskLine extends WindowPanel {
    private JButton acceptButton = null;

    public TaskLine(TaskLineListener listener, int width, int height, int posX, int posY, MarketplaceProto.DBFullTask task) {
        super(task.getDone() ? "/client/taskTable/TaskCompletedTable.png" : "/client/taskTable/TaskTable.png", width, height);
        if(!task.getDone()){
            this.acceptButton = ViewUtils.initButton(getPart(width, 0.11), getPart(height, 0.8), getPart(width, 0.89), getPart(height, 0.1), e -> listener.completeTheTask(task.getId()));
            add(acceptButton);
        }
        add(ViewUtils.initLabel(String.valueOf(task.getId()), getPart(width, 0.02), getPart(width, 0.10), getPart(height, 0.7), getPart(width, 0.01), getPart(height, 0.13)));
        add(ViewUtils.initLabel(task.getName(), getPart(width, 0.02), getPart(width, 0.23), getPart(height, 0.7), getPart(width, 0.11), getPart(height, 0.13)));
        add(ViewUtils.initLabel(task.getTask(), getPart(width, 0.02), getPart(width, 0.55), getPart(height, 0.7), getPart(width, 0.345), getPart(height, 0.13)));
        setBounds(posX, posY, width, height);
    }

    public void markAsAccepted() {
        if(acceptButton != null){
            remove(acceptButton);
            setImageIcon("/client/taskTable/TaskCompletedTable.png");
        }
    }
}
