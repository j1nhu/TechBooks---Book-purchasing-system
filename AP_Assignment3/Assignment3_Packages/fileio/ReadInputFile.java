package fileio;

import techstore.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ReadInputFile {
  private BufferedReader br;

  public ArrayList<Book> bookList() {
    ArrayList<Book> bookinfo = new ArrayList<Book>();
    try {
      File f = new File("input.txt");
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis);
      br = new BufferedReader(isr);
      String s = "";
      s = br.readLine();
      while ((s != null) & (!s.equals("#users"))) {
        s = br.readLine();
        s = br.readLine();
        while (!s.equals("#users")) {
          if (s.trim().length() > 0) {
            StringTokenizer st;
            st = new StringTokenizer(s, "|");
            while (st.hasMoreTokens()) {
              String title = st.nextToken();
              String author = st.nextToken();
              int numCopy = Integer.parseInt(st.nextToken());
              boolean eAvail;
              if (st.nextToken().equals("no")) {
                eAvail = false;
              } else {
                eAvail = true;
              }
              double phyPrice = Double.parseDouble(st.nextToken());
              double ePrice = Double.parseDouble(st.nextToken());
              Book book = new Book(title, author, numCopy, eAvail, ePrice, phyPrice);
              bookinfo.add(book);
            }
          }
          
          s = br.readLine();
        }
      }
    } catch (Exception e) {
      System.out.println("read book" + e);
    }
    return bookinfo;
  }

  public HashMap<String, User> userList() {
    HashMap<String, User> userinfo = new HashMap<String, User>();
    try {
      File f = new File("input.txt");
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis);
      br = new BufferedReader(isr);
      String s = "";
      s = br.readLine();
      while (s != null) {
        if (s.equals("#users")) {
          s = br.readLine();
          s = br.readLine();
          while (s != null) {
            if (s.trim().length() > 0) {
              StringTokenizer st;
              st = new StringTokenizer(s, "|");
              User user;
              Cart cart = new Cart();
              String id = st.nextToken();
              String pw = st.nextToken();
              String name = st.nextToken();
              String email = st.nextToken();
              String usertype = st.nextToken();
              if (usertype.equals("shopper")) {
                usertype += "|" + st.nextToken();
              }
              if (st.hasMoreTokens()) {
                int total = st.countTokens();
                int i = 0;
                while (i < total) {
                  String booktitle = st.nextToken();
                  st.nextToken();
                  boolean copytype;
                  if (st.nextToken().equals("ebook")) {
                    copytype = true;
                  } else {
                    copytype = false;
                  }
                  double price = Double.parseDouble(st.nextToken());
                  CartItem item = new CartItem(getBook(booktitle), price, copytype);
                  cart.add(item);
                  i = i + 4;
                }
              }
              user = new User(id, pw, name, email, usertype, cart);
              userinfo.put(id, user);
            }            
            s = br.readLine();
          }
        }
        s = br.readLine();
      }

    } catch (Exception e) {
      System.out.println("read user input " + e);
    }
    return userinfo;
  }

  public ArrayList<Book> getEBooks() {
    ArrayList<Book> ebooks = new ArrayList<Book>();
    for (Book book : bookList()) {
      if (book.isEbook()) {
        ebooks.add(book);
      }
    }
    return ebooks;
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

  public Cart getCart(String userid) {
    User user = userList().get(userid);
    Cart cart = user.getCart();
    return cart;
  }

}
