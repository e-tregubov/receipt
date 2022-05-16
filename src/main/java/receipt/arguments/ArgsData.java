package receipt.arguments;

import receipt.discountcards.CardList;
import receipt.products.ProductList;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArgsData {

    final int MAX_POSITIONS = 5; // максимальное кол-во позиций в чеке
    final int MAX_QTY = 999; // максимальное кол-во товара в позиции чека
    final String PRODUCTS = "pl", CARDS = "dc", CARD = "cn", HELP = "help";
    public ArgsObj argsObj;

    public static class ArgsObj {
        public String productsFile, cardsFile, cardNumber;
        public final Map<Integer, Integer> products = new LinkedHashMap<>();
    }

    public ArgsData(String[] args) {
        argsObj = parser(args);
    }

    public ArgsObj parser(String[] args) {

        ArgsObj argsObj = new ArgsObj();

        if (args.length == 0) error("No any arguments!");
        if (args.length == 1 && args[0].equals(HELP)) help();

        // перебор аргументов
        for (String arg : args) {
            try {
                String[] part = arg.split("-");

                if (part.length != 2) error("Wrong argument structure!");

                if (part[0].equals(PRODUCTS))
                    if (argsObj.productsFile == null) { argsObj.productsFile = part[1]; continue; }
                    else error(String.format("Multiple \"%s\" argument!", PRODUCTS));

                if (part[0].equals(CARDS))
                    if (argsObj.cardsFile == null) { argsObj.cardsFile = part[1]; continue; }
                    else error(String.format("Multiple \"%s\" argument!", CARDS));

                if (part[0].equals(CARD))
                    if (argsObj.cardNumber == null) { argsObj.cardNumber = part[1]; continue; }
                    else error(String.format("Multiple \"%s\" argument!", CARD));

                if (noNum(part[0])) error(String.format("Position argument ID \"%s\" is not a number!", part[0]));
                if (noNum(part[1])) error(String.format("Position argument quantity \"%s\" is not a number!", part[1]));

                int id = Integer.parseInt(part[0]);
                if (argsObj.products.containsKey(id)) error(String.format("Duplicate position argument ID: \"%d\"", id));

                int qty = Integer.parseInt(part[1]);
                if (qty < 1 || qty > MAX_QTY) error("Position argument value out of range[1-" + MAX_QTY + "]");

                argsObj.products.put(id, qty);
            }
            catch (Exception ex) { error(ex.getMessage()); }
        }

        if (argsObj.products.isEmpty()) error("No any position argument!");
        if (argsObj.products.size() > MAX_POSITIONS) error("Too much position arguments!");

        return argsObj;
    }

    public void check(ProductList productList, CardList cardList) {
        if (argsObj.cardNumber != null && !cardList.contains((argsObj.cardNumber))) error("Wrong discount card number!");
        for (Map.Entry<Integer, Integer> entry : argsObj.products.entrySet()) {
            if (!productList.idExists(entry.getKey())) error ("Invalid argument(s) product ID!");
        }
    }

    private void error(String msg) { System.out.println("ERROR: " + msg); System.exit(0); }

    private boolean noNum(String str) { return !str.matches("-?\\d+"); }

    private void help() {
        final String MainClassName = "CheckRunner";
        System.out.printf("""
                %nUsage: java %s %s-filename %s-filename id1-quantity id2-quantity.. idN-quantity %s-number
                Example1: java %s %s-pl.csv %s-dc.csv 1-10 24-15 %s-1020
                Example2: java %s 3-5 48-12 11-7
                """,
                MainClassName, PRODUCTS, CARDS, CARD,
                MainClassName, PRODUCTS, CARDS, CARD,
                MainClassName
        );
        System.exit(0);
    }
}
