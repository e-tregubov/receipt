package receipt.args;

import receipt.cards.CardList;
import receipt.products.ProductList;
import java.util.Map;

public class ArgsObj {

    public static final int MAX_POSITIONS = 5, MAX_QTY = 999;
    public static final String HELP = "help", PRODUCTS_ARG = "pl", CARDS_ARG = "cl", CARD_ARG = "cn";
    public Data data; // объект разобранных аргументов чека


    public ArgsObj(String[] args) throws Exception { data = parser(args); }


    public static Data parser(String[] args) throws Exception {

        if (args.length == 0) throw new Exception("No any arguments!");

        Data data = new Data();

        if (args.length == 1 && args[0].equals(HELP)) { help(); System.exit(0); }

        for (String arg : args) {

            String[] part = arg.split("-");

            if (part.length != 2) throw new Exception("Wrong argument structure!");

            if (part[0].equals(PRODUCTS_ARG))
                if (data.productsFileName == null) {
                    data.productsFileName = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", PRODUCTS_ARG));

            if (part[0].equals(CARDS_ARG))
                if (data.cardsFileName == null) {
                    data.cardsFileName = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", CARDS_ARG));

            if (part[0].equals(CARD_ARG))
                if (data.cardNumber == null) {
                    data.cardNumber = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", CARD_ARG));

            if (noNum(part[0]))
                throw new Exception(String.format("Position argument ID \"%s\" is not a number!", part[0]));
            if (noNum(part[1]))
                throw new Exception(String.format("Position argument quantity \"%s\" is not a number!", part[1]));

            int id = Integer.parseInt(part[0]);
            if (data.products.containsKey(id))
                throw new Exception(String.format("Duplicate position argument ID: \"%d\"", id));

            int qty = Integer.parseInt(part[1]);
            if (qty < 1 || qty > MAX_QTY)
                throw new Exception("Position argument value out of range[1-" + MAX_QTY + "]");

            data.products.put(id, qty);

        }

        if (data.products.isEmpty()) throw new Exception("No any position argument!");
        if (data.products.size() > MAX_POSITIONS) throw new Exception("Too much position arguments!");

        return data;
    }



    public static final String invalidCardNumberMsg = "Wrong discount card number!",
                               invalidIdMsg = "Invalid argument(s) product ID!";
    public static String check(Data data, ProductList productList, CardList cardList) {

        if (data.cardNumber != null && !cardList.contains((data.cardNumber)))
            return invalidCardNumberMsg;

        for (Map.Entry<Integer, Integer> entry : data.products.entrySet()) {
            if (!productList.contains(entry.getKey()))
                return invalidIdMsg;
        }
        return null;
    }



    private static void help() {
        final String MainClassName = "CheckRunner";
        System.out.printf("""
                        %nUsage: java %s %s-filename %s-filename id1-quantity id2-quantity.. idN-quantity %s-number
                        Example1: java %s %s-pl.csv %s-dc.csv 1-10 24-15 %s-1020
                        Example2: java %s 3-5 48-12 11-7
                        """,
                MainClassName, PRODUCTS_ARG, CARDS_ARG, CARD_ARG,
                MainClassName, PRODUCTS_ARG, CARDS_ARG, CARD_ARG,
                MainClassName);
    }

    private static boolean noNum(String str) { return !str.matches("-?\\d+"); }

}
