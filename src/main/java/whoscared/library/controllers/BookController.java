package whoscared.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import whoscared.library.dao.BookDAO;
import whoscared.library.dao.PersonDAO;
import whoscared.library.models.Book;
import whoscared.library.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String allBooks(Model model) {
        model.addAttribute("books", bookDAO.allBook());
        return ("/book/list");
    }

    @GetMapping("/{id}")
    public String oneBook(@PathVariable("id") int id,
                          Model model,
                          // Передаем пустой объект для выпадающего списка
                          @ModelAttribute("person") Person person) {
        // Передаем книгу, страницу которой мы открываем
        model.addAttribute("book", bookDAO.oneBook(id));
        Person bookOwner = personDAO.onePerson(bookDAO.getIdPerson(id));
        if (bookOwner != null) {
            // Передаем обладателя книги, если такой имеется
            model.addAttribute("owner", bookOwner);
        } else {
            // Передаем список всех людей, чтобы назначить владельца книги (если такового нет )
            model.addAttribute("people", personDAO.allPerson());
        }
        return "/book/id";
    }

    @PatchMapping("/{id}/addowner")
    public String addOwner(@PathVariable("id") int idBook,
                           @ModelAttribute("person") Person newOwner){
        Book book = bookDAO.oneBook(idBook);
        bookDAO.userGetBook(newOwner.getId(), book);
        return "redirect:/book/" + idBook;
    }

   /* @PatchMapping("/{id}")
    // Назначает пустому человеку, которого мы создаем, id, который мы передали через форму
    public String addUser(@PathVariable("id") int idBook,
                          @ModelAttribute("person") Person person) {
        Book book = bookDAO.oneBook(idBook);
        System.out.println(person.getId());
        System.out.println(idBook);

        //book.setIdPerson(person.getId());
        bookDAO.userGetBook(person.getId(), book);
        return "redirect:/book";
    }

    @PostMapping("/addUser/{bookId}")
    public String addNew(@PathVariable("bookId") int idBook,
                         @ModelAttribute("person") Person person) {
        Book book = bookDAO.oneBook(idBook);
        bookDAO.userGetBook(person.getId(), book);
        return "redirect:/book";
    }

    */

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "/book/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookDAO.addBook(book);
        return "redirect:/book";
    }
    //TODO: страницы : удалить книгу, освободить книгу
}
