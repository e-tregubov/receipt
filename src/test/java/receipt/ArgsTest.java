package receipt;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import receipt.args.Args;
import receipt.args.Data;
import static org.junit.jupiter.api.Assertions.*;

class ArgsTest extends Methods {


    // превышение лимита, внедрение невалидного или неопознанного аргумента в валидные
    @NullSource
    @EmptySource
    @RepeatedTest(TEST_QTY)
    void random_invalid_args_test() throws Exception {

        // превышение лимита
        assertThrows(Exception.class, () ->
                Args.parser(validProductArgs(randomInt(MAX_POSITIONS + 1, PRODUCT_LIST_LENGTH))));

        // невалидный по количеству товара
        assertThrows(Exception.class, () ->
                Args.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), invalidArgQty())));

        // невалидный по идентификатору
        assertThrows(Exception.class, () ->
                Args.check(Args.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), invalidArgId())).products, null, PRODUCT_LIST, CARD_LIST));

        // невалидный по имени файла(файлов)
        assertThrows(Exception.class, () ->
                Args.parser(someAdd(validProductArgs(randomInt(1, MAX_POSITIONS)), invalidProductListArg(), inValidCardListArg())));

        // невалидный по номеру скидкарты
        Data data = Args.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS)), inValidCardArg()));
        assertThrows(Exception.class, () -> Args.check(data.products, data.cardNumber, PRODUCT_LIST, CARD_LIST));

        // неопознанный
        assertThrows(Exception.class, () ->
                Args.parser(addArg(validProductArgs(randomInt(1, MAX_POSITIONS - 1)), wrongArg())));
    }


    // правильные сочетания всех возможных аргументов
    @RepeatedTest(TEST_QTY)
    void random_valid_args_test() throws Exception {

        String[] args = possibleAdd(validProductArgs(randomInt(1, MAX_POSITIONS)),
                validProductListArg(), validCardListArg(), validCardArg());

        MyAssertions.assertDoesNotThrow(() -> Args.parser(args));

        Data data = Args.parser(args);
        MyAssertions.assertDoesNotThrow(() -> Args.check(data.products, data.cardNumber, PRODUCT_LIST, CARD_LIST));

    }
    @FunctionalInterface interface FailingRunnable { void run() throws Exception; }
    static class MyAssertions {
        public static void assertDoesNotThrow(FailingRunnable action) {
            try { action.run(); }
            catch (Exception ex) { throw new Error("Wrong exception!", ex); }
        }
    }

}
