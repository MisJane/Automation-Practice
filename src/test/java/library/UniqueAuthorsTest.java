package library;

import library.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueAuthorsTest {

    @Test
    @DisplayName("addAuthor: сохраняет только уникальные имена авторов")
    void addAuthorShouldKeepOnlyUniqueValues() {
        UniqueAuthors uniqueAuthors = new UniqueAuthors();

        uniqueAuthors.addAuthor("Martin Fowler");
        uniqueAuthors.addAuthor("Martin Fowler");
        uniqueAuthors.addAuthor("Robert C. Martin");

        assertEquals(2, uniqueAuthors.getAuthorsCount());
    }

    @Test
    @DisplayName("addBook: добавляет автора из книги")
    void addBookShouldAddBookAuthor() {
        UniqueAuthors uniqueAuthors = new UniqueAuthors();
        Book book = new Book("Refactoring", "Martin Fowler", "9780134757599");

        uniqueAuthors.addBook(book);

        assertTrue(uniqueAuthors.hasAuthor("Martin Fowler"));
        assertEquals(1, uniqueAuthors.getAuthorsCount());
    }

    @Test
    @DisplayName("addAuthor: игнорирует null и пустые строки")
    void addAuthorShouldIgnoreInvalidValues() {
        UniqueAuthors uniqueAuthors = new UniqueAuthors();

        uniqueAuthors.addAuthor(null);
        uniqueAuthors.addAuthor("");
        uniqueAuthors.addAuthor("   ");

        assertEquals(0, uniqueAuthors.getAuthorsCount());
    }

    @Test
    @DisplayName("getAllAuthorsSnapshot: возвращает read-only снимок, отделенный от состояния")
    void getAllAuthorsSnapshotShouldBeReadOnlyAndDetached() {
        UniqueAuthors uniqueAuthors = new UniqueAuthors();
        uniqueAuthors.addAuthor("Kent Beck");

        Set<String> snapshot = uniqueAuthors.getAllAuthorsSnapshot();
        assertTrue(snapshot.contains("Kent Beck"));
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("New Author"));

        uniqueAuthors.addAuthor("Martin Fowler");
        assertFalse(snapshot.contains("Martin Fowler"));
    }

    @Test
    @DisplayName("printAllAuthors: печатает всех авторов")
    void printAllAuthorsShouldWriteAuthorsToStdOut() {
        UniqueAuthors uniqueAuthors = new UniqueAuthors();
        uniqueAuthors.addAuthor("Martin Fowler");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));
            uniqueAuthors.printAllAuthors();
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(out.toString().contains("Martin Fowler"));
    }
}
