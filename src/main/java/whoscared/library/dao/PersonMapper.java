package whoscared.library.dao;

import org.springframework.jdbc.core.RowMapper;
import whoscared.library.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id_person"));
        person.setFullName(rs.getString("fullname"));
        person.setYear(rs.getInt("year"));

        return person;
    }

}
