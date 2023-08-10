package DbManager;

import DbConnection.DbConnection;
import Models.Department;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDbManager {
    private static final String Insert_Student_Query = "insert into students(student_name,student_rollnum,student_age,dept_id) values(?,?,?,?)";
    private static final String Update_Student_Query = "update students set student_name=?,student_rollnum=?,student_age=?,dept_id=? where student_id=?";
    private static final String Delete_Student_Query = "delete from students where student_id=?";
    private static final String Get_Students_Query = "Select * from students";
    private static final String Get_Student_Id = "select student_id from students";

    public static void addStudent(Student student) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Insert_Student_Query);
            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getRollNumber());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getDepartment().getDptId());
            ps.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Student " + student.getStudentName() + " has been added");

    }

    public static ArrayList < Student > getAllStudents() {
        Connection con = DbConnection.getConnection();
        ArrayList < Student > students = new ArrayList < > ();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery("SELECT s.student_id,s.student_name,s.student_rollnum,s.student_age,d.dpt_id,d.dpt_name,d.`dpt_code`\n" +
                    "FROM students s\n" +
                    "INNER JOIN departments d\n" +
                    "ON s.`dept_id`=d.`dpt_id`;");
            while (rst.next()) {
                Student student = new Student();
                student.setStudentId(rst.getInt("student_id"));
                student.setStudentName(rst.getString("student_name"));
                student.setRollNumber(rst.getString("student_rollnum"));
                student.setAge(rst.getInt("student_age"));
                Department department = new Department();
                department.setDptId(rst.getInt("dpt_id"));
                department.setName(rst.getString("dpt_name"));
                department.setDptCode(rst.getString("dpt_code"));
                student.setDepartment(department);
                students.add(student);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void deleteStudent(Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Delete_Student_Query);
            ps.setInt(1, id);
            ps.execute();
            con.close();
            System.out.println("Student Deleted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateStudent(Student student, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Students_Query);
            PreparedStatement ps = con.prepareStatement(Update_Student_Query);
            while (rst.next()) {
                student.setStudentId(rst.getInt("student_id"));
                //student.setStudentName(rst.getString("student_name"));
                student.setRollNumber(rst.getString("student_rollnum"));
                student.setAge(rst.getInt("student_age"));
                Department department = new Department();
                department.setDptId(rst.getInt("dept_id"));
                student.setDepartment(department);
                if (id == student.getStudentId()) {
                    ps.setString(1, student.getStudentName());
                    ps.setString(2, student.getRollNumber());
                    ps.setInt(3, student.getAge());
                    ps.setInt(4, student.getDepartment().getDptId());
                    ps.setInt(5, student.getStudentId());
                    ps.execute();
                    break;
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Student " + student.getStudentName() + " has been Updated");
    }

    public static Student getStudentById(Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Students_Query);
            Student student = new Student();
            while (rst.next()) {

                student.setStudentId(rst.getInt("student_id"));
                student.setStudentName(rst.getString("student_name"));
                student.setRollNumber(rst.getString("student_rollnum"));
                student.setAge(rst.getInt("student_age"));
                Department department = new Department();
                department.setDptId(rst.getInt("dept_id"));
                student.setDepartment(department);
                if (id == student.getStudentId()) {
                    return student;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkStudentId(Student student, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Student_Id);
            while (rst.next()) {
                student.setStudentId(rst.getInt("student_id"));
                if (student.getStudentId() == id) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}