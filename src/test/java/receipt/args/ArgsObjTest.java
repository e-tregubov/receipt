package receipt.args;

import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import receipt.cards.CardList;
import receipt.products.ProductList;
import static org.junit.jupiter.api.Assertions.*;

class ArgsObjTest {

    private final static String P_ARG = ArgsObj.PRODUCTS_ARG + "-",
                                C_ARG = ArgsObj.CARDS_ARG + "-",
                                CARD = ArgsObj.CARD_ARG + "-";
    private final static ProductList productList = new ProductList(null);
    private final static CardList cardList = new CardList(null);
    private final static LoremIpsum lorem = LoremIpsum.getInstance();



    @RepeatedTest(64)
    void argsParserTest_Too_Much_Arguments_Exception() {

        String[] args = new String[ArgsObj.MAX_POSITIONS+getRandom(1,256)];

        for (int i = 0; i < ArgsObj.MAX_POSITIONS; i++) {
            args[i] = getPos(getRandom(1,100),getRandom(1,100));
        }
        assertThrows(Exception.class, () -> ArgsObj.parser(args));
    }



    @ParameterizedTest(name = "args \"{0}\" done!")
    @NullSource
    @EmptySource
    @ValueSource(strings = {
            "hi", "5-", "1-0", "25-2x", "2e-12",
            "ee-11", "3-X", "--", CARD+CardList.firstGenCardNumber+1, "1-"+""+ArgsObj.MAX_QTY+1})
    void argsParserTest_One_Invalid_Arg_Exception(String arg) {
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
    void argsParserTest_Two_Invalid_Args_Exception(String arg1, String arg2) {
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
                     cardNumber = ""+getRandom(CardList.firstGenCardNumber, CardList.firstGenCardNumber + 100);

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
        int qty1 = getRandom(1,ArgsObj.MAX_QTY);
        final String arg1 = getPos(id1 ,qty1);

        int id2;
        do { id2 = getRandom(1, 1000000); }
        while (!productList.contains(id2));
        int qty2 = getRandom(1,ArgsObj.MAX_QTY);
        final String arg2 = getPos(id2 ,qty2);

        int cardNum;
        do { cardNum = getRandom(1, 1000000); }
        while (cardList.contains(""+cardNum));
        final String card = "" + cardNum;


        assertEquals(ArgsObj.invalidIdMsg, ArgsObj.check(ArgsObj.parser(new String[]{arg1}), productList, cardList));
        assertEquals(ArgsObj.invalidCardNumberMsg, ArgsObj.check(ArgsObj.parser(new String[]{arg2, CARD+""+card}), productList, cardList));

    }

    private String getRandomValidProductArgsString(int argsQty) {

        StringBuilder args = new StringBuilder();
        String arg;

        for (int argsNum = 0; argsNum < argsQty; argsNum++ ) {
            do arg = getValidArg(); while (!args.toString().contains(arg));
            args.append(arg).append(" ");
        }
        return args.deleteCharAt(args.length()-1).toString();

    }

    private String getValidArg() {
        return getPos(getRandom(1, ProductList.LIST_GEN_LENGTH), getRandom(1, ArgsObj.MAX_QTY));
    }
    private String getInvalidIdArg() {
        return getPos(getRandom(ProductList.LIST_GEN_LENGTH+1, ProductList.LIST_GEN_LENGTH+65536), getRandom(1, ArgsObj.MAX_QTY));
    }
    private String getInvalidQtyArg() {
        return getPos(getRandom(1, ProductList.LIST_GEN_LENGTH), getRandom(ArgsObj.MAX_QTY+1, ArgsObj.MAX_QTY+65536));
    }

    private String getPos(int id, int qty) { return "" + id + "-" + qty; }
    private int getRandom(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }
    private boolean isProduct(Data data, int ...ids) {
        for (int id: ids) { if (!data.products.containsKey(id)) return false; }
        return true;
    }

}
