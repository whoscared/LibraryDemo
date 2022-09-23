package whoscared.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import whoscared.library.dao.BookDAO;
import whoscared.library.dao.PersonDAO;
import whoscared.library.models.Book;

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
    public String allBooks(Model model){
        model.addAttribute("books", bookDAO.allBook());
        return ("/book/list");
    }

    @GetMapping("/{id}")
    public String oneBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.oneBook(id));
        //model.addAttribute("person", personDAO.onePerson(bookDAO.getIdPerson(id)));
        return ("/book/id");
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/book/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "book/new";
        }
        bookDAO.addBook(book);
        return "redirect:/book";
    }
}
