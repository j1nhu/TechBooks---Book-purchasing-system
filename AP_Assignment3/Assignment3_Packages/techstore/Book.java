package techstore;

/**
 * This class hold the information of the Book.
 * @author JinHu
 */
public class Book 
{
  
	private String bookTitle;
	private String author;
	private int numPhysical;
	private boolean ebook;
	private double ePrice;
	private double physPrice;

	public Book(String bookTitle, String author, int numPhysical, boolean ebook, double ePrice, double physPrice)
	{
		this.bookTitle = bookTitle;
		this.author = author;
		this.numPhysical = numPhysical;
		this.ebook = ebook;
		this.ePrice = ePrice;
		this.physPrice = physPrice;
	}
	
	public String getBookTitle() {
    return bookTitle;
  }
	
	public String getAuthor() {
    return author;
  }
	
	public int getNumPhysical() {
    return numPhysical;
  }
	
	public boolean isEbook() {
    return ebook;
  }
	
	public double geteprice() {
	  return ePrice;
	}
	
	public double getphyprice() {
    return physPrice;
  }
  
	public void decreaseNumPhysical() {
	  this.numPhysical--;
	}
	
	public boolean available() {
	  if (numPhysical < 1) {
	    return false;
	  } else {
	    return true;
	  }  
	}
	
	public String printBook() {
	  return bookTitle + "|" + author;
	}
	
	public String printBookDetails() {
	  String ebookAv;
	  if (ebook) {
	    ebookAv = "yes";
	  } else {
	    ebookAv = "no";
	  }	  
    return bookTitle + "|" + author + "|" + numPhysical + "|" + ebookAv + "|" + physPrice + "|" + ePrice;
  }
}
