package receipt.receipt;

import receipt.args.Data;
import receipt.cards.CardList;
import receipt.products.Position;
import receipt.products.Product;
import receipt.products.ProductList;
import java.util.ArrayList;
import java.util.Map;

public class Calc {

    public static int receiptNumber;
    public final ArrayList<Position> positionList = new ArrayList<>();
    public long amount; // общая сумма позиций без скидок
    public long amountPromo; // общая сумма акционных позиций
    public final int discountCardValue; // % скидкарты
    public final long discountTotal; // общая сумма скидки по карте
    public final long total; // общая сумма чека

    public Calc(Data data, ProductList productList, CardList cardList) {

        if (++receiptNumber > 9999) receiptNumber = 1; // ограничитель номера чека
        discountCardValue = cardList.getValue(data.cardNumber);

        // перебор аргументов позиций и их вычисление
        for (Map.Entry<Integer, Integer> entry : data.products.entrySet()) {

            Product productData = productList.getProductByID(entry.getKey());
            int qty = entry.getValue();
            long total = (long) productData.price * qty;

            Position position = new Position(
                    productData.description,
                    productData.price,
                    qty,
                    productData.promoValue,
                    productData.promoQty);

            positionList.add(position);

            // акция активна?
            if (productData.promoValue > 0 && qty > productData.promoQty) {
                position.promoTotal = (long) Math.ceil((double) total * (100 - productData.promoValue) / 100);

                amountPromo += position.promoTotal; // накапливаем все скид.позиции
                amount += position.promoTotal; // накапливаем все позиции
            }
            else amount += total;

        }

        discountTotal = (long) Math.ceil((double) (amount - amountPromo) * discountCardValue / 100);
        total = amount - discountTotal;
    }
}
