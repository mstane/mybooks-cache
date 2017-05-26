package softarchlab.cache.mybooks;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import softarchlab.cache.mybooks.domain.Book;

@SpringBootApplication
@EnableJpaRepositories("softarchlab.cache.mybooks.repository.data")
@EnableCaching
public class MyBooksApplication {
    
    MyBooksApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(MyBooksApplication.class, args);
    }
    
    @Bean(destroyMethod = "close")
    public CachingProvider createCachingProvider() {
        CachingProvider provider = Caching.getCachingProvider();
        return provider;
    }

    @Bean(destroyMethod = "close")
    public CacheManager createCacheManager(CachingProvider cachingProvider) {
        CacheManager manager = cachingProvider.getCacheManager();
        return manager;
    }
    
    @Bean
    public JCacheCacheManager cacheCacheManager(CacheManager cacheManager) {
        return new JCacheCacheManager(cacheManager);
    }

    @Bean(name = "booksCacheConfig")
    public MutableConfiguration<Long, Book> createBooksCacheConfig() {
        MutableConfiguration<Long, Book> config
                = new MutableConfiguration<Long, Book>()
//                .setStatisticsEnabled(true)
//                .setManagementEnabled(true)
                ;
        return config;
    }

    @Bean(name = "booksCache", destroyMethod = "close")
    public Cache<Long, Book> createBooksCache(
    								CacheManager cacheManager, 
                                    @Qualifier("booksCacheConfig") MutableConfiguration<Long, Book> config
                              ) {
        return cacheManager.createCache("books", config);
    }

    @Bean(name = "notesCacheConfig")
    public MutableConfiguration<Long, Book> createNotesCacheConfig() {
        MutableConfiguration<Long, Book> config
                = new MutableConfiguration<Long, Book>()
//                .setStatisticsEnabled(true)
//                .setManagementEnabled(true)
                ;
        return config;
    }

    @Bean(name = "notesCache", destroyMethod = "close")
    public Cache<Long, Book> createNotesCache(
    								CacheManager cacheManager, 
                                    @Qualifier("notesCacheConfig") MutableConfiguration<Long, Book> config
                              ) {
        return cacheManager.createCache("notes", config);
    }
    
    @Bean(name = "readersCacheConfig")
    public MutableConfiguration<Long, Book> createReadersCacheConfig() {
        MutableConfiguration<Long, Book> config
                = new MutableConfiguration<Long, Book>()
//                .setStatisticsEnabled(true)
//                .setManagementEnabled(true)
                ;
        return config;
    }

    @Bean(name = "readersCache", destroyMethod = "close")
    public Cache<Long, Book> createReadersCache(
    								CacheManager cacheManager, 
                                    @Qualifier("readersCacheConfig") MutableConfiguration<Long, Book> config
                              ) {
        return cacheManager.createCache("readers", config);
    }





}
