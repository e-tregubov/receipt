package receipt.receipt;

import receipt.args.Data;
import receipt.cards.CardList;
import receipt.products.Position;
import receipt.products.Product;
import receipt.products.ProductList;
import java.util.Map;

public class Calc {

    public static Result result(Data data, ProductList productList, CardList cardList) {

        Result result = new Result();
        result.discountCardValue = cardList.getValue(data.cardNumber);

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

            result.positionList.add(position);

            // акция активна?
            if (productData.promoValue > 0 && qty > productData.promoQty) {
                position.promoTotal = (long) Math.ceil((double) total * (100 - productData.promoValue) / 100);

                result.amountPromo += position.promoTotal; // накапливаем все скид.позиции
                result.amount += position.promoTotal; // накапливаем все позиции
            }
            else result.amount += total;

        }

        result.discountTotal = (long) Math.ceil((double) (result.amount - result.amountPromo) * result.discountCardValue / 100);
        result.total = result.amount - result.discountTotal;

        return result;
    }

}
