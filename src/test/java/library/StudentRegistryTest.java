package library;

import library.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentRegistryTest {

    @Test
    @DisplayName("addStudent/findStudentById: добавляет студента и находит по id")
    void shouldAddAndFindStudentById() {
        StudentRegistry registry = new StudentRegistry();
        Student student = student("S-1", "Jane");

        registry.addStudent(student);

        assertTrue(registry.hasStudent("S-1"));
        assertEquals("Jane", registry.findStudentById("S-1").getFirstName());
        assertEquals(1, registry.getStudentsCount());
    }

    @Test
    @DisplayName("addStudent: запись с тем же id перезаписывает предыдущую")
    void addStudentShouldReplaceStudentWithSameId() {
        StudentRegistry registry = new StudentRegistry();

        registry.addStudent(student("S-1", "Old"));
        registry.addStudent(student("S-1", "New"));

        assertEquals(1, registry.getStudentsCount());
        assertEquals("New", registry.findStudentById("S-1").getFirstName());
    }

    @Test
    @DisplayName("removeStudent: удаляет существующего студента и возвращает true")
    void removeStudentShouldDeleteAndReturnTrue() {
        StudentRegistry registry = new StudentRegistry();
        registry.addStudent(student("S-2", "Ann"));

        boolean removed = registry.removeStudent("S-2");

        assertTrue(removed);
        assertFalse(registry.hasStudent("S-2"));
        assertNull(registry.findStudentById("S-2"));
    }

    @Test
    @DisplayName("getAllStudentsSnapshot: возвращает read-only снимок")
    void getAllStudentsSnapshotShouldBeReadOnly() {
        StudentRegistry registry = new StudentRegistry();
        registry.addStudent(student("S-3", "Mike"));

        List<Student> snapshot = registry.getAllStudentsSnapshot();

        assertEquals(1, snapshot.size());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(student("S-4", "Kate")));
    }

    @Test
    @DisplayName("addStudent: игнорирует null и невалидные id")
    void addStudentShouldIgnoreInvalidInput() {
        StudentRegistry registry = new StudentRegistry();

        registry.addStudent(null);
        registry.addStudent(student(null, "A"));
        registry.addStudent(student("", "B"));
        registry.addStudent(student("   ", "C"));

        assertEquals(0, registry.getStudentsCount());
    }

    private Student student(String id, String firstName) {
        return new Student(firstName, "Doe", id, 2024, "123");
    }
}
