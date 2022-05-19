package receipt;

import receipt.args.*;
import receipt.cards.*;
import receipt.products.*;
import receipt.receipt.*;

public class CheckRunner {

    public static void main(String[] args) throws Exception {

        final ArgsObj argsObj = new ArgsObj(args);

        final String pFile = argsObj.data.productsFileName;
        ProductList pList = (pFile == null) ? new ProductGenerator() : new ProductReader(pFile);

        final String cFile = argsObj.data.cardsFileName;
        CardList cList = (cFile == null) ? new CardGenerator() : new CardReader(cFile);

        final String checkMsg = argsObj.data.check(pList, cList); // проверка позиций и карты на существование
        if (checkMsg != null) {
            System.out.println(checkMsg);
            System.exit(0);
        }

        final Form form = new FormCalc(new Calc(argsObj.data, pList, cList));
        form.print();
        form.save();

        new Http(form);
    }

}
