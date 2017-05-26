package softarchlab.cache.mybooks.service;

import java.util.Optional;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.enums.SystemRole;
import softarchlab.cache.mybooks.repository.data.ReaderRepository;

@Service
@Transactional
@CacheDefaults(cacheName = "readers")
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("@authorizationService.isAdmin(principal)")
    public Page<Reader> findAllReaders(int pageNumber, int pageSize) {
        return readerRepository.findAll(new PageRequest(pageNumber, pageSize));
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
    @CacheResult
    public Reader findReader(Long id) {
        return readerRepository.findOne(id);
    }

    @Override
    @PreAuthorize("@authorizationService.isAdmin(principal)")
    public Page<Reader> search(String keyword, int pageNumber, int pageSize) {
        return readerRepository.search(keyword.toLowerCase(), new PageRequest(pageNumber, pageSize));
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessReader(principal, #reader)")
    @CachePut
    public Reader saveReader(@CacheKey Long id, @CacheValue Reader reader) {
        return readerRepository.save(reader);
    }

    @Override
    @PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
    @CacheRemove
    public void deleteReader(Long id) {
        readerRepository.delete(id);
    }

    @Override
    public Optional<Reader> findByUsername(String username) {
        return readerRepository.findByUsername(username);
    }

    @Override
    public Optional<Reader> findByEmail(String email) {
        return readerRepository.findByEmail(email);
    }

    @Override
    public Reader registerReader(Reader reader) {
        reader.setSystemRole(SystemRole.COMMON);
        reader.setPassword(encodePassword(reader.getPassword()));
        return readerRepository.save(reader);
    }

    @Override
    public Reader changePassword(String generatedPassword, Reader reader) {
        reader.setPassword(encodePassword(generatedPassword));
        return readerRepository.save(reader);
    }

    private String encodePassword(String password) {
        if (password != null) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

}
