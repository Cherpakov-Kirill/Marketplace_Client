package nsu.oop.marketplace.client.view.panels.db.main.globalChanges;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class GlobalChangeTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;
    private final GlobalChangeLineListener listener;
    private final Map<Integer, GlobalChangeLine> linesMap;

    public GlobalChangeTablePanel(GlobalChangeLineListener listener, int width, int height, int posX, int posY) {
        super("/client/acceptGlobalChanges/AcceptGlobalChangesPanel.png", width, height);
        this.listener = listener;
        this.linesMap = new HashMap<>();
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
            GlobalChangeLine line = new GlobalChangeLine(listener, widthProductPanel, heightProductPanel, 0, i * heightProductPanel, change);
            linesMap.put(change.getId(), line);
            changesTable.add(line);
            i++;
        }
        changesTable.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        changesTable.revalidate();
        scrollPane.setViewportView(changesTable);
    }

    public void updateAcceptChange(int id) {
        System.out.println("id = " + id);
        GlobalChangeLine line = linesMap.get(id);
        if(line != null){
            line.markAsAccepted();
        }
        else{
            System.err.println("GlobalChangeLine line = null");
        }
    }
}
