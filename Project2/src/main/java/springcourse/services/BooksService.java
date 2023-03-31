package springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public Book findOneBook(int bookId){
        Optional<Book> findBook = booksRepository.findById(bookId);
        return findBook.orElse(null);
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(Sort.by("releaseYear"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> searchByTitle(String query){
        return booksRepository.findByBookNameStartingWith(query);
    }

    @Transactional
    public void saveBook(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int BookId){
        booksRepository.deleteById(BookId);
    }

    @Transactional
    public void update(int bookId, Book updatedBook){
        Book book = booksRepository.findById(bookId).get();

        updatedBook.setBookId(bookId);
        updatedBook.setOwner(book.getOwner());

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void release(int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setOwner(null);
        book.setTakenAt(null);
        booksRepository.save(book);
    }

    /*@Transactional
    public void release(int bookId){
        booksRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }*/

    @Transactional
    public void makeOwner(int bookId, Person owner){
        Book book = findOneBook(bookId);
        book.setOwner(owner);
        book.setTakenAt(new Date());
        booksRepository.save(book);
    }

    public List<Book> findAll(int page, int itemsPerPage){
       return booksRepository.findAll(PageRequest.of(page,itemsPerPage)).getContent();
    }


}
