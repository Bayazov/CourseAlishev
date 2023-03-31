package springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springcourse.models.Book;
import springcourse.models.Person;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book,Integer> {

    List<Book> findByBookNameStartingWith(String title);
}
