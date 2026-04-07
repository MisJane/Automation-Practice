package library;

import library.model.Book;
import library.model.BookGenre;
import library.model.Language;
import library.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentBooksTest {

    @Test
    @DisplayName("assignBook: привязывает книгу к студенту и позволяет проверить наличие")
    void assignBookShouldLinkBookToStudent() {
        StudentBooks studentBooks = new StudentBooks();
        Student student = student("S-1");
        Book book = book("ISBN-1");

        studentBooks.assignBook(student, book);

        assertTrue(studentBooks.hasBook(student, "ISBN-1"));
        assertEquals(1, studentBooks.getBooksCount(student));
    }

    @Test
    @DisplayName("assignBook: не дублирует одну и ту же книгу по ISBN у студента")
    void assignBookShouldNotDuplicateSameIsbnForStudent() {
        StudentBooks studentBooks = new StudentBooks();
        Student student = student("S-1");
        Book book = book("ISBN-1");

        studentBooks.assignBook(student, book);
        studentBooks.assignBook(student, book);

        assertEquals(1, studentBooks.getBooksCount(student));
    }

    @Test
    @DisplayName("returnBook: удаляет книгу у студента и возвращает true")
    void returnBookShouldRemoveBookAndReturnTrue() {
        StudentBooks studentBooks = new StudentBooks();
        Student student = student("S-2");
        studentBooks.assignBook(student, book("ISBN-2"));

        boolean removed = studentBooks.returnBook(student, "ISBN-2");

        assertTrue(removed);
        assertFalse(studentBooks.hasBook(student, "ISBN-2"));
    }

    @Test
    @DisplayName("getBooksForStudent: возвращает read-only список книг студента")
    void getBooksForStudentShouldReturnReadOnlyList() {
        StudentBooks studentBooks = new StudentBooks();
        Student student = student("S-3");
        studentBooks.assignBook(student, book("ISBN-3"));

        List<Book> books = studentBooks.getBooksForStudent(student);
        assertEquals(1, books.size());
        assertThrows(UnsupportedOperationException.class, () -> books.add(book("ISBN-4")));
    }

    @Test
    @DisplayName("getBooksForStudentByLanguage: фильтрует книги студента по языку")
    void getBooksForStudentByLanguageShouldFilterBooks() {
        StudentBooks studentBooks = new StudentBooks();
        Student student = student("S-4");
        studentBooks.assignBook(student, book("ISBN-10", Language.EN));
        studentBooks.assignBook(student, book("ISBN-11", Language.RU));

        List<Book> englishBooks = studentBooks.getBooksForStudentByLanguage(student, Language.EN);

        assertEquals(1, englishBooks.size());
        assertEquals("ISBN-10", englishBooks.get(0).getISBN());
    }

    private Student student(String studentId) {
        return new Student("Jane", "Doe", studentId, 2023, "123456");
    }

    private Book book(String isbn) {
        return new Book("Title " + isbn, "Author " + isbn, isbn);
    }

    private Book book(String isbn, Language language) {
        return new Book("Title " + isbn, "Author " + isbn, isbn, BookGenre.UNKNOWN, language);
    }
}
