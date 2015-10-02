package fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import techstore.Book;
import techstore.User;

public class WriteOutputFile {
  public boolean createNewUser(String userid, String userpw, String username, String usertype) {
    if (userExist(userid)) {
      return false;
    } else {
      try {
        FileWriter fwriter = new FileWriter("input.txt", true);
        BufferedWriter out = new BufferedWriter(fwriter);
        String type;
        if (usertype.equals("Admin")) {
          type = "admin";
        } else {
          type = "shopper|non-member";
        }
        String s = userid + "|" + userpw + "|" + username + "|user@email.com|" + type;
        out.newLine();
        out.append(s);
        out.close();
        return true;
      } catch (Exception ex) {
        System.out.println("Adding new user error: " + ex);
        return false;
      }
    }
  }

  public boolean addToCart(String userid, String booktitle, boolean ebook) {
    CopyInputFile cif = new CopyInputFile();
    cif.copyInput();
    if (ebook) {
      String s = getUser(userid).printUser() + "|" + getBook(booktitle).printBook() + "|ebook|" + getBook(booktitle).geteprice();
      if (addToCartWriteFile(s)) {
        cif.updateInput();
        return true;
      } else {
        return false;
      }
    } else {
      if (getBook(booktitle).available()) {
        cif.copyHead();
        String s = getUser(userid).printUser() + "|" + getBook(booktitle).printBook() + "|physical book|" + getBook(booktitle).getphyprice();
        Book newbook = getBook(booktitle);
        newbook.decreaseNumPhysical();
        String s2 = newbook.printBookDetails();
        System.out.println(s2);
        if (updateNumPhyWriteFile(s2, booktitle, userid) && addToCartWriteFile(s)) {
          cif.updateInput();
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  public boolean updateNumPhyWriteFile(String string, String booktitle, String userid) {
    try {
      DeleteUserFile duf = new DeleteUserFile();
      duf.deleteBookLineByTitle(booktitle);
      duf.deleteUserLineByID(userid);
      FileWriter fwriter = new FileWriter("output.txt", true);
      BufferedWriter out = new BufferedWriter(fwriter);
      out.newLine();
      out.append(string);
      out.newLine();
      out.close();
      CopyInputFile cif = new CopyInputFile();
      cif.copyBody(userid, booktitle);
      return true;
    } catch (Exception ex) {
      System.out.println("Adding to cart error: " + ex);
      return false;
    }
  }

  public boolean addToCartWriteFile(String string) {
    try {
      FileWriter fwriter = new FileWriter("output.txt", true);
      BufferedWriter out = new BufferedWriter(fwriter);
      out.newLine();
      out.append(string);
      out.close();
      return true;
    } catch (Exception ex) {
      System.out.println("Adding to cart error: " + ex);
      return false;
    }
  }

  public boolean deleteFromCart(String userid, int index) {
    User user = getUser(userid);
    if (index >= user.getCart().getItems().size()) {
      System.out.println("out of index");
      return false;
    } else {
      
      DeleteUserFile duf = new DeleteUserFile();
      duf.deleteUserOutputByID(userid);
      
           
      user.getCart().remove(index);
      System.out.println(user.getCart().getItems().size());
      String s = user.printUser();
      if (addToCartWriteFile(s)) {
        CopyInputFile cif = new CopyInputFile();
        cif.updateInput();
        return true;
      }
      return false;
    }
  }

  public boolean purchaseBook(String booktitle, boolean ebook, String userid) {
    if (ebook) {
      return true;
    } else {
      if (getBook(booktitle).available()) {
        Book newbook = getBook(booktitle);
        newbook.decreaseNumPhysical();
        String s2 = newbook.printBookDetails();
        System.out.println(s2);
        if (updateNumPhyWriteFile(s2, booktitle, userid)) {
          CopyInputFile cif = new CopyInputFile();
          cif.updateInput();
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  public Book getBook(String booktitle) {
    Book book = null;
    ArrayList<Book> booklist = new ArrayList<Book>();
    ReadInputFile rbf = new ReadInputFile();

    booklist = rbf.bookList();

    for (Book books : booklist) {
      if (books.getBookTitle().equals(booktitle)) {
        book = books;
      }
    }
    return book;
  }

  public User getUser(String userid) {
    HashMap<String, User> userlist = new HashMap<String, User>();
    ReadInputFile rbf = new ReadInputFile();
    userlist = rbf.userList();
    User user = userlist.get(userid);
    return user;
  }

  public boolean userExist(String userid) {
    HashMap<String, User> userlist = new HashMap<String, User>();
    ReadInputFile rbf = new ReadInputFile();

    userlist = rbf.userList();

    if (userlist.containsKey(userid)) {
      return true;
    } else {
      return false;
    }
  }

}
