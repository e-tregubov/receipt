package receipt.cards;

import receipt.DataMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CardList implements DataMap<String, Integer> {

    public static final int CARD_LIST_LENGTH = 100; // номер первой генерируемой скидкарты, всего их 100 (0-100%)

    public Map<String, Integer> cardList;

    public boolean contains(String cardNumber) { return !cardList.containsKey(cardNumber); }

    public Integer getValue(String cardNumber) { return cardList.getOrDefault(cardNumber, 0); }


    public CardList(String fileName) {
        cardList = (fileName == null) ? generator(CARD_LIST_LENGTH) : reader(fileName);
    }


    // возвращает хэшмап скидкарт, заполняя его из файла
    public Map<String, Integer> reader(String fileName) {

        final Map<String, Integer> cardList = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String cardLine;
            while ((cardLine = br.readLine()) != null) {
                String[] elements = cardLine.split(",");
                cardList.put(elements[0], Integer.parseInt(elements[1]));
            }
        }
        catch (IOException ex) {
            System.out.printf("ERROR: No discount cards DB file \"%s\"", fileName);
            System.exit(0);
        }
        System.out.printf("Discount cards list was loaded from \"%s\"\n", fileName);
        return cardList;

    }


    // возвращает сгенерированный хэшмап скидкарт
    public Map<String, Integer> generator(int listLength) {

        Map<String, Integer> cardList = new LinkedHashMap<>();

        for (int number = 0, discountValue = 0; number < listLength;) {
            cardList.put(("" + ++number), ++discountValue);
        }
        System.out.println("Discount card list has been successfully generated");
        return cardList;
    }


    // метод сохранения хэшмапа скидкарт в файл типа .csv
    public void save(String fileName) {

        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Map.Entry<String, Integer> entry : cardList.entrySet()) {
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
