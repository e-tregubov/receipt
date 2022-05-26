package receipt.receipt;

import receipt.products.Position;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class FormBuilder implements Form {

    ArrayList<String> formLines;
    static final int WIDTH = 45,
                     QTY_WIDTH = 3,
                     PRICE_WIDTH = WIDTH/6,
                     TOTAL_WIDTH = PRICE_WIDTH+3,
                     DESC_WIDTH = WIDTH-QTY_WIDTH-PRICE_WIDTH-TOTAL_WIDTH;
    static final String EMPTY_LINE = " ".repeat(WIDTH),
                        DASH_LINE = "-".repeat(WIDTH);


    public FormBuilder(Result result) {
        formLines = linesCalc(result);
    }


    // возвращает все сформированные строки чека из объекта
    public ArrayList<String> get() { return formLines; }


    // возвращает все сформированные строки чека
    public ArrayList<String> linesCalc(Result result) {

        ArrayList<String> lines = new ArrayList<>();

        // добавление строк шапки в форму с выравниванием по центру
        for (String capLine : capLines()) {
            String indent = " ".repeat(WIDTH / 2 - capLine.length() / 2);
            String line = (indent + capLine + indent);
            if (line.length() == WIDTH) lines.add(line);
            else lines.add(line + " ");
        }

        // добавление строк позиций в форму
        for (Position position : result.positionList) {
            lines.add(makePositionLine(position.description, position.price, position.qty));
            if (position.promoTotal != 0) lines.add(makePromoLine(position.promoValue, position.promoTotal));
        }

        // добавление итогов
        Collections.addAll(lines, resultLines(result.amount, result.total, result.discountCardValue, result.discountTotal));

        return lines;
    }

    public void print() { for (String line : formLines) System.out.println(line); }

    public void save(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : formLines) writer.write(line + "\n");
            writer.flush();
            System.out.printf("Receipt form saved in \"%s\" file\n", fileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    // возвращает строки шапки
    private static String[] capLines() {
        return new String[]{
                EMPTY_LINE,
                "CASH RECEIPT",
                "SuperMarket-JAVA",
                "12, MILKY WAY Galaxy/Earth",
                "Tel: 123-456-7890",
                EMPTY_LINE,
                String.format("CASHIER: #" + "%04d" + " ".repeat(WIDTH - 30) + "DATE: %s", Result.receiptNumber, LocalDate.now()),
                String.format("%" + WIDTH + "s", String.format("TIME: %s", LocalTime.now().toString().substring(0, 8))),
                DASH_LINE,
                formatStr("QTY", QTY_WIDTH + 1, false) +
                        formatStr("DESCRIPTION", DESC_WIDTH + 1, false) +
                        formatStr("PRICE", PRICE_WIDTH - 1, true) +
                        formatStr("TOTAL", TOTAL_WIDTH - 1, true),
                EMPTY_LINE,
        };
    }

    // возвращает строки итогов
    private static String[] resultLines(long amount, long total, int discountCardValue, long discountTotal) {
        return new String[]{
                DASH_LINE,
                "Amount" + " ".repeat(WIDTH - format(amount).length() - 6) + format(amount),
                "Card discount: " + discountCardValue + "%"
                        + " ".repeat(WIDTH - format(discountTotal).length() - 16 - ("" + discountCardValue).length())
                        + format(discountTotal),
                "TOTAL" + " ".repeat(WIDTH - format(total).length() - 5) + format(total),
                EMPTY_LINE,
        };
    }

    // возвращает сформированную строку позиции (QTY, DESCRIPTION, PRICE, TOTAL)
    private static String makePositionLine(String description, int price, int qty) {
        return String.format("%-" + (QTY_WIDTH + 1) + "s", qty) +
                formatStr(description, DESC_WIDTH, false) + " " +
                formatStr(format(price), PRICE_WIDTH - 1, true) + " " +
                formatStr(format((long) price * qty), TOTAL_WIDTH - 2, true);
    }

    // возвращает сформированную строку "discounted" с новой скидочной ценой promoTotal
    private static String makePromoLine(int promoValue, long promoTotal) {
        String part1 = " ".repeat(QTY_WIDTH + 2) + "*" + promoValue + "% discounted*";
        String part2 = format(promoTotal);
        return part1 + " ".repeat(WIDTH - (part1 + part2).length()) + part2;
    }

    // возвращает строку заданной ширины (обрезает или добавляет пробелы до или после)
    private static String formatStr(String str, int width, boolean before) {
        return (str.length() > width) ? str.substring(0, width - 1) + "*" :
                before ? String.format("%" + width + "s", str) : String.format("%-" + width + "s", str);
    }

    // возвращает строку-цену в доллар-центах
    private static String format(long cents) {
        return "$" + new DecimalFormat("#0.00").format((double) cents / 100);
    }

}
