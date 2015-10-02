package techstore;

/**
 * Basic representation of a User/Shopper. Note this is NOT a full solution to
 *   Assignment 2, it is merely enough for Assignment 3 purposes
 * @author lcavedon
 *
 */
public class User {

	private String id;		// user's unique id
	private String passwd;  // user's password
	private String name;	// user's name
	private String email;
	private String type;
	private Cart cart;
	/**
	 * Constructor
	 * @param id
	 * @param passwd
	 * @param name
	 */
	public User(String id, String passwd, String name, String email, String type, Cart cart) {
		this.id = id;
		this.passwd = passwd;
		this.name = name;
		this.email = email;
		this.type = type;
		this.cart = cart;
	}
	
	/**
	 * Accessor for id
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Accessor for password
	 * @return
	 */
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * Accessor for name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public String getEmail() {
	  return email;
	}
	
	public String getType () {
    return type;
  }
	
	public Cart getCart() {
    return cart;
  }
	
	public String printUser() {
	  return id + "|" + passwd + "|" + name + "|" + email + "|" + type + cart.printCart();
	}
	
}
