package softarchlab.cache.mybooks.service;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.Note;
import softarchlab.cache.mybooks.repository.data.BookRepository;
import softarchlab.cache.mybooks.repository.data.NoteRepository;

@Service
@Transactional
@CacheDefaults(cacheName = "notes")
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    @PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
    @CacheResult
    public Note findNote(Long id) {
        return noteRepository.findOne(id);
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessBook(principal, #bookId)")
    @CachePut
    public Note saveNote(Long bookId, @CacheKey Long id, @CacheValue Note note) {
        Book book = bookRepository.findOne(bookId);
        note.setBook(book);
        return noteRepository.save(note);
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
    @CacheRemove
    public void deleteNote(Long id) {
        noteRepository.delete(id);
    }

}
