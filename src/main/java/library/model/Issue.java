package library.model;

import java.time.LocalDate;


public class Issue {
    private final int issueId;
    private final LocalDate issueDate;
    private final Book book;
    private final Student student;

    public Issue(int issueId, LocalDate issueDate, Book book, Student student) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.book = book;
        this.student = student;
    }

    public int getIssueId() {
        return issueId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }


}
