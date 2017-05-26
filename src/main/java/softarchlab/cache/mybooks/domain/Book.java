package softarchlab.cache.mybooks.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import softarchlab.cache.mybooks.enums.Genre;

@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;

    private String author;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startReadingDate;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endReadingDate;

    private Long rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String review;

    @ManyToOne
    private Reader reader;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book")
    private List<Note> notes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getStartReadingDate() {
        return startReadingDate;
    }

    public void setStartReadingDate(Date startReadingDate) {
        this.startReadingDate = startReadingDate;
    }

    public Date getEndReadingDate() {
        return endReadingDate;
    }

    public void setEndReadingDate(Date endReadingDate) {
        this.endReadingDate = endReadingDate;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void copyFields(Book fromBook) {
        this.id = fromBook.getId();
        this.title = fromBook.getTitle();
        this.author = fromBook.getAuthor();
        this.startReadingDate = fromBook.getStartReadingDate();
        this.endReadingDate = fromBook.getEndReadingDate();
        this.rating = fromBook.getRating();
        this.genre = fromBook.getGenre();
        this.review = fromBook.getReview();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((endReadingDate == null) ? 0 : endReadingDate.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((reader == null) ? 0 : reader.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((startReadingDate == null) ? 0 : startReadingDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (endReadingDate == null) {
			if (other.endReadingDate != null)
				return false;
		} else if (!endReadingDate.equals(other.endReadingDate))
			return false;
		if (genre != other.genre)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (reader == null) {
			if (other.reader != null)
				return false;
		} else if (!reader.equals(other.reader))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (startReadingDate == null) {
			if (other.startReadingDate != null)
				return false;
		} else if (!startReadingDate.equals(other.startReadingDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
    

}