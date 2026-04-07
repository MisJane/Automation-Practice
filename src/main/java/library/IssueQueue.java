package library;

import library.model.Issue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class IssueQueue {
    private final Queue<Issue> queue = new ArrayDeque<>();

    public void enqueue(Issue issue) {
        if (issue == null) {
            return;
        }
        queue.offer(issue);
    }

    public Issue dequeue() {
        return queue.poll();
    }

    public Issue peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public List<Issue> getQueueSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(queue));
    }

    public void printQueue() {
        for (Issue issue : queue) {
            System.out.println("Issue #" + issue.getIssueId());
        }
    }
}
