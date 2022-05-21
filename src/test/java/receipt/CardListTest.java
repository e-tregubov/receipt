package receipt;

import org.junit.jupiter.api.Test;
import receipt.cards.CardList;

import static org.junit.jupiter.api.Assertions.*;

class CardListTest {

    CardList cardList = new CardList(null);

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

}