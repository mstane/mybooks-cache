package softarchlab.cache.mybooks.repository.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import softarchlab.cache.mybooks.domain.SearchItem;
import softarchlab.cache.mybooks.enums.Genre;

public interface BookRepositoryCustom {

    public Page<SearchItem> searchContents(Long readerId, String keyword, Genre genre, Pageable pageable);

    public Long countSearch(Long readerId, String keyword, Genre genre);

}
