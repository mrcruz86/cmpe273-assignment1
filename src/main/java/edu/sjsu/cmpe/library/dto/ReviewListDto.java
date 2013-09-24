package edu.sjsu.cmpe.library.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder({"reviews","link"})
public class ReviewListDto extends LinksDto{
    private List<Review> reviews;

    /**
     * @param reviews
     */
    public ReviewListDto(List<Review> reviews) {
   	super();
   	this.reviews = reviews;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
	return reviews;
    }

    /**
     * @param reviews
     *            the reviews to set
     */
    public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
    }
}
