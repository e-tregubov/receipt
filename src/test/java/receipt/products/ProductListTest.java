package receipt.products;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {

    ProductList productList = new ProductGenerator();

    @Test
    void testIdExists() {
        assertTrue(productList.idExists(1));
        assertTrue(productList.idExists(32));
        assertTrue(productList.idExists(100));
        assertFalse(productList.idExists(0));
        assertFalse(productList.idExists(101));
        assertFalse(productList.idExists(-10));
    }

    @Test
    void testGetProductByID() {
        Product product = productList.getProductByID(4);
        assertNotEquals(null, product.description);
        assertNotEquals(0, product.price);
    }

    @Test
    void testSave() {
        assertEquals(productList.productList, new ProductReader(productList.FILE_NAME).productList);
    }

}