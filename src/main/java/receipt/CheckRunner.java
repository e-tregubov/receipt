package receipt;

import receipt.arguments.*;
import receipt.discountcards.*;
import receipt.products.*;
import receipt.receipt.*;
import java.io.IOException;

public class CheckRunner {

    public static void main(String[] args) throws IOException {

        ArgsData argsData = new ArgsData(args);

        String pFile = argsData.argsObj.productsFile;
        ProductList productList = (pFile == null) ? new ProductGenerator() : new ProductReader(pFile);

        String cFile = argsData.argsObj.cardsFile;
        CardList cardList = (cFile == null) ? new CardGenerator() : new CardReader(cFile);

        argsData.check(productList, cardList); // проверка позиций и карты на существование

        Form form = new FormCalc(new Calc(argsData, productList, cardList));

        form.print();
        form.save();

        new Http(form);
    }

}
