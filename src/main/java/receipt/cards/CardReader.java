package receipt.cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CardReader extends CardList {

    public CardReader(String fileName) {

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String cardLine;
        while ((cardLine = br.readLine()) != null) {
            String[] elements = cardLine.split(",");
            list.put(elements[0], Integer.parseInt(elements[1]));
        }
    } catch (IOException ex) {
        System.out.printf("ERROR: No discount cards DB file \"%s\"", fileName);
        System.exit(0);
    }

    System.out.printf("Discount cards list was loaded from \"%s\"\n", fileName);
    }

}
