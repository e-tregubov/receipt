package receipt.args;

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
    public String productsFileName, cardsFileName, cardNumber;
    public final Map<Integer, Integer> products = new LinkedHashMap<>();
}
