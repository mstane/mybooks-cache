package softarchlab.cache.mybooks.test.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import softarchlab.cache.mybooks.MyBooksApplication;
import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.Note;
import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.enums.Genre;
import softarchlab.cache.mybooks.enums.SystemRole;
import softarchlab.cache.mybooks.repository.data.BookRepository;
import softarchlab.cache.mybooks.repository.data.NoteRepository;
import softarchlab.cache.mybooks.repository.data.ReaderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MyBooksApplication.class)
@ContextConfiguration(classes = {MyBooksApplication.class})
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class BaseRestControllerTest {

    protected MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected MockMvc mockMvc;

    protected MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;


    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    protected void setConverters(HttpMessageConverter<?>[] converters) {
        HttpMessageConverter<?> httpMessageConverter;
        
        httpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", httpMessageConverter);
        
        mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter)httpMessageConverter;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected String encodePassword(String password) {
        if (password != null) {
            password = passwordEncoder.encode(password);
            return password;
        }
        return null;
    }

    protected Book loadOneBook() {
        return loadBooks(1).get(0);
    }

    protected Book getOneBook() {
        return getBooks(1).get(0);
    }

    protected List<Book> loadBooks(int number) {
        return bookRepository.save(getBooks(number));
    }

    protected List<Book> getBooks(int number) {
        Reader reader = loadOneReader();
        Book book;
        List<Book> books = new ArrayList<Book>();

        for (int i = 0; i < number; i++) {
            book = new Book();
            book.setTitle("Priamos Damokles Hyacinthus Deimos" + i);
            book.setAuthor("Tuth Jeggregh K'gassen" + i);
            book.setRating((long) number);
            book.setGenre(Genre.SATIRE);
            book.setReview("The book is fictional, it doesn't exist and the data are used as placeholder names." + i);
            book.setStartReadingDate(new Date());
            book.setEndReadingDate(new Date());
            book.setReader(reader);
            books.add(book);
        }

        return books;
    }

    protected Reader loadOneReader() {
        return loadReaders(1).get(0);
    }

    protected Reader getOneReader() {
        return getReaders(1).get(0);
    }

    protected List<Reader> loadReaders(int number) {
        return readerRepository.save(getReaders(number));
    }

    protected List<Reader> getReaders(int number) {
        Reader reader;
        List<Reader> readers = new ArrayList<Reader>();

        for (int i = 0; i < number; i++) {
            reader = new Reader();
            reader.setUsername("Agarf Rac" + i);
            reader.setEmail("agarf.rac" + i + "@example.com");
            reader.setPassword(encodePassword("Mb.1234"));
            reader.setSystemRole(SystemRole.COMMON);
            readers.add(reader);
        }

        return readers;
    }

    protected Reader loadMainReader() {
        Reader reader = new Reader();
        reader.setUsername("Agarf Rac");
        reader.setEmail("agarf.rac@example.com");
        reader.setPassword(encodePassword("Mb.1234"));
        reader.setSystemRole(SystemRole.COMMON);
        reader = readerRepository.save(reader);
        return reader;
    }

    protected Note loadOneNote() {
        return loadNotes(1).get(0);
    }

    protected Note getOneNote() {
        return getNotes(1).get(0);
    }

    protected List<Note> loadNotes(int number) {
        return noteRepository.save(getNotes(number));
    }

    protected List<Note> getNotes(int number) {
        Book book = loadOneBook();

        Note note;
        List<Note> notes = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            note = new Note();
            note.setBook(book);
            note.setTitle("Man and dolphins" + i);
            note.setContent("The dolphins had always believed that they were far more intelligent than man ..." + i);
            note.setCreatedTime(new Date());
            note.setModifiedTime(new Date());
            notes.add(note);
        }

        return notes;
    }

}
