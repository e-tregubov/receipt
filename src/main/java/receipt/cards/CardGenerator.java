package receipt.cards;

public class CardGenerator extends CardList {

    public static final int FIRST_CARD_NUMBER = 1000;

    public CardGenerator() {
        for (int number = FIRST_CARD_NUMBER, discountValue = 0; discountValue < 101;) {
            list.put(("" + number++), discountValue++);
        }
        System.out.println("Discount card list has been successfully generated");
//        super.save(FILE_NAME);
    }

}
