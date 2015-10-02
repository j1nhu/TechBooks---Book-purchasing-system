package fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DeleteUserFile {  
  public void deleteUserLineByID(String userid) {
    try {
      File f = new File("outputtext.txt");
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      StringTokenizer st;
      String line = br.readLine();
      int counter = 1;
      String deleteLine = "";
      while (line != null) {
        st = new StringTokenizer(line, "|");
        if ((counter >= 3) && (line.trim().length() > 0)) {
          if (st.nextToken().equals(userid)) {
            deleteLine = line;
            DeleteLine delete = new DeleteLine();
            delete.removeLineFromFile("outputtext.txt", deleteLine);
          }
        }
        line = br.readLine();
        counter++;
      }
    } catch (Exception ex) {
      System.out.println("Deleting error: " + ex);
    }
  }
  
  public void deleteBookLineByTitle(String booktitle) {
    try {
      File f = new File("outputtext.txt");
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      StringTokenizer st;
      String line = br.readLine();
      int counter = 1;
      String deleteLine = "";
      while (line != null) {
        st = new StringTokenizer(line, "|");
        if ((counter >= 3) && (line.trim().length() > 0)) {
          if (st.nextToken().equals(booktitle)) {
            deleteLine = line;
            DeleteLine delete = new DeleteLine();
            delete.removeLineFromFile("outputtext.txt", deleteLine);
          }
        }
        line = br.readLine();
        counter++;
      }
    } catch (Exception ex) {
      System.out.println("Deleting error: " + ex);
    }
  }
  
  public void deleteUserOutputByID(String userid) {
    try {
      File f = new File("output.txt");
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      StringTokenizer st;
      String line = br.readLine();
      int counter = 1;
      String deleteLine = "";
      while (line != null) {
        st = new StringTokenizer(line, "|");
        if ((counter >= 3) && (line.trim().length() > 0)) {
          if (st.nextToken().equals(userid)) {
            deleteLine = line;
            DeleteLine delete = new DeleteLine();
            delete.removeLineFromFile("output.txt", deleteLine);
          }
        }
        line = br.readLine();
        counter++;
      }
    } catch (Exception ex) {
      System.out.println("Deleting error: " + ex);
    }
  }

}
