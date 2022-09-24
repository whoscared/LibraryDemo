package whoscared.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import whoscared.library.models.Person;

import java.util.List;

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
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person onePerson(int id) {
        // В SQL-запросе вместо данных, которые будем передавать ставим знак вопроса
        // Данные передаем с помощью массива объектов (аналогично, если объект один)
        // query возвращает List<Optional>, поэтому находим нужное значение с помщью stream()
        return jdbcTemplate.query("SELECT * FROM Person WHERE id_person = ?", new Object[]{id},
                        new PersonMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    // В методах update мы ничего не возвращаем из БД, поэтому нам не нужен BeanPropertyRowMapper
    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id_person = ?", id);
    }

    // id получаем из url-адреса страницы, данные для объекта класса Person заполняем из формы
    public void edit(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET fullname=?, year=? WHERE id = ?",
                person.getFullName(), person.getYear(), id);
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fullname, year) VALUES (?,?)",
                person.getFullName(), person.getYear());
    }
}
