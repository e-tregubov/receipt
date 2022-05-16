package receipt.receipt;

import receipt.arguments.ArgsData;
import receipt.discountcards.CardList;
import receipt.products.Position;
import receipt.products.Product;
import receipt.products.ProductList;

import java.util.ArrayList;
import java.util.Map;

public class Calc {

    final ArrayList<Position> positionList = new ArrayList<>();
    static int receiptNumber;
    long amount; // общая сумма позиций без скидок
    long amountPromo; // общая сумма акционных позиций
    final int discountCardValue; // % скидкарты
    final int discountTotal; // общая сумма скидки по карте
    final long total; // общая сумма чека

    public Calc(ArgsData argsData, ProductList productList, CardList cardList) {

        if (++receiptNumber > 9999) receiptNumber = 1; // номер чека каждый раз следующий
        discountCardValue = cardList.getValue(argsData.getCardNumber());

        // перебор аргументов позиций
        for (Map.Entry<Integer, Integer> entry : argsData.getProducts().entrySet()) {

            Product productData = productList.getProductByID(entry.getKey());
            int qty = entry.getValue();
            long total = (long) productData.price * qty;

            Position position = new Position(productData.description, productData.price, qty, productData.promoValue);
            positionList.add(position);

            // акция активна?
            if (productData.promoValue > 0 && qty > productData.promoQty) {
                position.promoTotal = (long) Math.ceil((double) productData.price * qty * (100 - productData.promoValue) / 100);

                amountPromo += position.promoTotal; // накапливаем все скид.позиции
                amount += position.promoTotal; // накапливаем все позиции
            } else amount += total;
        }

        discountTotal = (int) Math.ceil((double) (amount - amountPromo) * discountCardValue / 100);
        total = amount - discountTotal;
    }
}
