package nsu.oop.marketplace.client.view.panels.db.main.product;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;


public class ProductTablePanel extends WindowPanel {
    private final int widthProductPanel;
    private final int heightProductPanel;
    private final JScrollPane scrollPane;

    public ProductTablePanel(int width, int height, int posX, int posY) {
        super("/client/productTable/AllProductTable.png", width, height);
        setBounds(posX, posY, width, height);

        this.widthProductPanel = getPart(width, 0.8);
        this.heightProductPanel = getPart(height, 0.04629);

        scrollPane = ViewUtils.initScrollPane(widthProductPanel + 20, getPart(height, 0.8), getPart(width, 0.1), getPart(height, 0.166));
        add(scrollPane);
    }

    public void updateProductTable(List<MarketplaceProto.DBFullProduct> products) {
        JPanel productsPanel = new JPanel();
        productsPanel.setOpaque(false);
        productsPanel.setLayout(null);
        productsPanel.add(new ProductTableTitle(widthProductPanel, heightProductPanel));
        int i = 1;
        for (MarketplaceProto.DBFullProduct product : products) {
            productsPanel.add(new ProductLine(widthProductPanel, heightProductPanel, 0, i * heightProductPanel, product));
            i++;
        }
        productsPanel.setPreferredSize(new Dimension(widthProductPanel, heightProductPanel * i));
        productsPanel.revalidate();
        scrollPane.setViewportView(productsPanel);
    }
}
