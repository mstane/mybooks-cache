package softarchlab.cache.mybooks.domain;

public class SearchItem {

    private static final int LIMIT_OF_SHORTEN_CONTENT = 255;

    private String title;
    private String link;
    private String shortContent;

    public SearchItem(Book book) {
        super();
        this.title = "Book: " + book.getTitle();
        this.link = "#/books/view/" + book.getId();
        this.shortContent = "Author: " + book.getAuthor() + "; Review: " + getShortenContent(book.getReview());
    }

    public SearchItem(Note note) {
        super();
        this.title = "Note: " + note.getTitle();
        this.link = "#/notes/view/" + note.getId();
        this.shortContent = "For book: " + note.getBook().getTitle() + "; Content: "
                + getShortenContent(note.getContent());
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getShortContent() {
        return shortContent;
    }

    private String getShortenContent(String content) {
        if (content == null) {
            return "";
        } else {
            if (content.length() < LIMIT_OF_SHORTEN_CONTENT) {
                return content;
            } else {
                return content.substring(0, LIMIT_OF_SHORTEN_CONTENT) + "...";
            }
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((shortContent == null) ? 0 : shortContent.hashCode());
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
		SearchItem other = (SearchItem) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (shortContent == null) {
			if (other.shortContent != null)
				return false;
		} else if (!shortContent.equals(other.shortContent))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
    
    

}
