package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;

@JsonPropertyOrder(alphabetic = true)
public class AuthorListDto extends LinksDto{
    private Author[] authors;

    /**
     * @param authors
     */
    public AuthorListDto(Author[] authors) {
   	super();
   	this.authors = authors;
    }

    /**
     * @return the authors
     */
    public Author[] getAuthors() {
	return authors;
    }

    /**
     * @param authors
     *            the authors to set
     */
    public void setAuthor(Author[] authors) {
	this.authors = authors;
    }
}
