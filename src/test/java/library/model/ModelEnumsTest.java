package library.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.provider.Arguments;

class ModelEnumsTest {

    @Test
    @DisplayName("Book: по старому конструктору жанр по умолчанию UNKNOWN")
    void bookOldConstructorShouldSetUnknownGenreByDefault() {
        Book book = new Book("Refactoring", "Martin Fowler", "9780134757599");

        assertEquals(BookGenre.UNKNOWN, book.getGenre());
        assertEquals(Language.OTHER, book.getLanguage());
    }

    @Test
    @DisplayName("Book: можно задать жанр через новый конструктор")
    void bookNewConstructorShouldAcceptGenre() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", BookGenre.SCIENCE);

        assertEquals(BookGenre.SCIENCE, book.getGenre());
        assertEquals(Language.OTHER, book.getLanguage());
    }

    @Test
    @DisplayName("Book: можно задать язык через новый конструктор")
    void bookNewConstructorShouldAcceptLanguage() {
        Book book = new Book(
                "War and Peace",
                "Leo Tolstoy",
                "9780199232765",
                BookGenre.FICTION,
                Language.RU
        );

        assertEquals(BookGenre.FICTION, book.getGenre());
        assertEquals(Language.RU, book.getLanguage());
    }

    @Test
    @DisplayName("Issue: по старому конструктору статус по умолчанию ISSUED")
    void issueOldConstructorShouldSetIssuedStatusByDefault() {
        Book book = new Book("DDD", "Eric Evans", "9780321125217");
        Student student = new Student("Jane", "Doe", "S-100", 2023, "123");

        Issue issue = new Issue(1, LocalDate.now(), book, student);

        assertEquals(IssueStatus.ISSUED, issue.getStatus());
    }

    @Test
    @DisplayName("Issue: можно задать статус через новый конструктор")
    void issueNewConstructorShouldAcceptStatus() {
        Book book = new Book("DDD", "Eric Evans", "9780321125217");
        Student student = new Student("Jane", "Doe", "S-100", 2023, "123");

        Issue issue = new Issue(2, LocalDate.now(), book, student, IssueStatus.OVERDUE);

        assertEquals(IssueStatus.OVERDUE, issue.getStatus());
    }

    @Test
    @DisplayName("Issue: геттеры возвращают значения, переданные в конструктор")
    void issueGettersShouldReturnConstructorValues() {
        LocalDate issueDate = LocalDate.of(2026, 4, 7);
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884");
        Student student = new Student("Jane", "Doe", "S-101", 2023, "123");
        Issue issue = new Issue(42, issueDate, book, student, IssueStatus.ISSUED);

        assertEquals(42, issue.getIssueId());
        assertEquals(issueDate, issue.getIssueDate());
        assertEquals(book, issue.getBook());
        assertEquals(student, issue.getStudent());
    }

    @Test
    @DisplayName("Issue: setStatus обновляет статус и нормализует null в ISSUED")
    void issueSetStatusShouldUpdateValueAndHandleNull() {
        Book book = new Book("DDD", "Eric Evans", "9780321125217");
        Student student = new Student("Jane", "Doe", "S-100", 2023, "123");
        Issue issue = new Issue(3, LocalDate.now(), book, student);

        issue.setStatus(IssueStatus.RETURNED);
        assertEquals(IssueStatus.RETURNED, issue.getStatus());

        issue.setStatus(null);
        assertEquals(IssueStatus.ISSUED, issue.getStatus());
    }

    @ParameterizedTest(name = "[{index}] language {0} -> book.language {0}")
    @CsvSource({
            "RU",
            "EN",
            "DE",
            "FR",
            "ES",
            "OTHER"
    })
    void bookConstructorShouldKeepProvidedLanguage(Language language) {
        Book book = new Book("Title", "Author", "ISBN-" + language, BookGenre.UNKNOWN, language);

        assertEquals(language, book.getLanguage());
    }

    @ParameterizedTest(name = "[{index}] genre={0}, language={1} -> expected {2}/{3}")
    @MethodSource("bookNormalizationCases")
    @DisplayName("Book: нормализует null-значения genre/language в значения по умолчанию")
    void bookShouldNormalizeNullEnums(
            BookGenre inputGenre,
            Language inputLanguage,
            BookGenre expectedGenre,
            Language expectedLanguage
    ) {
        Book book = new Book("Title", "Author", "ISBN", inputGenre, inputLanguage);

        assertEquals(expectedGenre, book.getGenre());
        assertEquals(expectedLanguage, book.getLanguage());
    }

    @Test
    @DisplayName("Book: геттеры/сеттеры для title/author/isbn/genre/language работают корректно")
    void bookGettersAndSettersShouldWorkCorrectly() {
        Book book = new Book("Initial title", "Initial author", "ISBN-1");

        book.setTitle("Updated title");
        book.setAuthor("Updated author");
        book.setISBN("ISBN-2");
        book.setGenre(BookGenre.HISTORY);
        book.setLanguage(Language.FR);

        assertEquals("Updated title", book.getTitle());
        assertEquals("Updated author", book.getAuthor());
        assertEquals("ISBN-2", book.getISBN());
        assertEquals(BookGenre.HISTORY, book.getGenre());
        assertEquals(Language.FR, book.getLanguage());
    }

    private static Stream<Arguments> bookNormalizationCases() {
        return Stream.of(
                arguments(BookGenre.FICTION, Language.RU, BookGenre.FICTION, Language.RU),
                arguments(BookGenre.SCIENCE, Language.EN, BookGenre.SCIENCE, Language.EN),
                arguments(null, Language.DE, BookGenre.UNKNOWN, Language.DE),
                arguments(BookGenre.HISTORY, null, BookGenre.HISTORY, Language.OTHER),
                arguments(null, null, BookGenre.UNKNOWN, Language.OTHER)
        );
    }
}
