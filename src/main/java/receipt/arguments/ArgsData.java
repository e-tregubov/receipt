package receipt.arguments;

import receipt.discountcards.CardList;
import receipt.products.ProductList;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ArgsData {

    int MAX_POSITIONS = 5; // максимальное кол-во позиций в чеке
    int MAX_QTY = 999; // максимальное кол-во товара в позиции чека

    String PRODUCTS = "pl", CARDS = "dc", CARD = "cn", HELP = "help";

    Map<Integer, Integer> PRODUCT_ARGS = new LinkedHashMap<>();

    default Map<Integer, Integer> getProducts() { return PRODUCT_ARGS; } // возвращает аргументы позиций

    String getProductsFileName();

    String getCardsFileName();

    String getCardNumber();

    void check(ProductList productList, CardList cardList);

}
