package receipt.arguments;

import org.junit.jupiter.api.Test;
import receipt.discountcards.CardGenerator;
import receipt.discountcards.CardList;
import receipt.products.ProductGenerator;
import receipt.products.ProductList;
import static org.junit.jupiter.api.Assertions.*;

class ArgsDataTest {

    @Test
    void parser() throws Exception {

        assertThrows(Exception.class, () -> new ArgsData(new String[]{""}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"hi"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"5-"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"5-0"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"25-2x"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"2e-12"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"3-2","3-15"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{ArgsData.productsArg+"-file1", ArgsData.productsArg+"-file2", "1-1"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{ArgsData.cardsArg+"-file1", ArgsData.cardsArg+"-file2", "1-1"}));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{ArgsData.cardArg+"-1234", ArgsData.cardArg+"-5678", "1-1"}));

        ArgsData argsData = new ArgsData(new String[] {"1-2"});
        assertNull(argsData.argsObj.productsFile);
        assertNull(argsData.argsObj.cardsFile);
        assertNull(argsData.argsObj.cardNumber);
        assertTrue(argsData.argsObj.products.containsKey(1));
        assertEquals(1, argsData.argsObj.products.size());
        assertEquals(2, argsData.argsObj.products.get(1));

        argsData = new ArgsData(new String[] {"21-5", "34-8", "66-15", ArgsData.cardArg+"-12345"});
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

        argsData = new ArgsData(new String[] {ArgsData.productsArg+"-file1.txt", ArgsData.cardsArg+"-file2.csv", "77-4", "41-8"});
        assertEquals("file1.txt", argsData.argsObj.productsFile);
        assertEquals("file2.csv", argsData.argsObj.cardsFile);
        assertNull(argsData.argsObj.cardNumber);
        assertFalse(argsData.argsObj.products.containsKey(1));
        assertEquals(2, argsData.argsObj.products.size());
        assertEquals(4, argsData.argsObj.products.get(77));

    }

    @Test
    void check() {

        ProductList productList = new ProductGenerator();
        CardList cardList = new CardGenerator();

        assertThrows(Exception.class, () -> new ArgsData(new String[]{"101-1"}).check(productList, cardList));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"2048-5"}).check(productList, cardList));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"1-1", ArgsData.cardArg+"-0"}).check(productList, cardList));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"1-1", ArgsData.cardArg+"-999"}).check(productList, cardList));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"1-1", ArgsData.cardArg+"-2048"}).check(productList, cardList));
        assertThrows(Exception.class, () -> new ArgsData(new String[]{"1-1", ArgsData.cardArg+"-card"}).check(productList, cardList));

    }

}