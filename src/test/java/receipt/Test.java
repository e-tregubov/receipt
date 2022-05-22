package receipt;

import com.thedeanda.lorem.LoremIpsum;
import receipt.args.ArgsObj;
import receipt.cards.CardList;
import receipt.products.ProductList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {

    final static int TEST_QTY = 256, // количество проходов тестов
                     MAX_POSITIONS = ArgsObj.MAX_POSITIONS,
                     MAX_QTY = ArgsObj.MAX_QTY,
                     PRODUCT_LIST_LENGTH = ProductList.LIST_GEN_LENGTH,
                     FIRST_CARD_NUMBER = CardList.firstGenCardNumber;

    final static String P_ARG = ArgsObj.PRODUCTS_ARG + "-",
                        C_ARG = ArgsObj.CARDS_ARG + "-",
                        CARD = ArgsObj.CARD_ARG + "-",
                        EXISTING_PRODUCT_LIST_FILENAME = "productList.csv",
                        EXISTING_CARD_LIST_FILENAME = "cardList.csv";

    final static ProductList PRODUCT_LIST = new ProductList(null);
    final static CardList CARD_LIST = new CardList(null);
    final static LoremIpsum lorem = LoremIpsum.getInstance();



    // возвращает переданный массив с возможно добавленными аргументами
    String[] possibleAdd(String[] args, String ... inserts ) {
        for (String arg : inserts)
            if (chance()) args = addArg(args, arg);
        return args;
    }

    // возвращает переданный массив с добавленными аргументами (хотя бы один будет добавлен)
    String[] someAdd(String[] args, String ... inserts ) {
        int length = args.length;
        while (args.length == length)
            for (String arg : inserts) {
                if (chance())
                    args = addArg(args, arg);
            }
        return args;
    }

    // возвращает аргумент указывающий на существующий файл позиций
    String validProductListArg() { return P_ARG + EXISTING_PRODUCT_LIST_FILENAME; }

    // возвращает аргумент указывающий на случайный несуществующий файл позиций
    String invalidProductListArg() { return C_ARG + lorem.getWords(1); }

    // возвращает аргумент указывающий на существующий файл скидкарт
    String validCardListArg() { return C_ARG + EXISTING_CARD_LIST_FILENAME; }

    // возвращает аргумент указывающий на случайный несуществующий файл скидкарт
    String inValidCardListArg() { return C_ARG + lorem.getWords(1); }

    // возвращает аргумент содеражщий случайный валидный номер скидкарты
    String validCardArg() { return CARD + (FIRST_CARD_NUMBER + randomInt(0, 100)); }

    // возвращает аргумент содеражщий случайный невалидный номер скидкарты
    String inValidCardArg() { return CARD + (FIRST_CARD_NUMBER + randomInt(FIRST_CARD_NUMBER + 101, FIRST_CARD_NUMBER + 100000)); }

    // добавляет переданный аргумент в массив аргументов на случайную позицию
    String[] addArg(String[] args, String arg) {
        List<String> list = new ArrayList<>(Arrays.asList(args));
        list.add(randomInt(0, args.length - 1), arg);
        return list.toArray(args);
    }

    // возвращает массив случайных валидных аргументов позиций заданной длины
    String[] validProductArgs(int argsLength) {
        List<Integer> ids = new ArrayList<>(argsLength);
        while (ids.size() < argsLength) {
            int id = validId();
            if (!ids.contains(id)) ids.add(id);
        }
        List<String> args = new ArrayList<>();
        for (int id : ids) args.add(formatArg(id, validQty()));
        return args.toArray(new String[argsLength]);
    }

    // возвращает случайный неправильный аргумент
    String wrongArg() {
        String[] args = {"hi reviewer", "1-0", "0-1", "5-", "-2", "25-2x", "2e-12", "UA-11", "3-$", "--", "!^*@", "bye!"};
        int rnd = new Random().nextInt(args.length);
        return (chance()) ? args[rnd] : lorem.getWords(1, 3);
    }

    // возвращает аргумент позиции со случайным невалидным идентификатором
    String invalidArgId() { return formatArg(invalidId(), validQty()); }

    // возвращает аргумент позиции со случайным невалидным количеством
    String invalidArgQty() { return formatArg(validId(), invalidQty()); }

    // возвращает строку сформированного аргумента позиции (id-qty)
    String formatArg(int id, int qty) { return "" + id + "-" + qty; }

    // возвращает случайный валидный идентификатор позиции
    int validId() { return randomInt(1, PRODUCT_LIST_LENGTH); }

    // возвращает случайный невалидный идентификатор позиции
    int invalidId() { return randomInt(PRODUCT_LIST_LENGTH + 1, PRODUCT_LIST_LENGTH + 100); }

    // возвращает случайное валидное количество позиции
    int validQty() { return randomInt(1, MAX_QTY); }

    // возвращает случайное невалидное количество позиции
    int invalidQty() { return randomInt(MAX_QTY + 1, MAX_QTY + 100); }

    // возвращает true или false с равной долей вероятности
    boolean chance() { return Math.random() < 0.5; }

    // возвращает случайное число в заданном диапазоне
    int randomInt(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }

}
