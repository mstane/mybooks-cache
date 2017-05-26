package softarchlab.cache.mybooks.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import softarchlab.cache.mybooks.domain.Reader;

public interface ReaderService {

    public abstract Page<Reader> findAllReaders(int pageNumber, int pageSize);

    public abstract Reader findReader(Long id);

    public abstract Page<Reader> search(String keyword, int pageNumber, int pageSize);

    public abstract Reader saveReader(Long id, Reader reader);

    public abstract void deleteReader(Long id);

    public abstract Optional<Reader> findByUsername(String username);

    public abstract Optional<Reader> findByEmail(String email);

    public abstract Reader registerReader(Reader reader);

    public abstract Reader changePassword(String generatedPassword, Reader reader);

}
