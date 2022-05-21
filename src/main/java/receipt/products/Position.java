package receipt.products;

public class Position extends Product {
    public int qty;
    public long promoTotal;

    public Position(String description, int price, int qty, int promoValue, int promoQty) {
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.promoValue = promoValue;
        this.promoQty = promoQty;
    }

}
