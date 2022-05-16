package receipt;

import receipt.arguments.*;
import receipt.discountcards.*;
import receipt.products.*;
import receipt.receipt.*;
import java.io.IOException;

public class CheckRunner {

    public static void main(String[] args) throws IOException {

        ArgsData argsData = new ArgsParser(args);

        String pFile = argsData.getProductsFileName();
        ProductList productList = (pFile == null) ? new ProductGenerator() : new ProductReader(pFile);

        String cFile = argsData.getCardsFileName();
        CardList cardList = (cFile == null) ? new CardGenerator() : new CardReader(cFile);

        argsData.check(productList, cardList); // проверка существования товара и скидкарты в БД

        Form form = new FormCalc(new Calc(argsData, productList, cardList));

        form.print();
        form.save();

        new Http(form);
    }

}
