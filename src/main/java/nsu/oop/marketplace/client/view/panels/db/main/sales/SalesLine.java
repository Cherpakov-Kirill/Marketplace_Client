package nsu.oop.marketplace.client.view.panels.db.main.sales;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class SalesLine extends WindowPanel {

    public SalesLine(int width, int height, int posX, int posY, MarketplaceProto.DBFullSales sales) {
        super("/client/salesTable/SalesTable.png", width, height);
        add(ViewUtils.initLabel(String.valueOf(sales.getProductId()), getPart(width, 0.02), getPart(width, 0.2), getPart(height, 0.7), getPart(width, 0.01), getPart(height, 0.13)));
        add(ViewUtils.initLabel(sales.getProductName(), getPart(width, 0.02), getPart(width, 0.29), getPart(height, 0.7), getPart(width, 0.09), getPart(height, 0.13)));
        add(ViewUtils.initLabel(sales.getDate(), getPart(width, 0.02), getPart(width, 0.15), getPart(height, 0.7), getPart(width, 0.38), getPart(height, 0.13)));
        add(ViewUtils.initLabel(sales.getQuantity(), getPart(width, 0.02), getPart(width, 0.13), getPart(height, 0.7), getPart(width, 0.51), getPart(height, 0.13)));
        add(ViewUtils.initLabel(sales.getAmount(), getPart(width, 0.02), getPart(width, 0.35), getPart(height, 0.7), getPart(width, 0.65), getPart(height, 0.13)));
        setBounds(posX, posY, width, height);
    }
}
