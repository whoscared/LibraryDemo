package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public List<Book> allBook(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> personBook(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> freeBook(){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = null",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void userGetBooks(int id, List<Book> books){
        for (Book book : books) {
            jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id_book=?",id, book.getIdBook());
        }
    }

}
