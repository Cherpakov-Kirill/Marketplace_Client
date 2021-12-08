package nsu.oop.marketplace.client.view.panels.db.main.globalChanges;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class GlobalChangeTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;
    private final GlobalChangeLineListener listener;

    public GlobalChangeTablePanel(GlobalChangeLineListener listener, int width, int height, int posX, int posY) {
        super("/client/acceptGlobalChanges/AcceptGlobalChangesPanel.png", width, height);
        this.listener = listener;
        setBounds(posX, posY, width, height);

        this.widthProductPanel = getPart(width, 0.9);
        this.heightProductPanel = getPart(height, 0.04629);

        scrollPane = ViewUtils.initScrollPane(widthProductPanel + 20, getPart(height, 0.8), getPart(width, 0.05), getPart(height, 0.166));
        add(scrollPane);
    }

    public void updateGlobalChangesTable(List<MarketplaceProto.DBFullChanges> changes) {
        JPanel changesTable = new JPanel();
        changesTable.setOpaque(false);
        changesTable.setLayout(null);
        changesTable.add(new GlobalChangeTableTitle(widthProductPanel, heightProductPanel));
        int i = 1;
        for (MarketplaceProto.DBFullChanges change : changes) {
            changesTable.add(new GlobalChangeLine(listener, widthProductPanel, heightProductPanel, 0, i * heightProductPanel, change));
            i++;
        }
        changesTable.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        changesTable.revalidate();
        scrollPane.setViewportView(changesTable);
    }
}
