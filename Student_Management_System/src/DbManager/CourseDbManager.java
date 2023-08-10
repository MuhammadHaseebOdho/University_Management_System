package DbManager;

import DbConnection.DbConnection;
import Models.Courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseDbManager {

    private static final String Insert_Course = "insert into courses(course_name,course_code) values(?,?)";
    private static final String Get_Course = "select * from courses";
    private static final String Update_Course = "update courses set course_name=?,course_code=? where course_id=?";
    private static final String Get_Course_ID = "select course_id from courses";
    private static final String Delete_Course = "delete from courses where course_id=?;";
    public static void addCourse(Courses courses) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Insert_Course);
            ps.setString(1, courses.getName());
            ps.setString(2, courses.getCourseCode());
            ps.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Course " + courses.getName() + " Added Successfully");

    }

    public static void deleteCourse(Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Delete_Course);
            ps.setInt(1, id);
            ps.execute();
            con.close();
            System.out.println("Course Deleted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCourse(Courses courses, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Course);
            PreparedStatement ps = con.prepareStatement(Update_Course);
            while (rst.next()) {
                courses.setCourseId(rst.getInt("course_id"));
                courses.setCourseCode(rst.getString("course_code"));
                if (id == courses.getCourseId()) {
                    ps.setString(1, courses.getName());
                    ps.setString(2, courses.getCourseCode());
                    ps.setInt(3, courses.getCourseId());
                    ps.execute();
                    break;
                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Courses " + courses.getName() + " has been Updated");
    }

    public static boolean checkCourses(Courses courses, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Course_ID);

            while (rst.next()) {
                courses.setCourseId(rst.getInt("course_id"));
                if (courses.getCourseId() == id) {
                    return true;
                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}