package receipt.products;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

public class ProductGenerator extends Products {

    public ProductGenerator() {
        final int LIST_LENGTH = 100;
        final int MAX_DESCRIPTION_WORDS = 4;
        final int MAX_PRICE = 100;
        final int MIN_PROMO = 10; // минимальный % акционной скидки
        final int MAX_PROMO = 50; // максимальный
        final int MIN_QTY_PROMO = 5;
        final int MAX_QTY_PROMO = 10;
        Lorem lorem = LoremIpsum.getInstance();

        for (int id = 0; id < LIST_LENGTH; ) {
            Product product = new Product();
            String name = lorem.getWords(1, MAX_DESCRIPTION_WORDS);
            product.description = name.substring(0, 1).toUpperCase() + name.substring(1);
            product.price = 1 + (int) (Math.random() * 100 * MAX_PRICE);
            if (Math.random() < 0.7) {
                product.promoValue = MIN_PROMO + (int) (Math.random() * (MAX_PROMO - MIN_PROMO));
                product.promoQty = MIN_QTY_PROMO + (int) (Math.random() * (MAX_QTY_PROMO - MIN_QTY_PROMO));
            }
            productList.put(++id, product);
        }
        System.out.println("\nProduct list has been successfully generated");
//        super.save(FILE_NAME);
    }

}
