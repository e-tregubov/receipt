package receipt.discountcards;

public class CardGenerator extends CardList {

    public CardGenerator() {
        for (int firstCardNumber = 1000, discountValue = 0; discountValue < 101;) {
            list.put(("" + firstCardNumber++), discountValue++);
        }
        System.out.println("Discount card list has been successfully generated");
        super.save(FILE_NAME);
    }

}
