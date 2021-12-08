package nsu.oop.marketplace.client.view.panels.db.main.product;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class ProductLine extends WindowPanel {

    public ProductLine(int width, int height, int posX, int posY, MarketplaceProto.DBFullProduct product) {
        super("/client/productTable/ProductTable.png", width, height);
        add(ViewUtils.initLabel(String.valueOf(product.getId()), getPart(width, 0.02), getPart(width, 0.2), getPart(height, 0.7), getPart(width, 0.01), getPart(height, 0.13)));
        add(ViewUtils.initLabel(product.getName(), getPart(width, 0.02), getPart(width, 0.36), getPart(height, 0.7), getPart(width, 0.11), getPart(height, 0.13)));
        add(ViewUtils.initLabel(product.getPrice(), getPart(width, 0.02), getPart(width, 0.14), getPart(height, 0.7), getPart(width, 0.476), getPart(height, 0.13)));
        add(ViewUtils.initLabel(product.getDescription(), getPart(width, 0.02), getPart(width, 0.4), getPart(height, 0.7), getPart(width, 0.61), getPart(height, 0.13)));
        setBounds(posX, posY, width, height);
    }
}
