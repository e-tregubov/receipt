package receipt.args;

import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import receipt.cards.CardGenerator;
import receipt.cards.CardList;
import receipt.products.ProductGenerator;
import receipt.products.ProductList;
import static org.junit.jupiter.api.Assertions.*;

class ArgsObjTest {

    private final static String P_ARG = Data.PRODUCTS_ARG + "-",
                                C_ARG = Data.CARDS_ARG + "-",
                                CARD = Data.CARD_ARG + "-";
    private final static ProductList productList = new ProductGenerator();
    private final static CardList cardList = new CardGenerator();
    private final static LoremIpsum lorem = LoremIpsum.getInstance();



    @RepeatedTest(64)
    void argsParserTest_Too_Much_Arguments_Exception() {

        String[] args = new String[Data.MAX_POSITIONS+getRandom(1,256)];

        for (int i = 0; i < Data.MAX_POSITIONS; i++) {
            args[i] = getPos(getRandom(1,100),getRandom(1,100));
        }
        assertThrows(Exception.class, () -> ArgsObj.parser(args));
    }



    @ParameterizedTest(name = "args \"{0}\" done!")
    @NullSource
    @EmptySource
    @ValueSource(strings = {
            "hi", "5-", "1-0", "25-2x", "2e-12",
            "ee-11", "3-X", "--", CARD+"1020", "1-"+""+Data.MAX_QTY+1})
    void argsParserTest_1_Wrong_Arg_Exception(String arg) {
        assertThrows(Exception.class, () -> ArgsObj.parser(new String[]{arg}));
    }


    @ParameterizedTest(name = "args \"{0} {1}\" done!")
    @CsvSource(value = {
            "1-2, 1-15",
            "7-1, 10",
            "2-4, 5-x",
            "3-11, 2-",
            P_ARG + "file1.csv," + P_ARG + "file2.csv",
            C_ARG + "file1.csv," + C_ARG + "file2.csv",
            CARD + "1234," + CARD + "5678",
    })
    void argsParserTest_2_Wrong_Args_Exception(String arg1, String arg2) {
        assertThrows(Exception.class, () -> ArgsObj.parser(new String[]{arg1, arg2}));
    }


    @RepeatedTest(64)
    void argsParserTest_Right_Args() throws Exception {

        final int id1 = getRandom(1,20), qty1 = getRandom(1,5),
                  id2 = getRandom(21,40), qty2 = getRandom(3,10),
                  id3 = getRandom(41,60), qty3= getRandom(8,15),
                  id4 = getRandom(61,80), qty4 = getRandom(10,24),
                  id5 = getRandom(81,100), qty5 = getRandom(15,45);

        final String fileName1 = lorem.getName(),
                     fileName2 = lorem.getName(),
                     cardNumber = ""+getRandom(CardGenerator.FIRST_CARD_NUMBER, CardGenerator.FIRST_CARD_NUMBER+100);

        final Data data = ArgsObj.parser(new String[] {
                P_ARG+fileName1,
                C_ARG+fileName2,
                getPos(id1, qty1),
                getPos(id2, qty2),
                getPos(id3, qty3),
                getPos(id4, qty4),
                getPos(id5, qty5),
                CARD+cardNumber
        });

        assertEquals(fileName1, data.productsFileName);
        assertEquals(fileName2, data.cardsFileName);
        assertTrue(isProduct(data, id1,id2,id3,id4,id5));
        assertEquals(cardNumber, data.cardNumber);
        assertEquals(5, data.products.size());

    }


    @RepeatedTest(64)
    void ArgsData_check_Test_Method_Wrong_ID_Or_Card_Number() throws Exception {

        int id1;
        do { id1 = getRandom(1, 1000000); }
        while (productList.contains(id1));
        int qty1 = getRandom(1,Data.MAX_QTY);
        final String arg1 = getPos(id1 ,qty1);

        int id2;
        do { id2 = getRandom(1, 1000000); }
        while (!productList.contains(id2));
        int qty2 = getRandom(1,Data.MAX_QTY);
        final String arg2 = getPos(id2 ,qty2);

        int cardNum;
        do { cardNum = getRandom(1, 1000000); }
        while (cardList.contains(""+cardNum));
        final String card = "" + cardNum;


        assertEquals(Data.invalidIdMsg, ArgsObj.parser(new String[]{arg1}).check(productList, cardList));
        assertEquals(Data.invalidCardNumberMsg, ArgsObj.parser(new String[]{arg2, CARD+""+card}).check(productList, cardList));

    }


    private String getPos(int id, int qty) { return "" + id + "-" + qty; }
    private int getRandom(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }
    private boolean isProduct(Data data, int ...ids) {
        for (int id: ids) { if (!data.products.containsKey(id)) return false; }
        return true;
    }

}
