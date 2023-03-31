package springcourse.dao;

import org.hibernate.Session;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class BookDAO {

}
