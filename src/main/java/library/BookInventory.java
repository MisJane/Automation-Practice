package library;

import java.util.HashMap;
import java.util.Map;

public class BookInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addBook(String title, int count) {
        if (title == null || title.isBlank() || count <= 0) {
            return;
        }
        inventory.merge(title, count, Integer::sum);
    }

    public void removeBook(String title) {
        if (title == null || title.isBlank()) {
            return;
        }
        inventory.remove(title);
    }

    public int findByIsbn(String isbn) {
        return findBook(isbn);
    }

    public int findBook(String title) {
        if (title == null || title.isBlank()) {
            return 0;
        }
        return inventory.getOrDefault(title, 0);
    }

    /**
     * Проверка наличия книги в инвентаре.
     *
     * @param title название книги
     * @return true, если книга есть
     */
    public boolean hasBook(String title) {
        if (title == null || title.isBlank()) {
            return false;
        }
        return inventory.containsKey(title);
    }

    /**
     * Установить точное количество экземпляров книги.
     * Если количество 0, книга удаляется.
     *
     * @param title название книги
     * @param newCount новое количество
     */
    public void updateBookCount(String title, int newCount) {
        if (title == null || title.isBlank() || newCount < 0) {
            return;
        }
        if (newCount == 0) {
            inventory.remove(title);
            return;
        }
        inventory.put(title, newCount);
    }

    /**
     * Общее количество экземпляров всех книг.
     *
     * @return суммарное количество
     */
    public int getTotalBooks() {
        int total = 0;
        for (int count : inventory.values()) {
            total += count;
        }
        return total;
    }

    /**
     * Снимок текущего инвентаря только для чтения.
     *
     * @return неизменяемая копия инвентаря
     */
    public Map<String, Integer> getAllBooksSnapshot() {
        return Map.copyOf(inventory);
    }

    @SuppressWarnings("unused")
    public void printAllBooks() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
