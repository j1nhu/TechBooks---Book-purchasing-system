package fileio;

import java.io.*;

public class CopyInputFile {
  public void copyInput() {
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      File file = new File("outputtext.txt");
      if (!file.exists()){
        file.createNewFile();
      }
      br = new BufferedReader(new FileReader("input.txt"));
      pw = new PrintWriter(new FileWriter("outputtext.txt", false));
      String line;
      while ((line = br.readLine()) != null) {       
        pw.println(line);
      }
      br.close();
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void updateInput() {
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      br = new BufferedReader(new FileReader("output.txt"));
      pw = new PrintWriter(new FileWriter("input.txt", false));
      String line;
      while ((line = br.readLine()) != null) {       
        pw.println(line);
      }
      br.close();
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void copyHead() {
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      br = new BufferedReader(new FileReader("outputtext.txt"));
      pw = new PrintWriter(new FileWriter("output.txt", false));
      String line;
      int counter = 1;
      while (((line = br.readLine()) != null) && (counter<3)) {       
        pw.println(line);
        counter ++;
      }
      br.close();
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void copyBody(String userid, String booktitle) {
    BufferedReader br = null;
    PrintWriter pw = null;

    try {
      br = new BufferedReader(new FileReader("outputtext.txt"));
      pw = new PrintWriter(new FileWriter("output.txt", true));
      String line;
      int counter = 1;
      while ((line = br.readLine()) != null) {
        if (counter<3) {
          System.out.println("not copy");
        } else {
          System.out.println(line);
          pw.println(line); 
        } 
        counter ++;
      }

      br.close();
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
}