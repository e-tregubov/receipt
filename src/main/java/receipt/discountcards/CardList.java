package receipt.discountcards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CardList {

    final String FILE_NAME = "dc.csv"; // имя файла для чтения/сохранения

    final Map<String, Integer> list = new LinkedHashMap<>(); // хэшмап скидочных карт №:%

    public boolean contains(String cardNumber) {
        return list.containsKey(cardNumber);
    }

    public int getValue(String cardNumber) { return list.getOrDefault(cardNumber, 0); }

    public void save(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                StringJoiner line = new StringJoiner(",");
                line.add(entry.getKey());
                line.add("" + entry.getValue());
                writer.write(line + "\n");
            }
            writer.flush();
            System.out.printf("Discount card list saved in \"%s\" file\n", fileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

}
