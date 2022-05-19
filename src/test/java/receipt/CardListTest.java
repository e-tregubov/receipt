package receipt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import receipt.cards.CardGenerator;
import receipt.cards.CardList;
import receipt.cards.CardReader;

import static org.junit.jupiter.api.Assertions.*;

class CardListTest {

    CardList cardList = new CardGenerator();

    @Test
    void testContains() {
        assertFalse(cardList.contains("0"));
        assertFalse(cardList.contains("99"));
        assertFalse(cardList.contains("card"));
        assertFalse(cardList.contains("2048"));
        assertTrue(cardList.contains("1000"));
        assertTrue(cardList.contains("1048"));
        assertTrue(cardList.contains("1100"));
    }

    @Test
    void testGetValue() {
        assertEquals(0, cardList.getValue("1000"));
        assertEquals(16, cardList.getValue("1016"));
        assertEquals(64, cardList.getValue("1064"));
        assertEquals(96, cardList.getValue("1096"));
    }

    @Test
    void testSave() { Assertions.assertEquals(cardList.list, new CardReader(CardList.FILE_NAME).list); }

}