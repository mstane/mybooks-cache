package softarchlab.cache.mybooks.repository.data;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import softarchlab.cache.mybooks.domain.QBook;
import softarchlab.cache.mybooks.domain.QNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;

import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.Note;
import softarchlab.cache.mybooks.domain.SearchItem;
import softarchlab.cache.mybooks.enums.Genre;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    NoteRepository noteRepository;

    @Override
    public Page<SearchItem> searchContents(Long readerId, String keyword, Genre genre, Pageable pageable) {
        List<SearchItem> totalContents = searchContents(readerId, "%" + keyword + "%", genre);

        int firstResult = pageable.getPageNumber() * pageable.getPageSize();
        List<SearchItem> pageContent = new ArrayList<>();
        for (int i = firstResult; i < totalContents.size() && i < firstResult + pageable.getPageSize(); i++) {
            pageContent.add(totalContents.get(i));
        }

        return new PageImpl<>(pageContent, pageable, totalContents.size());
    }

    public List<SearchItem> searchContents(Long readerId, String keyword, Genre genre) {
        List<SearchItem> bookSearchItems = searchBooks(readerId, keyword, genre);
        List<SearchItem> noteSearchItems = searchNotes(readerId, keyword, genre);
        bookSearchItems.addAll(noteSearchItems);
        return bookSearchItems;
    }

    private List<SearchItem> searchBooks(Long readerId, String keyword, Genre genre) {
        QBook book = QBook.book;
        BooleanBuilder where = new BooleanBuilder();
        where.and(book.reader.id.eq(readerId));
        where.and(book.title.likeIgnoreCase(keyword).or(book.author.likeIgnoreCase(keyword))
                .or(book.review.likeIgnoreCase(keyword)));
        if (genre != null) {
            where.and(book.genre.eq(genre));
        }
        Iterable<Book> books = bookRepository.findAll(where);
        return StreamSupport.stream(books.spliterator(), false).map(SearchItem::new).collect(toList());

    }

    private List<SearchItem> searchNotes(Long readerId, String keyword, Genre genre) {
        QNote note = QNote.note;
        BooleanBuilder where = new BooleanBuilder();
        where.and(note.book.reader.id.eq(readerId));
        where.and(note.title.likeIgnoreCase(keyword).or(note.content.likeIgnoreCase(keyword)));
        if (genre != null) {
            where.and(note.book.genre.eq(genre));
        }
        Iterable<Note> notes = noteRepository.findAll(where);
        return StreamSupport.stream(notes.spliterator(), false).map(SearchItem::new).collect(toList());
    }

    @Override
    public Long countSearch(Long readerId, String keyword, Genre genre) {
        List<SearchItem> list = searchContents(readerId, keyword, genre);
        return Long.valueOf(list.size());
    }

}
