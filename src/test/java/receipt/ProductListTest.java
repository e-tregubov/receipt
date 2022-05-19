package receipt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import receipt.products.Product;
import receipt.products.ProductGenerator;
import receipt.products.ProductList;
import receipt.products.ProductReader;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {

    ProductList productList = new ProductGenerator();

    @Test
    void testIdExists() {
        assertTrue(productList.contains(1));
        assertTrue(productList.contains(32));
        assertTrue(productList.contains(100));
        assertFalse(productList.contains(0));
        assertFalse(productList.contains(101));
        assertFalse(productList.contains(-10));
    }

    @Test
    void testGetProductByID() {
        Product product = productList.getProductByID(4);
        assertNotEquals(null, product.description);
        assertNotEquals(0, product.price);
    }

    @Test
    void testSave() {
        Assertions.assertEquals(productList.productList, new ProductReader(productList.FILE_NAME).productList);
    }

}