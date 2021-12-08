package nsu.oop.marketplace.client.view.panels.db.main.logs;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class LogTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;

    public LogTablePanel(int width, int height, int posX, int posY) {
        super("/client/logsTable/LogsTablePanel.png", width, height);
        setBounds(posX, posY, width, height);

        this.widthProductPanel = getPart(width, 0.8);
        this.heightProductPanel = getPart(height, 0.04629);

        scrollPane = ViewUtils.initScrollPane(widthProductPanel + 20, getPart(height, 0.8), getPart(width, 0.1), getPart(height, 0.166));
        add(scrollPane);
    }

    public void updateLogTable(List<MarketplaceProto.DBFullLog> logs) {
        JPanel logTable = new JPanel();
        logTable.setOpaque(false);
        logTable.setLayout(null);
        logTable.add(new LogTableTitle(widthProductPanel, heightProductPanel));
        int i = 1;
        for (MarketplaceProto.DBFullLog log : logs) {
            logTable.add(new LogLine(widthProductPanel, heightProductPanel, 0, i * heightProductPanel, log));
            i++;
        }
        logTable.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        logTable.revalidate();
        scrollPane.setViewportView(logTable);
    }
}
