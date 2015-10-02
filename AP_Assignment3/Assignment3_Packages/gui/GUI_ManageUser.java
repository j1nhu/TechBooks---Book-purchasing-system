package gui;
import techstore.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import fileio.*;

public class GUI_ManageUser extends JPanel{
  private static final long serialVersionUID = 6662705894721701698L;
  private JPanel topPanel;
  private JPanel bottomPanel;
  private JPanel topLeftPanel;
  private JPanel topLeftMain; 
  private JPanel topRightPanel;
  private JPanel topRightMain;
  private JPanel topRightDown;  
  private JList<String> cartList;
  private JList<String> userList;
  private JButton save;
  private JButton removeButton;
  private JTextField userIDText;    
  private JTextField userNameText;    
  private JTextField userPwText;    
  private JComboBox<String> userTypeComb;
  private JEditorPane createUserMsg;
  private ArrayList<Integer> deleteItem = new ArrayList<Integer>();
  private HashMap<String, ArrayList<Integer>> deleteMap = new HashMap<String, ArrayList<Integer>>();
  
  private DefaultListModel<String> userlistModel;
  private DefaultListModel<String> matchingItemlistModel;

  public GUI_ManageUser() {
    userlistModel = new DefaultListModel<>();
    getUserList();
    
    BorderLayout bLayout = new BorderLayout();
    bLayout.setHgap(10);
    setLayout(bLayout);
    
    createTopPanel();
    createBottomPanel();
    
    add(topPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }
  
  public void getUserList() {   
    userlistModel.clear();
    ReadInputFile ruf = new ReadInputFile();
    HashMap<String, User> userlist = new HashMap<String, User>();
    userlist = ruf.userList();
    Set<String> set = userlist.keySet();
    for (String key: set) {
      System.out.println(key);
      userlistModel.addElement(key + ", " + userlist.get(key).getName());
    }
    System.out.println("refreshed");
  }
  
  public void createTopPanel() {
    topPanel = new JPanel();
    GridLayout topPanelLayout = new GridLayout(1, 2);
    topPanelLayout.setHgap(40);
    topPanel.setLayout(topPanelLayout);
    
    createTopLeftPanel();
    createTopRightPanel();
    topPanel.add(topLeftPanel);
    topPanel.add(topRightPanel);
  }
  
  public void createTopLeftPanel() {
    topLeftPanel = new JPanel();
    BorderLayout bLayout = new BorderLayout();
    bLayout.setVgap(10);
    topLeftPanel.setLayout(bLayout);    
    createTopLeftMain();   
    removeButton = new JButton("REMOVE");
    if (cartList.isSelectionEmpty() || userList.isSelectionEmpty()) {
      removeButton.setEnabled(false);
    }
    removeButton.addActionListener(new DeleteFromListListener());

    topLeftPanel.add(topLeftMain, BorderLayout.CENTER);
    topLeftPanel.add(removeButton, BorderLayout.PAGE_END);
  }
  
  public void createTopLeftMain() {    
    topLeftMain = new JPanel();
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setVgap(10);
    topLeftMain.setLayout(gridLayout);
    
    JPanel user = new JPanel();
    BorderLayout bLayout = new BorderLayout();
    bLayout.setVgap(5);
    user.setLayout(bLayout);
     
    JLabel userLabel = new JLabel("User");
    
    userList = new JList<String>(userlistModel);
    userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    userList.addListSelectionListener(new LoadCartItemListener());
    JScrollPane scrollPane1 = new JScrollPane(userList);
    
    user.add(userLabel, BorderLayout.NORTH);
    user.add(scrollPane1, BorderLayout.CENTER);
   
    JPanel cart = new JPanel();
    BorderLayout bLayout2 = new BorderLayout();
    bLayout2.setVgap(5);
    cart.setLayout(bLayout2);
       
    JLabel cartLabel = new JLabel("Shopping Cart");   

    cartList = new JList<String>();
    cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    cartList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        removeButton.setEnabled(true);
      }
    });
    
    JScrollPane scrollPane2 = new JScrollPane(cartList);
    
    cart.add(cartLabel, BorderLayout.NORTH);
    cart.add(scrollPane2, BorderLayout.CENTER);
    
    topLeftMain.add(user);
    topLeftMain.add(cart);
  }

  public void createTopRightPanel() {
    topRightPanel = new JPanel();
    BorderLayout bLayout = new BorderLayout();
    bLayout.setVgap(10);
    topRightPanel.setLayout(bLayout);
    
    JLabel newUserLabel = new JLabel("Create New User");
    createTopRightMain();
    createTopRightDown();
    
    topRightPanel.add(newUserLabel, BorderLayout.PAGE_START);
    topRightPanel.add(topRightMain, BorderLayout.CENTER);
    topRightPanel.add(topRightDown, BorderLayout.PAGE_END);
  }
  
  public void createTopRightMain() {
    topRightMain = new JPanel();
    BorderLayout bLayout = new BorderLayout();
    bLayout.setHgap(10);
    topRightMain.setLayout(bLayout);
    
    JPanel newUserInfoLeft = new JPanel();
    GridLayout gl = new GridLayout(4, 1);
    gl.setVgap(10);
    newUserInfoLeft.setLayout(gl);    
      
    JLabel userIDLabel = new JLabel("id:");   
    JLabel userNameLabel = new JLabel("name:");    
    JLabel userPwLabel = new JLabel("password:");   
    JLabel userTypeLabel = new JLabel("type:"); 
    
    newUserInfoLeft.add(userIDLabel);
    newUserInfoLeft.add(userNameLabel);
    newUserInfoLeft.add(userPwLabel);
    newUserInfoLeft.add(userTypeLabel);  
    
    JPanel newUserInfoRight = new JPanel();
    newUserInfoRight.setLayout(gl);  
    
    userTypeComb = new JComboBox<String>();
    userTypeComb.addItem("Admin");
    userTypeComb.addItem("Member");
    userTypeComb.addItem("Non-Member");
    userIDText = new JTextField(15);
    userNameText = new JTextField(15);
    userPwText = new JTextField(15);
    
 
    newUserInfoRight.add(userIDText);
    newUserInfoRight.add(userNameText);
    newUserInfoRight.add(userPwText);
    newUserInfoRight.add(userTypeComb);
    
    topRightMain.add(newUserInfoLeft, BorderLayout.WEST);
    topRightMain.add(newUserInfoRight, BorderLayout.EAST);
  }
  
  public void createTopRightDown() {    
    topRightDown = new JPanel();
    BorderLayout bl = new BorderLayout();
    bl.setVgap(10);
    topRightDown.setLayout(bl);    
   
    JButton createUserButton = new JButton("Create User"); 
    createUserButton.addActionListener(new CreateNewUserListener());
   
    createUserMsg = new JEditorPane();    
    createUserMsg.setText("");
    createUserMsg.setEditable(false);
    
    topRightDown.add(createUserButton, BorderLayout.PAGE_START);
    topRightDown.add(createUserMsg, BorderLayout.CENTER);    
  }
  
  public void createBottomPanel() {  
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
    save = new JButton("SAVE");
    if (userList.isSelectionEmpty()) {
      save.setEnabled(false);
    }
    save.addActionListener(new DeleteFromCartListener());
    bottomPanel.add(quit);
    bottomPanel.add(save);
  }
  
  public void reloadShoppingCart(String id) {
    matchingItemlistModel.clear();
    ArrayList<CartItem> cartlist = new ArrayList<CartItem>();
    ReadInputFile ruf = new ReadInputFile();
    cartlist = ruf.getCart(id).getItems();
    for (int i=0; i < cartlist.size(); i++) {
      matchingItemlistModel.add(i, cartlist.get(i).getTitle() + ", 1 copy, $" + cartlist.get(i).getPricePaid());
    }
    cartList.setModel(matchingItemlistModel);
  }
  
  class LoadCartItemListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      save.setEnabled(true);
      removeButton.setEnabled(false);
      deleteItem.clear();
      deleteMap.clear();
      String userid = userList.getSelectedValue().split(", ")[0];
      matchingItemlistModel = new DefaultListModel<String>();
      reloadShoppingCart(userid);  
    }
  }
  
  class CreateNewUserListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (userIDText.getText().isEmpty() || userNameText.getText().isEmpty() || userPwText.getText().isEmpty()) {
        createUserMsg.setText("Please fill all the information required to create a new user.");
      } else {
        String usertype = userTypeComb.getSelectedItem().toString();
        WriteOutputFile wuf = new WriteOutputFile();        
        if (wuf.createNewUser(userIDText.getText(), userPwText.getText(), userNameText.getText(), usertype)) {
          createUserMsg.setText("New " + usertype + " created! Re-login to purchase books on techstore!");
          getUserList();          
        } else {
          createUserMsg.setText("Please check your input! Note: UserID must be unique.");
        }
      }
    }
  }
  
  class DeleteFromListListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (cartList.isSelectionEmpty() || userList.isSelectionEmpty()) {
        createUserMsg.setText("Please make sure you selected the user and the items you wish to purchase.");
      } else {
        createUserMsg.setText(cartList.getSelectedValue().split(", ")[0] + " removed from your cart! Click Save before you quit or switch to another user.");
        deleteItem.add(cartList.getSelectedIndex());
        deleteMap.put(userList.getSelectedValue().split(", ")[0], deleteItem);
        matchingItemlistModel.remove(cartList.getSelectedIndex());       
      }
    }
  }
  
  class DeleteFromCartListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (userList.isSelectionEmpty()) {
        createUserMsg.setText("Please make sure you selected the user and the items you wish to purchase.");
      } else {
        WriteOutputFile wuf = new WriteOutputFile(); 
        if (!deleteMap.containsKey(userList.getSelectedValue().split(", ")[0])) {
          createUserMsg.setText("No changes have made for you, " + userList.getSelectedValue().split(", ")[1]);
        } else {
          
          for (int i = 0; i < deleteMap.get(userList.getSelectedValue().split(", ")[0]).size(); i++) {
            if (wuf.deleteFromCart(userList.getSelectedValue().split(", ")[0], deleteMap.get(userList.getSelectedValue().split(", ")[0]).get(i))) {
              createUserMsg.setText("You have deleted " + deleteMap.get(userList.getSelectedValue().split(", ")[0]).size() + " items from the cart!"); 
            }
          }
          deleteMap.get(userList.getSelectedValue().split(", ")[0]).clear();
        }       
      }
    }
  }
  
}
