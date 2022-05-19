package receipt.products;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

public class Products implements ProductList {

    public void save(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Map.Entry<Integer, Product> entry : productList.entrySet()) {
                Product product = entry.getValue();
                StringJoiner line = new StringJoiner(",");
                line.add("" + entry.getKey());
                line.add(product.description);
                line.add("" + product.price);
                line.add("" + product.promoValue);
                line.add("" + product.promoQty);
                writer.write(line + "\n");
            }
            writer.flush();
            System.out.printf("Product list saved in \"%s\" file\n", fileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

}
