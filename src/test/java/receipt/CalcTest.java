package receipt;

import org.junit.jupiter.api.RepeatedTest;
import receipt.args.ArgsObj;
import receipt.args.Data;
import receipt.cards.CardList;
import receipt.products.ProductList;
import receipt.receipt.Calc;
import receipt.receipt.Result;

import static org.junit.jupiter.api.Assertions.*;

public class CalcTest {

    ProductList productList = new ProductList("productList.csv");
    CardList cardList = new CardList("cardList.csv");
    long amount = 0, amountPromo = 0;

    @RepeatedTest(3)
    void CalcTest_5_Positions() throws Exception {

        int id1 = getNum(1,20), qty1 = getNum(1,5),
            id2 = getNum(21,40), qty2 = getNum(3,10),
            id3 = getNum(41,60), qty3= getNum(8,15),
            id4 = getNum(61,80), qty4 = getNum(10,24),
            id5 = getNum(81,100), qty5 = getNum(15,45);

        String[] args = {getPos(id1, qty1),
                         getPos(id2, qty2),
                         getPos(id3, qty3),
                         getPos(id4, qty4),
                         getPos(id5, qty5),
                         ArgsObj.CARD_ARG + "-" + ""+getNum(1000,1100)};

        Data data = new ArgsObj(args).data;
        final Result result = Calc.result(data, productList, cardList);

        calcPosition(id1, qty1);
        calcPosition(id2, qty2);
        calcPosition(id3, qty3);
        calcPosition(id4, qty4);
        calcPosition(id5, qty5);

        long discountTotal = (long) Math.ceil((double) (amount - amountPromo)
                * cardList.getValue(data.cardNumber) / 100);
        long total = amount - discountTotal;

        assertEquals(amount, result.amount);
        assertEquals(discountTotal, result.discountTotal);
        assertEquals(total, result.total);

    }

    void calcPosition(int id, int qty) {

        int price = productList.getProductByID(id).price;
        int promoValue = productList.getProductByID(id).promoValue;
        int promoQty = productList.getProductByID(id).promoQty;
        int total = price * qty;

        if (promoValue > 0 && qty > promoQty) {
            long promoTotal = (long) Math.ceil((double) total * (100 - promoValue) / 100);
            amountPromo += promoTotal;
            amount += promoTotal;
        }
        else amount += total;

    }
    private String getPos(int id, int qty) { return "" + id + "-" + qty; }

    private int getNum(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }

}
