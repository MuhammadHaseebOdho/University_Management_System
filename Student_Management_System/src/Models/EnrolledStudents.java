package Models;

public class EnrolledStudents {
    private int enrolledId;
    private Student student;
    private Courses courses;
    private int obtMarks;

    public int getEnrolledId() {
        return enrolledId;
    }

    public void setEnrolledId(int enrolledId) {
        this.enrolledId = enrolledId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public int getObtMarks() {
        return obtMarks;
    }

    public void setObtMarks(int obtMarks) {
        this.obtMarks = obtMarks;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
