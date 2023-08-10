package DbManager;

import DbConnection.DbConnection;
import Models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DepartmentDbManager {
    private static final String Insert_Dept = "insert into departments(dpt_name,dpt_code) values(?,?)";
    private static final String Update_Dept = "update departments set dpt_name=?,dpt_code=? where dpt_id=?";
    private static final String Get_Dept_Id = "select dpt_id from departments";
    private static final String Get_Dept = "select * from departments";
    private static final String Delete_Dept = "delete from departments where dpt_id=?";

    public static void addDept(Department department) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Insert_Dept);
            ps.setString(1, department.getName());
            ps.setString(2, department.getDptCode());
            ps.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Department " + department.getName() + " added Successfully.");
    }

    public static void deleteDept(Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(Delete_Dept);
            ps.setInt(1, id);
            ps.execute();
            con.close();
            System.out.println("Department Deleted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateDept(Department department, Integer id) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Dept);
            PreparedStatement ps = con.prepareStatement(Update_Dept);
            while (rst.next()) {
                department.setDptId(rst.getInt("dpt_id"));
                department.setDptCode(rst.getString("dpt_code"));
                if (id == department.getDptId()) {
                    ps.setString(1, department.getName());
                    ps.setString(2, department.getDptCode());
                    ps.setInt(3, department.getDptId());
                    ps.execute();
                    break;
                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Department " + department.getName() + " has been Updated");
    }

    public static boolean checkDept(Department department, Integer dptId) {
        Connection con = DbConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rst = statement.executeQuery(Get_Dept_Id);

            while (rst.next()) {
                department.setDptId(rst.getInt("dpt_id"));
                if (department.getDptId() == dptId) {
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