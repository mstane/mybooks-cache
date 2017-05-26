package softarchlab.cache.mybooks.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import softarchlab.cache.mybooks.domain.Note;
import softarchlab.cache.mybooks.service.NoteService;

@RestController
@RequestMapping("/rest/notes")
public class NoteRestController {

    @Autowired
    NoteService noteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Note get(@PathVariable("id") long id) {
        return this.noteService.findNote(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Note create(@RequestBody @Valid Note note, @RequestParam(value = "bookId") long bookId) {
        return noteService.saveNote(bookId, null, note);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Note update(@PathVariable("id") long id, @RequestBody @Valid Note note,
            @RequestParam(value = "bookId") long bookId) {
        return noteService.saveNote(bookId, id, note);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        this.noteService.deleteNote(id);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}
