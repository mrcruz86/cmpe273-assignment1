package edu.sjsu.cmpe.library.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.IntParam;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorListDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewListDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	/** bookRepository instance */
	private final BookRepositoryInterface bookRepository;

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	//Create Book

	@POST
	@Timed(name = "create-book")
	public Response createBook(Book request) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);

		String location = "/books/" + savedBook.getIsbn();
		LinksDto bookResponse = new LinksDto();
		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
		bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		bookResponse.addLink(new LinkDto("create-book", location, "POST"));

		return Response.status(201).entity(bookResponse).build();
	}

	//View Book
	
	@GET
	@Path("/{isbn}")
	@Timed(name = "view-book")
	public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		String location = "/books/" + book.getIsbn();
		BookDto bookResponse = new BookDto(book);
		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
		bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		bookResponse.addLink(new LinkDto("create-review", location + "/reviews", 
			"POST"));

		if(book.getReviews().size() > 0){
			bookResponse.addLink(new LinkDto("view-all-reviews", "/books/"
					+ book.getIsbn() + "/reviews", "GET"));
		}

		return bookResponse;
	}

	//Delete Book
	
	@DELETE
	@Path("/{isbn}")
	@Timed(name = "delete-view")
	public LinksDto deleteBookByIsbn(@PathParam("isbn") LongParam isbn) {
		bookRepository.deleteBookByISBN(isbn.get());
		LinksDto deleteResponse = new LinksDto();
		deleteResponse.addLink(new LinkDto("create-book", "/books", "POST"));
		return deleteResponse;
	}
	
	//Update Book
	
	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBook(@PathParam("isbn") LongParam isbn,
			@QueryParam("status") @DefaultValue("available") String status) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		book.setStatus(status);
		String location = "/books/" + book.getIsbn();
		LinksDto updateResponse = new LinksDto();
		updateResponse.addLink(new LinkDto("view-book", location, "GET"));
		updateResponse.addLink(new LinkDto("update-book", location, "PUT"));
		updateResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		updateResponse.addLink(new LinkDto("create-review", location + "/reviews", 
			"POST"));

		if(book.getReviews().size() > 0){
			updateResponse.addLink(new LinkDto("view-all-reviews", "/books/"
					+ book.getIsbn() + "/reviews", "GET"));
		}


		return Response.status(200).entity(updateResponse).build();
	}
	
	//Create Book Review
	
	@POST
	@Path("/{isbn}/reviews")
	@Timed(name = "create-review")
	public Response createReview(@PathParam("isbn") LongParam isbn, Review newReview) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		Review r = bookRepository.addReview(isbn.get(), newReview);
		String location = "/books/" + book.getIsbn() + "/reviews/" + r.getId();
		LinksDto reviewResponse = new LinksDto();
		reviewResponse.addLink(new LinkDto("view-review", location, "GET"));

		return Response.status(201).entity(reviewResponse).build();
	}
	
	//View Book Review
	
	@GET
	@Path("/{isbn}/reviews/{id}")
	@Timed(name = "view-review")
	public ReviewDto viewReview(@PathParam("isbn") LongParam isbn, @PathParam("id") IntParam id){
		Book book = bookRepository.getBookByISBN(isbn.get());
		Review r = book.getReview(id.get()-1);
		String location = "/books/" + book.getIsbn() + "/reviews/" + r.getId();
		ReviewDto reviewResponse = new ReviewDto(r);
		reviewResponse.addLink(new LinkDto("view-review", location, "GET"));
		return reviewResponse;
	}
	
	//View All Reviews for a Book
	
	@GET
	@Path("/{isbn}/reviews")
	@Timed(name = "view-all-reviews")
	public ReviewListDto viewAllReviews(@PathParam("isbn") LongParam isbn){
		Book book = bookRepository.getBookByISBN(isbn.get());
		List<Review> r = book.getReviews();
		ReviewListDto reviewResponse = new ReviewListDto(r);
		return reviewResponse;
	}
	
	//View Book Author
	
	@GET
	@Path("/{isbn}/authors/{id}")
	@Timed(name = "view-review")
	public AuthorDto viewAuthor(@PathParam("isbn") LongParam isbn, @PathParam("id") IntParam id){
		Book book = bookRepository.getBookByISBN(isbn.get());
		Author a = book.getAuthor(id.get()-1);
		String location = "/books/" + book.getIsbn() + "/authors/" + a.getId();
		AuthorDto authorResponse = new AuthorDto(a);
		authorResponse.addLink(new LinkDto("view-author", location, "GET"));
		return authorResponse;
	}
	
	//View All Authors for a Book
	
	@GET
	@Path("/{isbn}/authors")
	@Timed(name = "view-all-authors")
	public AuthorListDto viewAllAuthors(@PathParam("isbn") LongParam isbn){
		Book book = bookRepository.getBookByISBN(isbn.get());
		Author[] a = book.getAuthors();
		AuthorListDto authorResponse = new AuthorListDto(a);
		return authorResponse;
	}
}

