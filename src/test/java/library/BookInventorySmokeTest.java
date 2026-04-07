package library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("SMOKE")
@DisplayName("Smoke: базовые проверки BookInventory")
class BookInventorySmokeTest {

    @Test
    @DisplayName("SMOKE: можно добавить и найти книгу")
    void shouldAddAndFindBook() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Clean Architecture", 1);

        assertTrue(inventory.hasBook("Clean Architecture"));
        assertEquals(1, inventory.findBook("Clean Architecture"));
    }

    @Test
    @DisplayName("SMOKE: можно удалить книгу")
    void shouldRemoveBook() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Refactoring", 2);
        inventory.removeBook("Refactoring");

        assertFalse(inventory.hasBook("Refactoring"));
        assertEquals(0, inventory.findBook("Refactoring"));
    }

    @Test
    @DisplayName("SMOKE: корректно считается общее количество книг")
    void shouldReturnCorrectTotalBooks() {
        BookInventory inventory = new BookInventory();

        inventory.addBook("Book A", 2);
        inventory.addBook("Book B", 3);

        assertEquals(5, inventory.getTotalBooks());
    }
}
