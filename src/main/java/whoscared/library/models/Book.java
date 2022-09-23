package whoscared.library.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Book {
    private int idBook;
    @NotEmpty(message = "Name of book should not be empty!")
    @Size(min = 1, max = 30)
    private String name;
    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 1, max = 30)
    
    @Pattern(regexp = "[A-Z]\\w+ [A-Z].[A-Z].")
    private String author;
    @Min(value = 1000, message = "Year should not be < 1000")
    private int year;
    public Book(int idBook, String name, String author, int year) {
        this.idBook = idBook;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(){}
    public int getIdBook() {
        return idBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
