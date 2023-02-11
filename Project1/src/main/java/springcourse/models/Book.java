package springcourse.models;

public class Book {

    private int bookId;
    private String bookName;
    private String author;
    private int releaseYear;

    public Book(){}
    public Book(int bookId, String bookName, String author, int releaseYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
