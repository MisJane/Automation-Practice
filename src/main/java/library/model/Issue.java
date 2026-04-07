package library.model;

import java.time.LocalDate;


public class Issue {
    private final int issueId;
    private final LocalDate issueDate;
    private final Book book;
    private final Student student;
    private IssueStatus status;

    public Issue(int issueId, LocalDate issueDate, Book book, Student student) {
        this(issueId, issueDate, book, student, IssueStatus.ISSUED);
    }

    public Issue(int issueId, LocalDate issueDate, Book book, Student student, IssueStatus status) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.book = book;
        this.student = student;
        this.status = status == null ? IssueStatus.ISSUED : status;
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

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status == null ? IssueStatus.ISSUED : status;
    }
}
