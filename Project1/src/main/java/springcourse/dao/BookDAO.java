package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Book;
import springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void saveBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(book_name,author,release_year) values (?,?,?)", book.getBookName(),
                book.getAuthor(),book.getReleaseYear());
    }

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{bookId},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void update(int bookId, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET book_name = ?, author = ?, release_year = ? WHERE book_id = ?",
                updatedBook.getBookName(),updatedBook.getAuthor(),updatedBook.getReleaseYear(), bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", bookId);
    }

    public void makeOwner(Book book, Person person){
        jdbcTemplate.update("UPDATE Book Set id = ? where book_id = ?", person.getId(), book.getBookId());
    }

    public Person showOwner(int bookId){
        return jdbcTemplate.query("SELECT * FROM Person Left JOIN Book ON person.id = book.id where book.book_id = ?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }



    public void release(int bookId){
        jdbcTemplate.update("UPDATE Book set id = null where book_id = ?", bookId);
    }
}
