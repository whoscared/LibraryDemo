package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import whoscared.library.models.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBook() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book oneBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?", new Object[]{id},
                        new BookMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id_book=?", id);
    }

    //Возвращает id пользователя, обладателя книги, id которой передается в метод
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

    //Возвращает список книг для пользователя, id которого передается в метод
    public List<Book> personBook(int idPerson) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = ?", new Object[]{idPerson},
                new BookMapper());
    }

    //Назначает книге внешний ключ => пользователь берет книгу
    public void userGetBook(int idPerson, Book book) {
        jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id_book=?",
                    idPerson, book.getId());
    }

    //Присваивает для книги значение внешнего ключа = null => пользователь возвращает книгу
    public void releaseBook(int id){
        jdbcTemplate.update("UPDATE Book SET id_person=NULL WHERE id_book=?", id);
    }

}
