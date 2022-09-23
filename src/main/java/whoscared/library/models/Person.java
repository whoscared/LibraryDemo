package whoscared.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Calendar;

public class Person {
    private int id;
    @NotEmpty(message = "Full name should not be empty!")
    // Данное регулярное выражение позволяет добавить строку из трех слов,
    // каждое из которых начинается с заглавной буквы и имеет любую длину
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",
            message = "You need to enter the full name according to the example:" +
                    "Senatorova Sofya Stanislavovna")
    private String fullName;
    @Min(value = 1900, message = "Year of birth should not < 1900")
    private int yearOfBirth;

    public Person(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    //Объект, созданный с помощью пустого конструктора, передается в модель для страницы с формой создания нового пользователя
    public Person() {
    }

    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public int getAge() {
        return Calendar.YEAR - this.yearOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}
