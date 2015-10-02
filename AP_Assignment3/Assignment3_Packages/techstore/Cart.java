package techstore;

import java.util.ArrayList;

/**
 * Object representing Shopping Cart. 
 * Contains original item, price paid, and whether it was eItem or physical copy
 * @author lcavedon
 *
 */
public class Cart {

	private ArrayList<CartItem> items;   // arraylist of actual items
	
	/**
	 * Constructor
	 */
	public Cart() {
		items = new ArrayList<CartItem>();
	}

	/**
	 * Add an item to the cart
	 * @param item
	 */
	public void add(CartItem item) {
		items.add(item);
	}
	
	/**
	 * Remove an item from the cart by giving its index
	 * @param index
	 */
	public void remove(int index) {
		items.remove(index);
	}
	
	/**
	 * Clear all items from the cart
	 */
	public void clear() {
		items = new ArrayList<CartItem>();
	}
	
	/**
	 * Total price of all items stored in the cart
	 * @return
	 */
	public double totalPrice() {
		double total = 0;
		for (CartItem item : items) {
			total += item.getPricePaid(); 
		}
		return total;
	}
	
	/**
	 * Return the ArrayList of items
	 * @return
	 */
	public ArrayList<CartItem> getItems() {
		return items;
	}
	
	/**
	 * Return a String representation of list of items in the Cart 
	 * @return
	 */
	public String printCart() {
		String string = "";
		for (CartItem item : items) {
			string = string + item.printItem();
		}
		return string;
	}
}
