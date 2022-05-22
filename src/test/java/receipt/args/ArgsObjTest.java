package receipt.args;

import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import receipt.cards.CardList;
import receipt.products.ProductList;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ArgsObjTest {

    private final static int TEST_QTY = 256, // количество проходов тестов
                             MAX_POSITIONS = ArgsObj.MAX_POSITIONS,
                             MAX_QTY = ArgsObj.MAX_QTY,
                             PRODUCT_LIST_LENGTH = ProductList.LIST_GEN_LENGTH,
                             FIRST_CARD_NUMBER = CardList.firstGenCardNumber;
    private final static String P_ARG = ArgsObj.PRODUCTS_ARG + "-",
                                C_ARG = ArgsObj.CARDS_ARG + "-",
                                CARD = ArgsObj.CARD_ARG + "-",
                                EXISTING_PRODUCT_LIST_FILENAME = "productList.csv",
                                EXISTING_CARD_LIST_FILENAME = "cardList.csv";
    private final static ProductList PRODUCT_LIST = new ProductList(null);
    private final static CardList CARD_LIST = new CardList(null);
    private final static LoremIpsum lorem = LoremIpsum.getInstance();



    // правильные сочетания всех аргументов с превышением лимита, внедрением невалидного или неопознанного
    @NullSource
    @EmptySource
    @RepeatedTest(TEST_QTY)
    void all_invalid_args_combinations() throws Exception {

        // превышение лимита
        assertThrows(Exception.class, () -> ArgsObj.parser(validProductArgs(randomInt(MAX_POSITIONS + 1, PRODUCT_LIST_LENGTH))));

        // невалидный по количеству товара
        assertThrows(Exception.class, () ->
                ArgsObj.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), invalidArgQty())));

        // невалидный по идентификатору
        assertThrows(Exception.class, () ->
                ArgsObj.check(ArgsObj.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), invalidArgId())).products, null, PRODUCT_LIST, CARD_LIST));

        // невалидный по имени файла(файлов)
        assertThrows(Exception.class, () ->
                ArgsObj.parser(someAdd(validProductArgs(randomInt(1, MAX_POSITIONS)), invalidProductListArg(), inValidCardListArg())));

        // невалидный по номеру скидкарты
        Data data = ArgsObj.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS)), inValidCardArg()));
        assertThrows(Exception.class, () -> ArgsObj.check(data.products, data.cardNumber, PRODUCT_LIST, CARD_LIST));

        // неопознанный
        assertThrows(Exception.class, () ->
                ArgsObj.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), wrongArg())));
    }


    // правильные сочетания всех возможных аргументов
    @RepeatedTest(TEST_QTY)
    void all_valid_args_combinations() throws Exception {

        String[] args = possibleAdd(validProductArgs(randomInt(1, MAX_POSITIONS)),
                validProductListArg(), validCardListArg(), validCardArg());

        MyAssertions.assertDoesNotThrow(() -> ArgsObj.parser(args));

        Data data = ArgsObj.parser(args);
        MyAssertions.assertDoesNotThrow(() -> ArgsObj.check(data.products, data.cardNumber, PRODUCT_LIST, CARD_LIST));

    }
    @FunctionalInterface interface FailingRunnable { void run() throws Exception; }
    static class MyAssertions {
        public static void assertDoesNotThrow(FailingRunnable action) {
            try { action.run(); }
            catch (Exception ex) { throw new Error("Wrong exception!", ex); }
        }
    }


    // возвращает переданный массив с добавленными аргументами (возможно без добавления)
    private String[] possibleAdd(String[] args, String ... inserts ) {
        for (String arg : inserts)
            if (chance()) args = addArg(args, arg);
        return args;
    }

    // возвращает переданный массив с добавленными аргументами (хотя бы один будет добавлен)
    private String[] someAdd(String[] args, String ... inserts ) {
        int length = args.length;
        while (args.length == length)
            for (String arg : inserts) {
                if (chance())
                    args = addArg(args, arg);
            }
        return args;
    }

    // возвращает аргумент указывающий на существующий файл позиций
    private String validProductListArg() { return P_ARG + EXISTING_PRODUCT_LIST_FILENAME; }

    // возвращает аргумент указывающий на случайный несуществующий файл позиций
    private String invalidProductListArg() { return C_ARG + lorem.getWords(1); }

    // возвращает аргумент указывающий на существующий файл скидкарт
    private String validCardListArg() { return C_ARG + EXISTING_CARD_LIST_FILENAME; }

    // возвращает аргумент указывающий на случайный несуществующий файл скидкарт
    private String inValidCardListArg() { return C_ARG + lorem.getWords(1); }

    // возвращает аргумент содеражщий случайный валидный номер скидкарты
    private String validCardArg() { return CARD + (FIRST_CARD_NUMBER + randomInt(0, 100)); }

    // возвращает аргумент содеражщий случайный невалидный номер скидкарты
    private String inValidCardArg() { return CARD + (FIRST_CARD_NUMBER + randomInt(FIRST_CARD_NUMBER + 101, FIRST_CARD_NUMBER + 100000)); }

    // добавляет переданный аргумент в массив аргументов на случайную позицию
    private String[] addArg(String[] args, String arg) {
        List<String> list = new ArrayList<>(Arrays.asList(args));
        list.add(randomInt(0, args.length - 1), arg);
        return list.toArray(args);
    }

    // возвращает массив случайных валидных аргументов позиций заданной длины
    private String[] validProductArgs(int argsLength) {
        List<Integer> ids = new ArrayList<>(argsLength);
        while (ids.size() < argsLength) {
            int id = validId();
            if (!ids.contains(id)) ids.add(id);
        }
        List<String> args = new ArrayList<>();
        for (int id : ids) args.add(formatArg(id, validQty()));
        return args.toArray(new String[argsLength]);
    }

    private String wrongArg() {
        String[] args = {"hi reviewer", "1-0", "0-1", "5-", "-2", "25-2x", "2e-12", "UA-11", "3-$", "--", "!^*@", "bye!"};
        int rnd = new Random().nextInt(args.length);
        return (chance()) ? args[rnd] : lorem.getWords(1);
    }

    // возвращает аргумент позиции со случайным невалидным идентификатором
    private String invalidArgId() { return formatArg(invalidId(), validQty()); }

    // возвращает аргумент позиции со случайным невалидным количеством
    private String invalidArgQty() { return formatArg(validId(), invalidQty()); }

    // возвращает строку сформированного аргумента позиции (id-qty)
    private String formatArg(int id, int qty) { return "" + id + "-" + qty; }

    // возвращает случайный валидный идентификатор позиции
    private int validId() { return randomInt(1, PRODUCT_LIST_LENGTH); }

    // возвращает случайный невалидный идентификатор позиции
    private int invalidId() { return randomInt(PRODUCT_LIST_LENGTH + 1, PRODUCT_LIST_LENGTH + 100); }

    // возвращает случайное валидное количество позиции
    private int validQty() { return randomInt(1, MAX_QTY); }

    // возвращает случайное невалидное количество позиции
    private int invalidQty() { return randomInt(MAX_QTY + 1, MAX_QTY + 100); }

    // возвращает true или false с равной долей вероятности
    private boolean chance() { return Math.random() < 0.5; }

    // возвращает случайное число в заданном диапазоне
    private int randomInt(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }

}
