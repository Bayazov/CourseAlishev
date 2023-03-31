package springcourse.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.repositories.PeopleRepository;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> index(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void savePerson(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void deletePerson(int id){
        peopleRepository.deleteById(id);
    }

    public Person findOwner(Book book){
        return peopleRepository.findPersonByBooks(book);
    }

    public List<Book> findBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());

            person.get().getBooks().forEach(book -> {
                long diffMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());

                if(diffMillies > 864000000)
                    book.setExpired(true);
            });

            return person.get().getBooks();
        }else
            return Collections.emptyList();
    }


    public Optional<Person> findByName(String name){
        Optional<Person> person = Optional.ofNullable(peopleRepository.findByName(name));
        return person;
    }
 }
