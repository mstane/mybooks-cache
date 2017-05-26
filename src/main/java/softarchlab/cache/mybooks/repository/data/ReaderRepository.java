package softarchlab.cache.mybooks.repository.data;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import softarchlab.cache.mybooks.domain.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>, QueryDslPredicateExecutor<Reader> {

    Optional<Reader> findByUsername(String username);

    Optional<Reader> findByEmail(String email);

    @Query("SELECT r FROM Reader r WHERE " + "LOWER(r.username) LIKE %:keyword%" + " OR LOWER(r.email) LIKE %:keyword%")
    Page<Reader> search(@Param("keyword") String keyword, Pageable pageable);

    Page<Reader> findByUsernameContaining(String keyword, Pageable pageable);

}
