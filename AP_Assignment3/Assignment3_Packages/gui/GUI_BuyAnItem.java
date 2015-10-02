package gui;

import fileio.*;
import techstore.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI_BuyAnItem extends JPanel {
  private static final long serialVersionUID = 7697597190563019977L;
  private JPanel topPanel;
  private JPanel bottomPanel;
  private JPanel topMain;
  private JPanel topSecond;
  private JPanel topMainLeft;
  private JPanel topMainRight;
  private JList<String> userList;
  private JComboBox<String> itemTypeComb;
  private JComboBox<String> copyTypeComb;
  private JList<String> matchingList;
  private JEditorPane purchaseMessage;
  private JButton save;
  private JButton purchaseButton;

  private DefaultListModel<String> userlistModel;
  private DefaultListModel<String> matchingItemlistModel;

  public GUI_BuyAnItem() {
    matchingItemlistModel = new DefaultListModel<String>();
    getBookList();
    getUserList();
    BorderLayout bpage1 = new BorderLayout();
    bpage1.setVgap(10);
    setLayout(bpage1);
    createTopPanel();
    createP1BottomPanel();
    add(topPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  public void getBookList() {
    matchingItemlistModel.clear();
    ArrayList<Book> booklist = new ArrayList<Book>();
    ReadInputFile rbf = new ReadInputFile();
    booklist = rbf.bookList();

    for (Book books : booklist) {
      matchingItemlistModel.addElement(books.getBookTitle() + ", " + books.getNumPhysical() + " copies, $" + books.getphyprice());
    }
  }

  public void getEBookList() {
    matchingItemlistModel.clear();
    ArrayList<Book> eBooklist = new ArrayList<Book>();

    ReadInputFile rbf = new ReadInputFile();
    eBooklist = rbf.getEBooks();

    for (Book books : eBooklist) {
      matchingItemlistModel.addElement(books.getBookTitle() + ", $" + books.geteprice());
    }
  }

  public void getUserList() {
    userlistModel = new DefaultListModel<>();
    HashMap<String, User> userlist = new HashMap<String, User>();

    ReadInputFile rbf = new ReadInputFile();
    userlist = rbf.userList();

    Set<String> set = userlist.keySet();
    for (String key : set) {
      System.out.println(key);
      userlistModel.addElement(key + ", " + userlist.get(key).getName());
    }
  }

  public void createTopPanel() {
    topPanel = new JPanel();
    BorderLayout btop1 = new BorderLayout();
    btop1.setVgap(20);
    topPanel.setLayout(btop1);
    createTopMain();
    createTopSecond();

    topPanel.add(topMain, BorderLayout.CENTER);
    topPanel.add(topSecond, BorderLayout.SOUTH);
  }

  public void createTopMain() {
    topMain = new JPanel();
    BorderLayout bl = new BorderLayout();
    bl.setHgap(20);
    topMain.setLayout(bl);
    createTopMainLeft();
    createTopMainRight();
    topMain.add(topMainLeft, BorderLayout.WEST);
    topMain.add(topMainRight, BorderLayout.CENTER);
  }

  public void createTopMainLeft() {
    topMainLeft = new JPanel();
    GridLayout gridLayout = new GridLayout(1, 2);
    gridLayout.setHgap(20);
    topMainLeft.setLayout(gridLayout);

    JPanel selectionLeftUser = new JPanel();
    BorderLayout bl = new BorderLayout();
    bl.setVgap(5);
    selectionLeftUser.setLayout(bl);
    //
    JLabel userLabel = new JLabel("User");

    userList = new JList<String>(userlistModel);
    userList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!matchingList.isSelectionEmpty()) {
          purchaseButton.setEnabled(true);
          save.setEnabled(true);
        }
      }
    });
    JScrollPane scrollPane1 = new JScrollPane(userList);

    JPanel selectionLeftItem = new JPanel();
    GridLayout gl = new GridLayout(2, 1);
    gl.setVgap(10);
    selectionLeftItem.setLayout(gl);
    //
    JPanel itemType = new JPanel();
    BorderLayout bl2 = new BorderLayout();
    bl2.setVgap(5);
    itemType.setLayout(bl2);

    JLabel itemTypeLabel = new JLabel("Item Type");

    itemTypeComb = new JComboBox<String>();
    itemTypeComb.addItem("Book");
    itemTypeComb.addItem("Software");
    itemTypeComb.addActionListener(new SelectItemTypeListener());

    JPanel copyType = new JPanel();
    BorderLayout bl3 = new BorderLayout();
    bl3.setVgap(5);
    copyType.setLayout(bl3);

    JLabel copyTypeLabel = new JLabel("eBook?");
    copyTypeComb = new JComboBox<String>();
    copyTypeComb.addItem("No");
    copyTypeComb.addItem("Yes");
    copyTypeComb.addActionListener(new SelectCopyTypeListener());

    selectionLeftUser.add(userLabel, BorderLayout.NORTH);
    selectionLeftUser.add(scrollPane1, BorderLayout.CENTER);

    itemType.add(itemTypeLabel, BorderLayout.NORTH);
    itemType.add(itemTypeComb, BorderLayout.CENTER);

    copyType.add(copyTypeLabel, BorderLayout.NORTH);
    copyType.add(copyTypeComb, BorderLayout.CENTER);

    selectionLeftItem.add(itemType);
    selectionLeftItem.add(copyType);

    topMainLeft.add(selectionLeftUser);
    topMainLeft.add(selectionLeftItem);
  }

  public void createTopMainRight() {
    topMainRight = new JPanel();
    BorderLayout bl = new BorderLayout();
    bl.setVgap(5);
    topMainRight.setLayout(bl);

    JLabel matchingLabel = new JLabel("Matching Items");

    matchingList = new JList<String>(matchingItemlistModel);
    matchingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    matchingList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!userList.isSelectionEmpty()) {
          purchaseButton.setEnabled(true);
          save.setEnabled(true);
        }
      }
    });

    JScrollPane scrollPane = new JScrollPane(matchingList);

    topMainRight.add(matchingLabel, BorderLayout.NORTH);
    topMainRight.add(scrollPane, BorderLayout.CENTER);
  }

  public void createTopSecond() {
    topSecond = new JPanel();
    BorderLayout bl = new BorderLayout();
    bl.setHgap(10);
    topSecond.setLayout(bl);
    purchaseButton = new JButton("PURCHASE");
    if (matchingList.isSelectionEmpty() || userList.isSelectionEmpty()) {
      purchaseButton.setEnabled(false);
    }
    purchaseButton.addActionListener(new PurchaseListener());

    topSecond.add(purchaseButton, BorderLayout.WEST);
    purchaseMessage = new JEditorPane();
    purchaseMessage.setEditable(false);
    purchaseMessage.setText("");
    topSecond.add(purchaseMessage, BorderLayout.CENTER);
  }

  public void createP1BottomPanel() {
    bottomPanel = new JPanel();
    FlowLayout fl = new FlowLayout();
    fl.setHgap(10);
    bottomPanel.setLayout(fl);

    JButton quit = new JButton("QUIT");
    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    bottomPanel.add(quit);
    save = new JButton("SAVE");
    if (matchingList.isSelectionEmpty() || userList.isSelectionEmpty()) {
      save.setEnabled(false);
    }
    save.addActionListener(new SaveToCartListener());
    bottomPanel.add(save);
  }

  class SelectCopyTypeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (copyTypeComb.getSelectedItem() == "Yes") {
        getEBookList();
      } else {
        getBookList();
      }
    }
  }

  class SelectItemTypeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (itemTypeComb.getSelectedItem() == "Software") {
        matchingItemlistModel.clear();
        matchingItemlistModel.addElement("No software available");
      } else {
        getBookList();
      }
    }
  }

  class SaveToCartListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (userList.isSelectionEmpty() || matchingList.isSelectionEmpty()) {
        purchaseMessage.setText("Please make sure you selected the user and the items you wish to purchase.");
      } else {
        WriteOutputFile wuf = new WriteOutputFile();

        String userid = userList.getSelectedValue().split(", ")[0];
        String booktitle = matchingList.getSelectedValue().split(", ")[0];
        boolean ebook;
        if (copyTypeComb.getSelectedItem().equals("Yes")) {
          ebook = true;
        } else {
          ebook = false;
        }
        if (wuf.addToCart(userid, booktitle, ebook)) {
          purchaseMessage.setText(booktitle + " saved in your shopping cart.");
          if (!ebook) {
            getBookList();
            save.setEnabled(false);
            purchaseButton.setEnabled(false);
          }
        } else {
          purchaseMessage.setText("Unfortunately, " + booktitle + " is out of stock or temporily not available in TechStore.");
        }
      }
    }
  }

  class PurchaseListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (userList.isSelectionEmpty() || matchingList.isSelectionEmpty()) {
        purchaseMessage.setText("Please make sure you selected the user and the items you wish to purchase.");
      } else {
        WriteOutputFile wuf = new WriteOutputFile();
        String booktitle = matchingList.getSelectedValue().split(", ")[0];
        String userid = userList.getSelectedValue().split(", ")[0];
        boolean ebook;
        if (copyTypeComb.getSelectedItem().equals("Yes")) {
          ebook = true;
        } else {
          ebook = false;
        }
        if (wuf.purchaseBook(booktitle, ebook, userid)) {
          purchaseMessage.setText("You have successfully purchased " + booktitle);
          if (!ebook) {
            getBookList();
            purchaseButton.setEnabled(false);
            save.setEnabled(false);
          }
        } else {
          purchaseMessage.setText("Unfortunately, " + booktitle + " is out of stock or temporily not available in TechStore.");
        }
      }
    }
  }

}
