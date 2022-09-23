package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public Book oneBook(int idBook){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?", new Object[]{idBook},
                new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public int getIdPerson(int idBook){
        Book book = jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?", new Object[]{idBook},
                new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
        if (book != null){
            return book.getPerson_id();
        }
        return -1;
    }

    public List<Book> allBook(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> personBook(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> freeBook(){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = null",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void userGetBooks(int id, List<Book> books){
        for (Book book : books) {
            jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id_book=?",id, book.getIdBook());
        }
    }

    public void addBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(id_book,name,author,year) VALUES (?,?,?,?)",
                book.getIdBook(), book.getName(), book.getAuthor(), book.getYear());
    }
}
