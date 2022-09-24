package whoscared.library.models;

public class BookOwner {
    private Person person;
    private Book book;

    public BookOwner(Person person, Book book) {
        this.person = person;
        this.book = book;
    }
    public BookOwner(){}

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
