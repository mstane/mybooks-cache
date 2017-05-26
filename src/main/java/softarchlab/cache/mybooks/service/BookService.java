package softarchlab.cache.mybooks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.SearchItem;
import softarchlab.cache.mybooks.enums.Genre;

public interface BookService {

    public Page<Book> findReadersBooks(PageRequest pageRequest);

    public abstract Book findBook(Long id);

    public abstract Page<SearchItem> search(String keyword, Genre genre, PageRequest pageRequest);

    public abstract Book saveBook(Long id, Book book);

    public abstract void deleteBook(Long id);

}
