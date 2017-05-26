package softarchlab.cache.mybooks.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.service.ReaderService;

@RestController
@RequestMapping("/rest/profiles")
public class ProfileRestController {

    @Autowired
    ReaderService readerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Reader get(@PathVariable("id") long id) {
        return this.readerService.findReader(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Reader update(@PathVariable("id") long id, @RequestBody @Valid Reader reader) {
        reader.setPassword(encodePassword(reader.getPassword()));
        return readerService.saveReader(id, reader);
    }

    private String encodePassword(String password) {
        if (password != null) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

}
