package springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcourse.models.Book;
import springcourse.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Person findPersonByBooks(Book book);

    Person findByName(String name);
}
