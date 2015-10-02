package gui;
import java.awt.*;
import javax.swing.*;

public class GUITab extends JFrame {  
  /**
   * 
   */
  private static final long serialVersionUID = -7044829792667990598L;
  private JTabbedPane tabbedPane; 
 
  public GUITab() {
    setTitle("Tabbed Pane Application");
    setSize(600, 350);
    setBackground(Color.gray);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    getContentPane().add(topPanel);

    // Create the tab pages
    GUI_BuyAnItem buyAnItem = new GUI_BuyAnItem();
    GUI_ManageUser manageUser = new GUI_ManageUser();
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Buy an Item", buyAnItem);
    tabbedPane.addTab("Manage Users", manageUser);
    topPanel.add(tabbedPane, BorderLayout.CENTER);
  }

}
