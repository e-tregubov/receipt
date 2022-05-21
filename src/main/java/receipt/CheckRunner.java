package receipt;

import receipt.args.*;
import receipt.cards.CardList;
import receipt.products.ProductList;
import receipt.receipt.*;


public class CheckRunner {

    public static void main(String[] args) throws Exception {

        // разбираем аргументы
        Data data = new ArgsObj(args).data;

        // получаем объекты данных
        ProductList pList = new ProductList(data.productsFileName);
        CardList cList = new CardList(data.cardsFileName);

        // проверка указаний аргументов в данных
        String checkMsg = ArgsObj.check(data, pList, cList);
        if (checkMsg != null) { System.out.println(checkMsg); System.exit(0); }

        // формируем чек
        Form form = new FormBuilder(Calc.result(data, pList, cList));
        form.print();
        form.save();

        new Http(form.getLines());
    }

}
