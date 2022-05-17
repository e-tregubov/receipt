package receipt.calculation;

import org.junit.jupiter.api.RepeatedTest;
import receipt.arguments.ArgsData;
import receipt.discountcards.CardGenerator;
import receipt.discountcards.CardList;
import receipt.discountcards.CardReader;
import receipt.products.ProductGenerator;
import receipt.products.ProductList;
import receipt.products.ProductReader;
import receipt.receipt.Calc;
import static org.junit.jupiter.api.Assertions.*;

public class CalcTest {
//    ProductList productList = new ProductGenerator();
    ProductList productList = new ProductReader(ProductList.FILE_NAME);
//    CardList cardList = new CardGenerator();
    CardList cardList = new CardReader(CardList.FILE_NAME);
    long amount = 0, amountPromo = 0;

    @RepeatedTest(3)
    void TestCalc() throws Exception {

        int id1 = getNum(1,20), qty1 = getNum(1,5),
            id2 = getNum(21,40), qty2 = getNum(3,10),
            id3 = getNum(41,60), qty3= getNum(8,15),
            id4 = getNum(61,80), qty4 = getNum(10,24),
            id5 = getNum(81,100), qty5 = getNum(15,45);

        ArgsData argsData = new ArgsData(new String[] {
//                ArgsData.productsArg + "-" + ProductList.FILE_NAME,
//                ArgsData.cardsArg + "-" + CardList.FILE_NAME,
                getPos(id1, qty1),
                getPos(id2, qty2),
                getPos(id3, qty3),
                getPos(id4, qty4),
                getPos(id5, qty5),
                ArgsData.cardArg + "-" + ""+getNum(1000,1100)
        });

        Calc calc = new Calc(argsData, productList, cardList);

        calcPosition(id1, qty1);
        calcPosition(id2, qty2);
        calcPosition(id3, qty3);
        calcPosition(id4, qty4);
        calcPosition(id5, qty5);

        long discountTotal = (long) Math.ceil((double) (amount - amountPromo)
                * cardList.getValue(argsData.argsObj.cardNumber) / 100);
        long total = amount - discountTotal;

        assertEquals(amount, calc.amount);
        assertEquals(discountTotal, calc.discountTotal);
        assertEquals(total, calc.total);

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
