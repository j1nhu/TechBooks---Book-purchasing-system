package techstore;

/**
 * Represents an item to be added to a shopping cart.
 * As well as normal Item information, stores price actually paid and whether e- or physical copy.
 * @author lcavedon
 *
 */
public class CartItem {

	private Book item;   // original item object
	private double pricePaid;   // actual price paid (may be discounted)
	private boolean eItem;   // did user buy e-version or physical copy
	
	/**
	 * Constructor
	 * @param item
	 * @param pricePaid
	 * @param eItem
	 */
	public CartItem(Book item, double pricePaid, boolean eItem) {
		this.item = item;
		this.pricePaid = pricePaid;
		this.eItem = eItem;
	}
	
	/**
	 * Accessor for Item
	 * @return
	 */
	public Book getItem() {
		return item;
	}
	
	/**
	 * Accessoor from original item
	 * @return
	 */
	public String getTitle() {
		return item.getBookTitle();
	}
	
	public String getAuthor() {
	  return item.getAuthor();
	}

	/**
	 * Accessor of actual price paid
	 * @return
	 */
	public double getPricePaid() {
		return pricePaid;
	}
	
	/**
	 * Accessor of whether e- version or physical copy purchased
	 * @return
	 */
	public boolean eItem() {
		return eItem;
	}
	
	
	/**
	 * Convert this object into a String form, containing all info, for easy printing
	 */
	public String printItem() {
	  String eBook = "";
	  double price = 0.0;
    if (eItem) {
      eBook = "ebook";
      price = item.geteprice();
    } else {
      eBook = "physical book";
      price = item.getphyprice();
    }
		return "|" + item.getBookTitle() + "|" + item.getAuthor() + "|" + eBook  + "|" + price;
	}

	
}
