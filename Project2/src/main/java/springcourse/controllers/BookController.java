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
import springcourse.services.BooksService;
import springcourse.services.PeopleService;
import springcourse.unit.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    private final BooksService booksService;

    private final PeopleService peopleService;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO, BooksService booksService, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String listOfBook(Model model,@RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "books_per_page",required = false) Integer booksPerPage,
                             @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){
        if(booksPerPage == null || page == null)
            model.addAttribute("books",booksService.findAll(sortByYear));
        else
            model.addAttribute("books",booksService.findWithPagination(page,booksPerPage,sortByYear));
        return "book/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "book/new";

        booksService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOneBook(bookId);
        model.addAttribute("book", book);
        person = peopleService.findOwner(book);
        model.addAttribute("owner", person);
        if(person == null){
            model.addAttribute("people", peopleService.index());
        }
        return "book/show";
    }

    @PatchMapping("/{bookId}/add")
    public String makeOwner(@ModelAttribute("person") Person person, @PathVariable("bookId") int bookId){
        booksService.makeOwner(bookId,person);
        return "redirect:/books/{bookId}";
    }

    @PatchMapping("/{bookId}/release")
    public String release(@ModelAttribute("book") @Valid Book book,
                          @PathVariable("bookId") int bookId){
        booksService.release(bookId);
        return "redirect:/books/{bookId}";
    }


    @GetMapping("/{bookId}/edit")
    public String editBook(Model model, @PathVariable("bookId") int bookId) {
        model.addAttribute("book", booksService.findOneBook(bookId));
        return "book/edit";
    }

    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("bookId") int bookId,
                             BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "book/{bookId}/edit";
        booksService.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") int bookId){
        booksService.delete(bookId);
        return "redirect:/books";
    }


    @GetMapping("/search")
    public String search(){
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", booksService.searchByTitle(query));
        return "book/search";
    }
}
