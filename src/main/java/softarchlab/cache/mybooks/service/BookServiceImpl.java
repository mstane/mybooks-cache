package softarchlab.cache.mybooks.service;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.SearchItem;
import softarchlab.cache.mybooks.enums.Genre;
import softarchlab.cache.mybooks.repository.data.BookRepository;
import softarchlab.cache.mybooks.repository.data.ReaderRepository;
import softarchlab.cache.mybooks.security.UserDetailsImpl;

@Service
@Transactional
@CacheDefaults(cacheName = "books")
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Override
    public Page<Book> findReadersBooks(PageRequest pageRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return bookRepository.findByReaderId(userDetails.getId(), pageRequest);
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessBook(principal, #id)")
    @CacheResult
    public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Page<SearchItem> search(String keyword, Genre genre, PageRequest pageRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return bookRepository.searchContents(userDetails.getId(), keyword, genre, pageRequest);
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessBook(principal, #receivedBook)")
    @CachePut
    public Book saveBook(@CacheKey Long id, @CacheValue Book receivedBook) {
        Book book;

        if (id != null) {
            book = bookRepository.findOne(id);
            book.copyFields(receivedBook); // to preserve relationship to notes
        } else {
            book = receivedBook;
        }

        book = bookRepository.save(book);
        return book;
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessBook(principal, #id)")
    @CacheRemove
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

}
