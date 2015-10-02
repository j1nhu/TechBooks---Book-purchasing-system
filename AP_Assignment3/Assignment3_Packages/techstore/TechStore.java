package techstore;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that implements main Store functionality, including user interaction
 * @author lcavedon
 *
 */
public class TechStore {

	private ArrayList<Book> books = new ArrayList<Book>();  // inventory of all books in store

	
	private HashMap<String,User> users = new HashMap<String,User>(); // list of Users, indexed by id
	
	private Scanner input = new Scanner(System.in);
	
	/**
	 * main program --- create an instance and call run(), which is main method
	 * @param args
	 */
//	public static void main(String[] args) {
//		TechStore store = new TechStore();
//		store.run();
//	}
	
	/**
	 * Constructor. Creates instances of Shoppers, Books, Software
	 */
	public TechStore() {	
		// populate inventory of all books held by store --- NOTE prices are INCORRECT
		books.add(new Book("Absolute Java", "Savitch", 5, true, 5.00, 75.00));
		books.add(new Book("JAVA: How to Program", "Deitel and Deitel", 0, true, 5.00, 75.00));
		books.add(new Book("Computing Concepts with JAVA 3 Essentials", "Horstman", 5, false, 5.00, 75.00));
		books.add(new Book("Java Software Solutions", "Lewis and Loftus", 5, false, 5.00, 75.00));
		books.add(new Book("Java Program Design", "Cohoon and Davidson", 1, true, 5.00, 75.00));
		
	//	users.put("lc", new Shopper("lc", "lcpw", "Lawrence", "lawrence@techstore.com.au"));
	//	users.put("xl", new Shopper("rmac", "rmacqpw", "Robert", "robq@techstore.com.au"));
	}
	
	/**
	 * Main method --- prints main menu and handles user inputs
	 */
	public void run() {
		int option;   // option read from menu --- needs to be defined outside loop 
		// main menu loop: print menu, then do something depending on selection
		do {
			printMainMenu();
			option = input.nextInt();  // read option as an integer
			input.nextLine();      // NOTE: clear end-of-line from previous read
			System.out.println();
			
			// perform correct action, depending on selection
			switch (option) {
			case 1: purchaseItem();
				break;
			case 2: showCart();
				break;
			case 3: removeItem();   // not implemented in this version
				break;
			case 4: checkOut();
				break;
			case 5: listAllItems();
				break;
			case 9: addUser();
				break;
			case 0: System.out.println("Goodbye!");  // will quit---do nothing
				break;
			default:
				// if no legal option selected, just go round and choose again
				System.out.println("Invalid option! Make another selection.");
			}
			
		} while (option != 0);   // exit when option == 0
	}
	
	/*
	 * Print the main menu
	 */
	private static void printMainMenu() {
		System.out.println();
		System.out.println("Select an option:");
		System.out.println("1. Purchase an item");
		System.out.println("2. View shopping cart");
		System.out.println("3. Remove an item from a shopping cart");
		System.out.println("4. Checkout");
		System.out.println("5. View all items");
		System.out.println("9. Add a user");
		System.out.println("0. Quit");
		System.out.print("Your option: ");
	}


	/*
	 * List all the books in the store, plus their status (number of copies)
	 */
	private void listAllItems() {
		System.out.println("List of all available books:");
		// loop through list of all books and print them
		for (Book b : books) {
			System.out.println(b.toString());  
		}
		System.out.println("List of all available software:");
		// loop through list of all software items
	
	}
	
	/*
	 * Display the current contents of the shopping cart, after querying for which user
	 */
	private void showCart() {
		User user = promptForUser();
		if (user != null)
			showCart(user);
	}
	
	/*
	 * Display cart for a particular shopper
	 */
	private void showCart(User user) {
		System.out.println("Items in " + user.getId() + "'s cart:");
		System.out.println(user.getCart());
	}

