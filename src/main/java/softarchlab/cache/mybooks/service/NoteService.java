package softarchlab.cache.mybooks.service;

import softarchlab.cache.mybooks.domain.Note;

public interface NoteService {

    public abstract Note findNote(Long id);

    public abstract Note saveNote(Long bookId, Long id, Note note);

    public abstract void deleteNote(Long id);

}
