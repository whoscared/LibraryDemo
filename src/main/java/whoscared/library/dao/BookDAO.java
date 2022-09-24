package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import whoscared.library.models.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book oneBook(int idBook) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?", new Object[]{idBook},
                        new BookMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public int getIdPerson(int idBook) {
        Book book = jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?", new Object[]{idBook},
                        new BookMapper())
                .stream()
                .findAny()
                .orElse(null);
        if (book != null) {
            return book.getIdPerson();
        }
        return -1;
    }

    public List<Book> allBook() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public List<Book> personBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = ?", new Object[]{id},
                new BookMapper());
    }

    public List<Book> freeBook() {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = null",
                new BookMapper());
    }

    public void userGetBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id_book=?",
                    id, book.getId());
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }


}
