package library;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookInventoryTest {

    @Test
    @Tags({
            @Tag("SMOKE"),
            @Tag("BACK")
    })
    @DisplayName("addBook: увеличивает количество и findBook возвращает итоговое число")
    void addBookShouldIncreaseCountAndFindBookShouldReturnCount() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Clean Code", 2);
        inventory.addBook("Clean Code", 3);

        assertEquals(5, inventory.findBook("Clean Code"));
        assertTrue(inventory.hasBook("Clean Code"));
    }

    @Test
    @Tag("WEB")
    @DisplayName("addBook: игнорирует невалидные значения title и count")
    void addBookShouldIgnoreInvalidInput() {
        BookInventory inventory = new BookInventory();

        inventory.addBook(null, 2);
        inventory.addBook("", 2);
        inventory.addBook("Valid title", 0);
        inventory.addBook("Valid title", -1);

        assertEquals(10, inventory.getTotalBooks());
        assertFalse(inventory.hasBook("Valid title"));
    }

    @Test
    @DisplayName("removeBook: удаляет существующую книгу из инвентаря")
    void removeBookShouldDeleteExistingTitle() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Refactoring", 1);
        inventory.removeBook("Refactoring");

        assertEquals(0, inventory.findBook("Refactoring"));
        assertFalse(inventory.hasBook("Refactoring"));
    }

    @Test
    @DisplayName("updateBookCount: обновляет количество и удаляет запись при нуле")
    void updateBookCountShouldSetNewCountAndRemoveWhenZero() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("DDD", 2);
        inventory.updateBookCount("DDD", 7);
        assertEquals(7, inventory.findBook("DDD"));

        inventory.updateBookCount("DDD", 0);
        assertEquals(0, inventory.findBook("DDD"));
        assertFalse(inventory.hasBook("DDD"));
    }

    @Test
    @DisplayName("updateBookCount: игнорирует невалидный ввод")
    void updateBookCountShouldIgnoreInvalidInput() {
        BookInventory inventory = new BookInventory();
        inventory.addBook("Domain-Driven Design", 2);

        inventory.updateBookCount(null, 5);
        inventory.updateBookCount("", 5);
        inventory.updateBookCount("Domain-Driven Design", -1);

        assertEquals(2, inventory.findBook("Domain-Driven Design"));
    }

    @Test
    @DisplayName("getTotalBooks: возвращает сумму всех экземпляров")
    void getTotalBooksShouldReturnSumOfAllCopies() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Book A", 2);
        inventory.addBook("Book B", 4);

        assertEquals(6, inventory.getTotalBooks());
    }

    @Test
    @DisplayName("findByIsbn: в текущей реализации делегирует вызов в findBook")
    void findByIsbnCurrentlyDelegatesToFindBook() {
        BookInventory inventory = new BookInventory();
        inventory.addBook("9780132350884", 3);

        assertEquals(3, inventory.findByIsbn("9780132350884"));
    }

    @Test
    @DisplayName("getAllBooksSnapshot: возвращает read-only снимок, отделенный от внутреннего состояния")
    void getAllBooksSnapshotShouldBeReadOnlyAndDetachedFromInternalMap() {
        BookInventory inventory = new BookInventory();
        inventory.addBook("The Pragmatic Programmer", 2);

        Map<String, Integer> snapshot = inventory.getAllBooksSnapshot();
        assertEquals(2, snapshot.get("The Pragmatic Programmer"));
        assertThrows(UnsupportedOperationException.class,
                () -> snapshot.put("New Book", 1));

        inventory.updateBookCount("The Pragmatic Programmer", 5);
        assertEquals(2, snapshot.get("The Pragmatic Programmer"));
    }

    @ParameterizedTest(name = "[{index}] findBook: возвращает 0/null для \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void findBookShouldReturnZeroForInvalidTitles(String invalidTitle) {
        BookInventory inventory = new BookInventory();
        inventory.addBook("Clean Code", 2);

        assertEquals(0, inventory.findBook(invalidTitle));
    }

    @ParameterizedTest(name = "[{index}] addBook(A:{0}, B:{1}) -> total {2}")
    @CsvSource({
            "1, 2, 3",
            "5, 0, 5",
            "10, 15, 25"
    })
    void getTotalBooksShouldMatchProvidedCounts(int firstCount, int secondCount, int expectedTotal) {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Book A", firstCount);
        inventory.addBook("Book B", secondCount);

        assertEquals(expectedTotal, inventory.getTotalBooks());
    }

    @ParameterizedTest(name = "[{index}] CSV file: addBook(A:{0}, B:{1}) -> total {2}")
    @CsvFileSource(resources = "/library/book-inventory-total-books.csv", numLinesToSkip = 1)
    void getTotalBooksShouldMatchCountsFromCsvFile(int firstCount, int secondCount, int expectedTotal) {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Book A", firstCount);
        inventory.addBook("Book B", secondCount);

        assertEquals(expectedTotal, inventory.getTotalBooks());
    }
}
