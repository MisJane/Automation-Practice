package library;

import library.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRegistry {
    private final Map<String, Student> studentsById = new HashMap<>();

    public void addStudent(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isBlank()) {
            return;
        }
        studentsById.put(student.getStudentId(), student);
    }

    public Student findStudentById(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return null;
        }
        return studentsById.get(studentId);
    }

    public boolean removeStudent(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return false;
        }
        return studentsById.remove(studentId) != null;
    }

    public int getStudentsCount() {
        return studentsById.size();
    }

    public boolean hasStudent(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return false;
        }
        return studentsById.containsKey(studentId);
    }

    public List<Student> getAllStudentsSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(studentsById.values()));
    }
}
