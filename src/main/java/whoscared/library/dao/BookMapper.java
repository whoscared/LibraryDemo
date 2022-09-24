package whoscared.library.dao;

import org.springframework.jdbc.core.RowMapper;
import whoscared.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id_book"));
        book.setIdPerson(rs.getInt("id_person"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        return book;
    }
}
