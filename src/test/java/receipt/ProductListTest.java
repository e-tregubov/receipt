package receipt;

import org.junit.jupiter.api.Test;
import receipt.products.Product;
import receipt.products.ProductList;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {

    ProductList productList = new ProductList(null);

    @Test
    void IdExistsTest() {
        assertTrue(productList.contains(1));
        assertTrue(productList.contains(32));
        assertTrue(productList.contains(100));
        assertFalse(productList.contains(0));
        assertFalse(productList.contains(101));
        assertFalse(productList.contains(-10));
    }

    @Test
    void GetProductByIdTest() {
        Product product = productList.getProductByID(4);
        assertNotEquals(null, product.description);
        assertNotEquals(0, product.price);
    }

}