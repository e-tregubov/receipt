package receipt.receipt;

import receipt.products.Position;
import java.util.ArrayList;

public class Result {

    public static int receiptNumber;
    public ArrayList<Position> positionList = new ArrayList<>();
    public long amount; // общая сумма позиций без скидок
    public long amountPromo; // общая сумма акционных позиций
    public int discountCardValue; // % скидкарты
    public long discountTotal; // общая сумма скидки по карте
    public long total; // общая сумма чека

    public Result() { if (++receiptNumber > 9999) receiptNumber = 1; } // ограничитель номера чека

}
