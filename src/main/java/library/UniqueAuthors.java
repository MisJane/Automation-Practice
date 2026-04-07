package library;

import library.model.Book;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class UniqueAuthors {
    private final Set<String> authors = new HashSet<>();

    public void addAuthor(String author) {
        if (author == null || author.isBlank()) {
            return;
        }
        authors.add(author);
    }

    public void addBook(Book book) {
        if (book == null) {
            return;
        }
        addAuthor(book.getAuthor());
    }

    public boolean hasAuthor(String author) {
        if (author == null || author.isBlank()) {
            return false;
        }
        return authors.contains(author);
    }

    public int getAuthorsCount() {
        return authors.size();
    }

    public Set<String> getAllAuthorsSnapshot() {
        return Collections.unmodifiableSet(new TreeSet<>(authors));
    }

    public void printAllAuthors() {
        for (String author : new TreeSet<>(authors)) {
            System.out.println(author);
        }
    }
}
