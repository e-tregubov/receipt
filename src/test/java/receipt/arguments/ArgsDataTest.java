package receipt.arguments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgsDataTest {

    @Test
    void parser() {

        ArgsData argsData = new ArgsData(new String[] {"1-2"});
        assertNull(argsData.argsObj.productsFile);
        assertNull(argsData.argsObj.cardsFile);
        assertNull(argsData.argsObj.cardNumber);
        assertTrue(argsData.argsObj.products.containsKey(1));
        assertEquals(1, argsData.argsObj.products.size());
        assertEquals(2, argsData.argsObj.products.get(1));

        argsData = new ArgsData(new String[] {"21-5", "34-8", "66-15", String.format("%s-12345", argsData.CARD)});
        assertNull(argsData.argsObj.productsFile);
        assertNull(argsData.argsObj.cardsFile);
        assertEquals("12345", argsData.argsObj.cardNumber);
        assertFalse(argsData.argsObj.products.containsKey(11));
        assertTrue(argsData.argsObj.products.containsKey(21));
        assertTrue(argsData.argsObj.products.containsKey(34));
        assertTrue(argsData.argsObj.products.containsKey(66));
        assertEquals(3, argsData.argsObj.products.size());
        assertEquals(5, argsData.argsObj.products.get(21));
        assertEquals(8, argsData.argsObj.products.get(34));
        assertEquals(15, argsData.argsObj.products.get(66));

        argsData = new ArgsData(new String[] {String.format("%s-file1.txt",
                argsData.PRODUCTS), String.format("%s-file2.csv", argsData.CARDS), "77-4", "41-8"});
        assertEquals("file1.txt", argsData.argsObj.productsFile);
        assertEquals("file2.csv", argsData.argsObj.cardsFile);
        assertNull(argsData.argsObj.cardNumber);
        assertFalse(argsData.argsObj.products.containsKey(1));
        assertEquals(2, argsData.argsObj.products.size());
        assertEquals(4, argsData.argsObj.products.get(77));
    }

}