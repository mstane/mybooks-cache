package softarchlab.cache.mybooks.repository.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import softarchlab.cache.mybooks.domain.Book;
import softarchlab.cache.mybooks.domain.Reader;

@Repository
public interface BookRepository
        extends QueryDslPredicateExecutor<Book>, JpaRepository<Book, Long>, BookRepositoryCustom {

    Page<Book> findByReader(Reader reader, Pageable pageable);

    Page<Book> findByReaderId(Long readerId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:keyword) OR  LOWER(b.author) = LOWER(:keyword)")
    Page<Book> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
