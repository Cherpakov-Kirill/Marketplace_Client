package nsu.oop.marketplace.client.view.panels.db.main.tasks;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class TaskTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;
    private final TaskLineListener listener;

    public TaskTablePanel(TaskLineListener listener, int width, int height, int posX, int posY) {
        super("/client/taskTable/TaskTablePanel.png", width, height);
        this.listener = listener;
        setBounds(posX, posY, width, height);

        this.widthProductPanel = getPart(width, 0.8);
        this.heightProductPanel = getPart(height, 0.04629);

        scrollPane = ViewUtils.initScrollPane(widthProductPanel + 20, getPart(height, 0.8), getPart(width, 0.1), getPart(height, 0.166));
        add(scrollPane);
    }

    public void updateTaskTable(List<MarketplaceProto.DBFullTask> tasks) {
        JPanel taskTable = new JPanel();
        taskTable.setOpaque(false);
        taskTable.setLayout(null);
        taskTable.add(new TaskTableTitle(widthProductPanel, heightProductPanel));
        int i = 1;
        for (MarketplaceProto.DBFullTask task : tasks) {
            taskTable.add(new TaskLine(listener, widthProductPanel, heightProductPanel, 0, i * heightProductPanel, task));
            i++;
        }
        taskTable.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        taskTable.revalidate();
        scrollPane.setViewportView(taskTable);
    }
}
