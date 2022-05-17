package receipt.arguments;

import receipt.discountcards.CardList;
import receipt.products.ProductList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArgsData {

    final int MAX_POSITIONS = 5; // максимальное кол-во позиций в чеке
    final int MAX_QTY = 999; // максимальное кол-во товара в позиции чека
    public static final String productsArg = "pl";
    public static final String cardsArg = "dc";
    public static final String cardArg = "cn";
    static final String help = "help";
    public ArgsObj argsObj;

    public static class ArgsObj {
        public String productsFile, cardsFile, cardNumber;
        public final Map<Integer, Integer> products = new LinkedHashMap<>();
    }

    public ArgsData(String[] args) throws Exception {
        argsObj = parser(args);
    }

    public ArgsObj parser(String[] args) throws Exception {

        ArgsObj argsObj = new ArgsObj();

        if (args.length == 0) throw new Exception("No any arguments!");
        if (args.length == 1 && args[0].equals(help)) help();

        // перебор аргументов
        for (String arg : args) {

                String[] part = arg.split("-");

                if (part.length != 2) throw new Exception("Wrong argument structure!");

                if (part[0].equals(productsArg))
                    if (argsObj.productsFile == null) { argsObj.productsFile = part[1]; continue; }
                    else throw new Exception(String.format("Multiple \"%s\" argument!", productsArg));

                if (part[0].equals(cardsArg))
                    if (argsObj.cardsFile == null) { argsObj.cardsFile = part[1]; continue; }
                    else throw new Exception(String.format("Multiple \"%s\" argument!", cardsArg));

                if (part[0].equals(cardArg))
                    if (argsObj.cardNumber == null) { argsObj.cardNumber = part[1]; continue; }
                    else throw new Exception(String.format("Multiple \"%s\" argument!", cardArg));

                if (noNum(part[0]))
                    throw new Exception(String.format("Position argument ID \"%s\" is not a number!", part[0]));
                if (noNum(part[1]))
                    throw new Exception(String.format("Position argument quantity \"%s\" is not a number!", part[1]));

                int id = Integer.parseInt(part[0]);
                if (argsObj.products.containsKey(id))
                    throw new Exception(String.format("Duplicate position argument ID: \"%d\"", id));

                int qty = Integer.parseInt(part[1]);
                if (qty < 1 || qty > MAX_QTY)
                    throw new Exception("Position argument value out of range[1-" + MAX_QTY + "]");

                argsObj.products.put(id, qty);
            }

        if (argsObj.products.isEmpty())
            throw new Exception("No any position argument!");
        if (argsObj.products.size() > MAX_POSITIONS)
            throw new Exception("Too much position arguments!");

        return argsObj;
    }

    public void check(ProductList productList, CardList cardList) throws Exception {

        if (argsObj.cardNumber != null && !cardList.contains((argsObj.cardNumber)))
            throw new Exception("Wrong discount card number!");
        for (Map.Entry<Integer, Integer> entry : argsObj.products.entrySet()) {
            if (!productList.idExists(entry.getKey()))
                throw new Exception("Invalid argument(s) product ID!");
        }
    }

    private boolean noNum(String str) { return !str.matches("-?\\d+"); }

    private void help() {
        final String MainClassName = "CheckRunner";
        System.out.printf("""
                %nUsage: java %s %s-filename %s-filename id1-quantity id2-quantity.. idN-quantity %s-number
                Example1: java %s %s-pl.csv %s-dc.csv 1-10 24-15 %s-1020
                Example2: java %s 3-5 48-12 11-7
                """,
                MainClassName, productsArg, cardsArg, cardArg,
                MainClassName, productsArg, cardsArg, cardArg,
                MainClassName
        );
        System.exit(0);
    }
}
