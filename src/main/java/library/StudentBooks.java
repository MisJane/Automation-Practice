package library;

import library.model.Book;
import library.model.Language;
import library.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentBooks {
    private final Map<String, Set<String>> booksByStudentId = new HashMap<>();
    private final Map<String, Book> booksByIsbn = new HashMap<>();

    public void assignBook(Student student, Book book) {
        if (student == null || book == null) {
            return;
        }

        String studentId = student.getStudentId();
        String isbn = book.getISBN();
        if (studentId == null || studentId.isBlank() || isbn == null || isbn.isBlank()) {
            return;
        }

        booksByIsbn.putIfAbsent(isbn, book);
        booksByStudentId.computeIfAbsent(studentId, key -> new LinkedHashSet<>()).add(isbn);
    }

    public boolean returnBook(Student student, String isbn) {
        if (student == null || isbn == null || isbn.isBlank()) {
            return false;
        }
        String studentId = student.getStudentId();
        if (studentId == null || studentId.isBlank()) {
            return false;
        }

        Set<String> studentIsbns = booksByStudentId.get(studentId);
        if (studentIsbns == null) {
            return false;
        }

        boolean removed = studentIsbns.remove(isbn);
        if (studentIsbns.isEmpty()) {
            booksByStudentId.remove(studentId);
        }
        return removed;
    }

    public int getBooksCount(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isBlank()) {
            return 0;
        }
        Set<String> studentIsbns = booksByStudentId.get(student.getStudentId());
        return studentIsbns == null ? 0 : studentIsbns.size();
    }

    public List<Book> getBooksForStudent(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isBlank()) {
            return Collections.emptyList();
        }

        Set<String> studentIsbns = booksByStudentId.get(student.getStudentId());
        if (studentIsbns == null || studentIsbns.isEmpty()) {
            return Collections.emptyList();
        }

        List<Book> result = new ArrayList<>();
        for (String isbn : studentIsbns) {
            Book book = booksByIsbn.get(isbn);
            if (book != null) {
                result.add(book);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public boolean hasBook(Student student, String isbn) {
        if (student == null || isbn == null || isbn.isBlank() || student.getStudentId() == null || student.getStudentId().isBlank()) {
            return false;
        }
        Set<String> studentIsbns = booksByStudentId.get(student.getStudentId());
        return studentIsbns != null && studentIsbns.contains(isbn);
    }

    public List<Book> getBooksForStudentByLanguage(Student student, Language language) {
        if (language == null) {
            return Collections.emptyList();
        }

        List<Book> books = getBooksForStudent(student);
        if (books.isEmpty()) {
            return Collections.emptyList();
        }

        List<Book> filtered = new ArrayList<>();
        for (Book book : books) {
            if (language.equals(book.getLanguage())) {
                filtered.add(book);
            }
        }
        return Collections.unmodifiableList(filtered);
    }
}
