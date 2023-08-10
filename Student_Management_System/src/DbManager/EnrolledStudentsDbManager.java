package DbManager;

import DbConnection.DbConnection;
import Models.Courses;
import Models.Department;
import Models.EnrolledStudents;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnrolledStudentsDbManager {
    private static final String Assign_Course = "insert into enrolled_students(student_id,course_id,obt_marks) values (?,?,?)";
    private static final String Update_Course = "update enrolled_students set student_id=?, course_id=?,obt_marks=? where enrolled_id=?";
    private static final String Get_Course = "select * from enrolled_students";
    private static final String Delete_Enrolled = "delete from enrolled_students where enrolled_id=?;";
    private static final String Get_Enrolled_Id = "select enrolled_id from enrolled_students";
    private static final String Get_Student_Courses_By_ID = "SELECT student_name,c.course_name,e.obt_marks FROM enrolled_students e \n" +
            "INNER JOIN students s ON s.`student_id`=e.`student_id`\n" +
            "INNER JOIN courses c ON c.`course_id`=e.`course_id` \n" +
            "WHERE s.student_id=?;";
    private static final String Get_All_Students_Courses = "SELECT student_name,c.course_name,e.obt_marks FROM enrolled_students e \n" +
            "INNER JOIN students s ON s.`student_id`=e.`student_id`\n" +
            "INNER JOIN courses c ON c.`course_id`=e.`course_id` ;";
    public static void assignCourse(EnrolledStudents enrolledStudents) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Assign_Course);
            ps.setInt(1, enrolledStudents.getStudent().getStudentId());
            ps.setInt(2, enrolledStudents.getCourses().getCourseId());
            ps.setInt(3, enrolledStudents.getObtMarks());
            ps.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Student " + enrolledStudents.getStudent().getStudentId() + " has been assigned to " + enrolledStudents.getCourses().getCourseId() + " Marks:" + enrolledStudents.getObtMarks());
    }

    public static ArrayList < EnrolledStudents > getStudentCourses(Integer id) {
        Connection con = DbConnection.getConnection();
        ArrayList < EnrolledStudents > getList = new ArrayList < > ();

        try {
            //Statement statement=con.createStatement();
            PreparedStatement ps = con.prepareStatement(Get_Student_Courses_By_ID);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                EnrolledStudents obj = new EnrolledStudents();
                obj.setStudent(new Student());
                obj.setCourses(new Courses());
                obj.getStudent().setStudentName(rst.getString("student_name"));
                obj.getCourses().setName(rst.getString("course_name"));
                obj.setObtMarks(rst.getInt("obt_marks"));
                getList.add(obj);
            }
            return getList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void updateMarks(EnrolledStudents enrolledStudents, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Course);
            PreparedStatement ps = con.prepareStatement(Update_Course);
            enrolledStudents.setStudent(new Student());
            enrolledStudents.setCourses(new Courses());
            while (rst.next()) {
                enrolledStudents.setEnrolledId(rst.getInt("enrolled_id"));
                enrolledStudents.getStudent().setStudentId(rst.getInt("student_id"));
                enrolledStudents.getCourses().setCourseId(rst.getInt("course_id"));
                if (id == enrolledStudents.getEnrolledId()) {
                    ps.setInt(1, enrolledStudents.getStudent().getStudentId());
                    ps.setInt(2, enrolledStudents.getCourses().getCourseId());
                    ps.setInt(3, enrolledStudents.getObtMarks());
                    ps.setInt(4, enrolledStudents.getEnrolledId());
                    ps.execute();
                    break;

                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" Marks has been Updated.");
    }

    public static boolean checkEnrolled(EnrolledStudents enrolledStudents, Integer Id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Enrolled_Id);

            while (rst.next()) {
                enrolledStudents.setEnrolledId(rst.getInt("enrolled_id"));
                if (enrolledStudents.getEnrolledId() == Id) {
                    return true;
                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList < EnrolledStudents > getAllStudentCourses() {
        Connection con = DbConnection.getConnection();
        ArrayList < EnrolledStudents > getList = new ArrayList < > ();
        try {
            //Statement statement=con.createStatement();
            PreparedStatement ps = con.prepareStatement(Get_All_Students_Courses);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                EnrolledStudents enrolledStudents = new EnrolledStudents();
                enrolledStudents.setStudent(new Student());
                enrolledStudents.setCourses(new Courses());
                enrolledStudents.getStudent().setStudentName(rst.getString("student_name"));
                enrolledStudents.getCourses().setName(rst.getString("course_name"));
                enrolledStudents.setObtMarks(rst.getInt("obt_marks"));
                getList.add(enrolledStudents);
            }
            return getList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteEnrolled(Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Delete_Enrolled);
            ps.setInt(1, id);
            ps.execute();
            con.close();
            System.out.println("Assigned Course Deleted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}