package library;

import library.model.Book;
import library.model.Issue;
import library.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssueQueueTest {

    @Test
    @DisplayName("queue: enqueue/dequeue работает в порядке FIFO")
    void shouldProcessIssuesInFifoOrder() {
        IssueQueue queue = new IssueQueue();
        Issue first = issue(1);
        Issue second = issue(2);

        queue.enqueue(first);
        queue.enqueue(second);

        assertEquals(1, queue.dequeue().getIssueId());
        assertEquals(2, queue.dequeue().getIssueId());
    }

    @Test
    @DisplayName("queue: snapshot read-only и отражает текущее состояние")
    void snapshotShouldBeReadOnlyAndContainCurrentItems() {
        IssueQueue queue = new IssueQueue();
        queue.enqueue(issue(10));

        List<Issue> snapshot = queue.getQueueSnapshot();
        assertEquals(1, snapshot.size());
        assertEquals(10, snapshot.get(0).getIssueId());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(issue(99)));
    }

    @Test
    @DisplayName("queue: dequeue и peek возвращают null для пустой очереди")
    void dequeueAndPeekShouldReturnNullForEmptyQueue() {
        IssueQueue queue = new IssueQueue();

        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("queue: printQueue печатает id заявок")
    void printQueueShouldWriteIssueIdsToStdOut() {
        IssueQueue queue = new IssueQueue();
        queue.enqueue(issue(77));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));
            queue.printQueue();
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(out.toString().contains("Issue #77"));
    }

    private Issue issue(int issueId) {
        Book book = new Book("Book " + issueId, "Author", "ISBN-" + issueId);
        Student student = new Student("Ann", "Smith", "S-" + issueId, 2024, "123");
        return new Issue(issueId, LocalDate.now(), book, student);
    }
}