	/*
	 * Remove a specific book from the shopping cart --- not yet implemented
	 */
	private void removeItem() {
	  User user = promptForUser();
		Cart cart = user.getCart();
		ArrayList<CartItem> cartItems = cart.getItems();
		System.out.println("Items in " + user.getId() + "'s cart:");
		for (int i=0; i<cartItems.size(); i++) {
			System.out.println(i + ": " + cartItems.get(i).toString());
		}
		System.out.print("Which item do you want to remove--enter the number: ");
		int index = input.nextInt();
		input.nextLine();
		cart.remove(index);
		System.out.println("New cart:");
		System.out.println(cart);
		System.out.println();
	}
	
	
	/*
	 * Checkout -- display items being purchased, final bill, then clear the cart of some user.
	 * This prompts for the user first.
	 */
	private void checkOut() {
	  User user = promptForUser();
		if (user != null) {
			System.out.println("Thanks for shopping at Daintree!");
			showCart(user);
			System.out.println(user.getId() + "'s final bill is " + user.getCart().totalPrice());
			user.getCart().clear();
		}
	}
	
	
	/*
	 * Purchase an item after prompting for the user
	 */
	private Book purchaseItem() {
		User user = promptForUser();
		if (user == null)
			return null;
		else
			return purchaseItem(user);
	}
	/*
	 * Select an Item to be purchased by a given user.
	 */
	private Book purchaseItem(User user) {
		ArrayList<Book> itemsToList = new ArrayList<Book>();
		// first check if user wants book or software
		System.out.print("Book (b) or Software (s): ");
		String option = input.nextLine();
		if (option.startsWith("b"))
			itemsToList.addAll(books);
		else
			itemsToList.addAll(books);
		// check if wants electronic version or not
		System.out.print("Do you want the electronic version (y/n): ");
		option = input.nextLine();
		boolean eVersion = false;
		if (option.startsWith("y")) 
			eVersion = true;

		// filter out items not matching user's selection (i.e. B/M + eVersion)
		// ie., only display those items the user is interested in
		ArrayList<Book> filteredItems = new ArrayList<Book>();
		for (Book item : itemsToList) {
			if (!eVersion || item.isEbook()) {
				filteredItems.add(item);
			}
		}
		// filteredItems now only contains items of interest
		// print out the list of matching items
		if (filteredItems.size() == 0) {
			System.out.println("There are no items matching your request!");
			return null;
		}
		for (int i=0; i<filteredItems.size(); i++) {
			System.out.println(i + ": " + filteredItems.get(i).toString());
		}
		
		// query for user to select an item from the displayed list
		System.out.println("Which item do you want? Input the number:");
		int optionInt;
		boolean legal = false;  // flag to indicate if user has made a valid selection
		do {
			optionInt = input.nextInt();
			input.nextLine();
			// check if user's selection was a valid option
			if (optionInt >= 0 && optionInt < filteredItems.size())
				legal = true;
			else
				System.out.println("Illegal option; try again!");
		} while (!legal);
		Book selection = filteredItems.get(optionInt);  
		
		// first check if there are enough copies if buying physical version
		if (!eVersion && selection.getNumPhysical() < 1) {  
			System.out.println("There are no copies of that title avaialble");
			return null;
		}
		else {
			// we have a copy --- add it to user's cart 
			double price;
			if (eVersion) price = selection.geteprice();
			else price = selection.getphyprice();
			selection.decreaseNumPhysical();;    // reduces number of copies left in store
			// update cart --- i.e., add new item to user's cart
			user.getCart().add(new CartItem(selection, price, eVersion));
		}
		// confirm purchase to user 
		System.out.println("Added to " + user.getId() + "'s cart: " + selection.getBookTitle());
		return selection;
	}
	
	/*
	 * Add a new user to the store
	 */
	private void addUser() {
		String id = "";
		boolean uniqueId = false;
		// must check that the user id isn't already a key in hashmap -- i.e., must be unique
		do {
			System.out.print("What is the id (must be unique): ");
			id = input.nextLine();
			if (users.containsKey(id))
				System.out.println("That id already exists!");
			else
				uniqueId = true;
		} while (!uniqueId);
		System.out.print("Choose a password: ");
		String passwd = input.nextLine();
		System.out.print("What is the user's name: ");
		String name = input.nextLine();
		System.out.print("What is the user's email address: ");
    String email = input.nextLine();
		System.out.print("Is this user an Administrator: ");
		String isAdmin = input.nextLine();
		boolean admin = isAdmin.startsWith("y") || isAdmin.startsWith("Y");
		// create the right type of User
		User newUser = null;
		if (admin)
		//	newUser = new Admin(id, passwd, name, email);
	//	else
		//	newUser = new Shopper(id, passwd, name, email);
		// add the new user to the hashmap of users
		users.put(id, newUser);
		System.out.println("New user added!");
	}
	
	
	private User promptForUser() {
		System.out.print("Which user: ");
		String userId = input.nextLine();
		if (!users.containsKey(userId)) {
			System.out.println("User not found!");
			return null;
		}
		return users.get(userId); 
	}

}
