package library.model;

public class Student {
    private String firstName;
    private String lastName;
    private String studentId;
    private int enrollmentYear;
    private String phoneNumber;

    public Student(String firstName, String lastName, String studentId, int enrollmentYear, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.enrollmentYear = enrollmentYear;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
