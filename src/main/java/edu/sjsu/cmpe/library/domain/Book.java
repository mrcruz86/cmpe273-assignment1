package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"isbn","title","publication-date","language"
	,"num-pages","status","reviews","authors"})
public class Book {
	
	private long isbn;
	private String title;
	
	@JsonProperty("publication-date")
	private String pubDate;
	
	@JsonProperty("language")
	private String lang;
	
	@JsonProperty("num-pages")
	private long numOfPages;
	private String status;
	private Author[] authors;
	private List<Review> reviews = new ArrayList<Review>();

	// add more fields here

	/**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the pubDate
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate
	 *            the pubDate to set
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the numOfPages
	 */
	public long getNumOPages() {
		return numOfPages;
	}

	/**
	 * @param numOfPages
	 *            the numOfPages to set
	 */
	public void setNumOfPages(long numOfPages) {
		this.numOfPages = numOfPages;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the authors
	 */
	public Author[] getAuthors() {
		return authors;
	}
	
	/**
	 * @return the author
	 */
	public Author getAuthor(int i) {
		return authors[i];
	}

	/**
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}
	
	/**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}
	
	/**
	 * @return the review
	 */
	public Review getReview(int i) {
		return reviews.get(i);
	}

	/**
	 * @param review
	 *            the review to set
	 */
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
}

