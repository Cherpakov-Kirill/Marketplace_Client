package nsu.oop.marketplace.client.view.panels.db.main.sales;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class SalesTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;

    public SalesTablePanel(int width, int height, int posX, int posY) {
        super("/client/salesTable/SalesTablePanel.png", width, height);
        setBounds(posX, posY, width, height);

        this.widthProductPanel = getPart(width, 0.9);
        this.heightProductPanel = getPart(height, 0.04629);

        scrollPane = ViewUtils.initScrollPane(widthProductPanel + 20, getPart(height, 0.8), getPart(width, 0.05), getPart(height, 0.166));
        add(scrollPane);
    }

    public void updateSaleTable(List<MarketplaceProto.DBFullSales> sales) {
        JPanel saleTable = new JPanel();
        saleTable.setOpaque(false);
        saleTable.setLayout(null);
        saleTable.add(new SalesTableTitle(widthProductPanel, heightProductPanel));
        int i = 1;
        for (MarketplaceProto.DBFullSales sale : sales) {
            saleTable.add(new SalesLine(widthProductPanel, heightProductPanel, 0, i * heightProductPanel, sale));
            i++;
        }
        saleTable.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        saleTable.revalidate();
        scrollPane.setViewportView(saleTable);
    }
}
