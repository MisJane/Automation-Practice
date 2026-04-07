package library.model;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private BookGenre genre;
    private Language language;

    public Book(String title, String author, String ISBN) {
        this(title, author, ISBN, BookGenre.UNKNOWN, Language.OTHER);
    }

    public Book(String title, String author, String ISBN, BookGenre genre) {
        this(title, author, ISBN, genre, Language.OTHER);
    }

    public Book(String title, String author, String ISBN, BookGenre genre, Language language) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre == null ? BookGenre.UNKNOWN : genre;
        this.language = language == null ? Language.OTHER : language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre == null ? BookGenre.UNKNOWN : genre;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language == null ? Language.OTHER : language;
    }
}
