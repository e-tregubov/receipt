package receipt.args;

import receipt.cards.CardList;
import receipt.products.ProductList;

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {

    public static final String HELP = "help";
    public static final String PRODUCTS_ARG = "pl";
    public static final String CARDS_ARG = "dc";
    public static final String CARD_ARG = "cn";
    public static final int MAX_POSITIONS = 5;
    public static final int MAX_QTY = 999;
    public static final String invalidCardNumberMsg = "Wrong discount card number!";
    public static final String invalidIdMsg = "Invalid argument(s) product ID!";

    public String productsFileName;
    public String cardsFileName;
    public String cardNumber;
    public final Map<Integer, Integer> products = new LinkedHashMap<>();

    public String check(ProductList productList, CardList cardList) {

        if (cardNumber != null && !cardList.contains((cardNumber)))
            return invalidCardNumberMsg;

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            if (!productList.contains(entry.getKey()))
                return invalidIdMsg;
        }
        return null;

    }

}
