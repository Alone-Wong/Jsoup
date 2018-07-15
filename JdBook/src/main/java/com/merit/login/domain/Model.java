package com.merit.login.domain;

public class Model {

	private String bookID;
	private String bookName;
	private String bookPrice;
	public Model() {
		super();
	}
	public Model(String bookID, String bookName, String bookPrice) {
		super();
		this.bookID = bookID;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}
	@Override
	public String toString() {
		return "Model [bookID=" + bookID + ", bookName=" + bookName + ", bookPrice=" + bookPrice + "]";
	}
	
	
	
	
}
