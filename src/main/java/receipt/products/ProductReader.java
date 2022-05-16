package receipt.products;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductReader extends Products {

    public ProductReader(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String productLine;
            while ((productLine = br.readLine()) != null) {
                String[] elements = productLine.split(",");
                Product product = new Product();
                product.description = elements[1];
                product.price = Integer.parseInt(elements[2]);
                product.promoValue = Integer.parseInt(elements[3]);
                product.promoQty = Integer.parseInt(elements[4]);
                productList.put(Integer.parseInt(elements[0]), product);
            }
        }
        catch (IOException ex) {
            System.out.printf("ERROR: file \"%s\" doesn't exists!", fileName);
            System.exit(0);
        }
        System.out.printf("\nProduct list was loaded from \"%s\"\n", fileName);
    }
}
