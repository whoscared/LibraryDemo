package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import whoscared.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean haveAny(){
        return allPerson() != null;
    }
    public List<Person> allPerson() {
        // В jdbc сначала отправляем SQL-запрос затем BeanPropertyRowMapper
        //BeanPropertyRowMapper преобразует объект, который отображает бд, в требуемую сущность
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> onePerson(int id) {
        // В SQL-запросе вместо данных, которые будем передавать ставим знак вопроса
        // Данные передаем с помощью массива объектов (аналогично, если объект один)
        // query возвращает List<Optional>, поэтому находим нужное значение с помщью stream()
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny(); //Возвращает не объект класса, а Optional
    }

    // В методах update мы ничего не возвращаем из БД, поэтому нам не нужен BeanPropertyRowMapper
    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

    // id получаем из url-адреса страницы, данные для объекта класса Person заполняем из формы
    public void edit(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET fullname=?, year=? WHERE id = ?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fullname, year) VALUES (?,?)",
                person.getFullName(), person.getYearOfBirth());
    }
}
