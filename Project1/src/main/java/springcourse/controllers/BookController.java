package springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.BookDAO;
import springcourse.dao.PersonDAO;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.unit.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String listOfBook(Model model){
        model.addAttribute("books",bookDAO.index());
        return "book/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "book/new";

        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(bookId));
        person = bookDAO.showOwner(bookId);
        model.addAttribute("owner", person);
        if(person == null){
            model.addAttribute("people", personDAO.index());
        }
        return "book/show";
    }

    @PatchMapping("/{bookId}/add")
    public String makeOwner(@ModelAttribute("person") Person person, @PathVariable("bookId") int bookId){
        bookDAO.makeOwner(bookDAO.show(bookId),person);
        return "redirect:/books/{bookId}";
    }

    @PatchMapping("/{bookId}/release")
    public String release(@ModelAttribute("book") @Valid Book book,
                          @PathVariable("bookId") int bookId){
        bookDAO.release(bookId);
        return "redirect:/books/{bookId}";
    }


    @GetMapping("/{bookId}/edit")
    public String editBook(Model model, @PathVariable("bookId") int bookId) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "book/edit";
    }

    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("bookId") int bookId,
                             BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "book/{bookId}/edit";
        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") int bookId){
        bookDAO.delete(bookId);
        return "redirect:/books";
    }




}
