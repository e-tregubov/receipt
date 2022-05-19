package receipt.args;

public class ArgsObj {
    public Data data; // объект разобранных аргументов чека

    public ArgsObj(String[] args) throws Exception {
        data = parser(args);
    }

    public static Data parser(String[] args) throws Exception {

        if (args.length == 0) throw new Exception("No any arguments!");

        Data data = new Data();

        if (args.length == 1 && args[0].equals(Data.HELP)) { help(); System.exit(0); }

        for (String arg : args) {

            String[] part = arg.split("-");

            if (part.length != 2) throw new Exception("Wrong argument structure!");

            if (part[0].equals(Data.PRODUCTS_ARG))
                if (data.productsFileName == null) {
                    data.productsFileName = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", Data.PRODUCTS_ARG));

            if (part[0].equals(Data.CARDS_ARG))
                if (data.cardsFileName == null) {
                    data.cardsFileName = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", Data.CARDS_ARG));

            if (part[0].equals(Data.CARD_ARG))
                if (data.cardNumber == null) {
                    data.cardNumber = part[1];
                    continue;
                } else throw new Exception(String.format("Multiple \"%s\" argument!", Data.CARD_ARG));

            if (noNum(part[0]))
                throw new Exception(String.format("Position argument ID \"%s\" is not a number!", part[0]));
            if (noNum(part[1]))
                throw new Exception(String.format("Position argument quantity \"%s\" is not a number!", part[1]));

            int id = Integer.parseInt(part[0]);
            if (data.products.containsKey(id))
                throw new Exception(String.format("Duplicate position argument ID: \"%d\"", id));

            int qty = Integer.parseInt(part[1]);
            if (qty < 1 || qty > Data.MAX_QTY)
                throw new Exception("Position argument value out of range[1-" + Data.MAX_QTY + "]");

            data.products.put(id, qty);

        }

        if (data.products.isEmpty()) throw new Exception("No any position argument!");
        if (data.products.size() > Data.MAX_POSITIONS) throw new Exception("Too much position arguments!");

        return data;
    }

    private static void help() {
        final String MainClassName = "CheckRunner";
        System.out.printf("""
                        %nUsage: java %s %s-filename %s-filename id1-quantity id2-quantity.. idN-quantity %s-number
                        Example1: java %s %s-pl.csv %s-dc.csv 1-10 24-15 %s-1020
                        Example2: java %s 3-5 48-12 11-7
                        """,
                MainClassName, Data.PRODUCTS_ARG, Data.CARDS_ARG, Data.CARD_ARG,
                MainClassName, Data.PRODUCTS_ARG, Data.CARDS_ARG, Data.CARD_ARG,
                MainClassName);
    }

    private static boolean noNum(String str) { return !str.matches("-?\\d+"); }

}
