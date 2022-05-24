package receipt;

import receipt.args.*;
import receipt.cards.CardList;
import receipt.products.Product;
import receipt.products.ProductList;
import receipt.receipt.*;

public class CheckRunner {

    public static void main(String[] args) throws Exception {

        // разбираем аргументы
        Data data = Args.parser(args);

        // получаем объекты данных
        DataMap<Integer, Product> pList = new ProductList(data.productsFileName);
        DataMap<String, Integer> cList = new CardList(data.cardsFileName);

        // проверка указаний аргументов в объектах данных
        Args.check(data.products, data.cardNumber, pList, cList);

        // формируем чек
        Form form = new FormBuilder(Calc.result(data, pList, cList));

        form.print();
        form.save(String.format("receipt-%04d.txt", Result.receiptNumber ));

        new Http(form.get());

    }

}
