package Main;

import DbManager.CourseDbManager;
import DbManager.DepartmentDbManager;
import DbManager.EnrolledStudentsDbManager;
import DbManager.StudentDbManager;
import Models.Department;
import Models.EnrolledStudents;
import Models.Student;
import Models.Courses;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Department department = new Department();
    static Student student = new Student();
    static Courses courses = new Courses();
    static EnrolledStudents enrolledStudents = new EnrolledStudents();
    static Scanner sc = new Scanner(System.in);

    public static void options() {

        System.out.println("=======WELCOME TO UNIVERSITY_MANAGEMENT_SYSTEM=======");
        boolean chk = true;
        do {

            System.out.print("1. DEPARTMENT MANAGEMENT \n" +
                    "2. STUDENT MANAGEMENT\n" +
                    "3. COURSE MANAGEMENT\n" +
                    "4. STUDENT COURSE MANAGEMENT\n" +
                    "5. Exit\n" +
                    "CHOICE: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    departmentManagement();
                    break;
                case 2:
                    studentManagement();
                    break;
                case 3:
                    courseManagement();
                    break;
                case 4:
                    enrolledStudentsManagement();
                    break;

                case 5:
                    System.out.println("Thank You.");
                    chk = false;
                    break;

                default:
                    System.out.println("Invalid Choice!!!");
            }
        } while (chk);
    }

    public static void departmentManagement() {
        boolean back_option_dpt = true;
        System.out.println("===========Department Management==========");
        do {
            System.out.print("1. ADD DEPARTMENT\n" +
                    "2.UPDATE DEPARTMENT\n" +
                    "3. DELETE DEPARTMENT\n" +
                    "4. Back\n" +
                    "CHOICE:");
            int dptChoice = sc.nextInt();
            switch (dptChoice) {
                case 1:
                    //Add Department
                    //Department department=new Department();
                    sc.nextLine();
                    System.out.println("Enter Department Name");
                    String dptName = sc.nextLine();
                    department.setName(dptName);
                    System.out.println("Enter Department Code");
                    String dptCode = sc.nextLine();
                    department.setDptCode(dptCode);
                    DepartmentDbManager.addDept(department);

                    break;
                case 2:
                    //Update Department
                    //Department departmentUpdate=new Department();
                    System.out.println("Enter Department Id you want to Update.");
                    int updateDpt = sc.nextInt();
                    if (DepartmentDbManager.checkDept(department, updateDpt)) {
                        sc.nextLine();
                        System.out.println("Enter new Department Name");
                        String newDptName = sc.nextLine();
                        department.setName(newDptName);
                        DepartmentDbManager.updateDept(department, updateDpt);
                    } else
                        System.out.println("Department does not Exist");

                    break;
                case 3:
                    //Delete Dpt
                    //Department deleteDpt=new Department();
                    System.out.print("Enter Department ID to delete:");
                    int delDptId = sc.nextInt();
                    if (DepartmentDbManager.checkDept(department, delDptId)) {
                        DepartmentDbManager.deleteDept(delDptId);
                    } else System.out.println("Department Does not Exist.");
                    break;
                case 4:
                    back_option_dpt = false;
                    break;

                default:
                    System.out.println("Invalid Choice!!!");
            }
        } while (back_option_dpt);

    }

    public static void studentManagement() {
        System.out.println("=======STUDENT MANAGEMENT=======");
        boolean back_option_stu = true;
        do {
            System.out.print("1. ADD STUDENT\n" +
                    "2. UPDATE STUDENT\n" +
                    "3. DELETE STUDENT\n" +
                    "4. GET STUDENT BY ID\n" +
                    "5. GET ALL STUDENTS\n" +
                    "6. BACK\n" +
                    "CHOICE: ");
            int studentChoice = sc.nextInt();
            switch (studentChoice) {
                case 1:
                    //Add Student
                    //Student student=new Student();
                    sc.nextLine();
                    System.out.println("Enter Student Name");
                    String stName = sc.nextLine();
                    student.setStudentName(stName);
                    System.out.println("Enter Student Roll Number");
                    String stRollnum = sc.nextLine();
                    student.setRollNumber(stRollnum);
                    System.out.println("Enter Student Age");
                    int stAge = sc.nextInt();
                    student.setAge(stAge);
                    Department department = new Department();
                    System.out.println("Enter Dept ID");
                    int stDptID = sc.nextInt();
                    department.setDptId(stDptID);
                    student.setDepartment(department);
                    if (DepartmentDbManager.checkDept(student.getDepartment(), stDptID)) {
                        StudentDbManager.addStudent(student);
                    } else
                        System.out.println("Department does not Exist.");
                    break;
                case 2:
                    //Update Student
                    //Student updateStudent=new Student();
                    System.out.println("Enter Student id To update");
                    int updateStuId = sc.nextInt();
                    if (StudentDbManager.checkStudentId(student, updateStuId)) {
                        sc.nextLine();
                        System.out.println("Enter New Name");
                        String updateName = sc.nextLine();
                        student.setStudentName(updateName);
                        StudentDbManager.updateStudent(student, updateStuId);
                    } else System.out.println("Student Does not Exist");

                    break;
                case 3:
                    // Delete Student
                    // Student deleteStudent=new Student();
                    System.out.println("Enter Student Id to delete");
                    int delStu = sc.nextInt();
                    if (StudentDbManager.checkStudentId(student, delStu)) {
                        StudentDbManager.deleteStudent(delStu);
                    } else System.out.println("Student Id does not exist");
                    break;
                case 4:
                    //get Student by id
                    // Student studentGet=new Student();
                    System.out.println("Enter Student id:");
                    int stId = sc.nextInt();
                    if (StudentDbManager.checkStudentId(student, stId)) {
                        Student studentId = StudentDbManager.getStudentById(stId);
                        System.out.println("Id: " + studentId.getStudentId());
                        System.out.println("Name: " + studentId.getStudentName());
                        System.out.println("Roll Number: " + studentId.getRollNumber());
                        System.out.println("Age: " + studentId.getAge());
                        System.out.println("Department: " + studentId.getDepartment().getDptId());
                    } else
                        System.out.println("Student Does not Exist");
                    break;
                case 5:
                    //get all students
                    List < Student > getList = StudentDbManager.getAllStudents();
                    for (Student s: getList) {
                        System.out.println("**************************");
                        System.out.println("Id : " + s.getStudentId());
                        System.out.println("Name : " + s.getStudentName());
                        System.out.println("Email : " + s.getRollNumber());
                        System.out.println("Dept-ID : " + s.getDepartment().getDptId());
                        System.out.println("Dept-Name : " + s.getDepartment().getName());
                    }
                    System.out.println("**************************");
                    break;
                case 6:
                    back_option_stu = false;
                    break;
                default:
                    System.out.println("Invalid Choice!!!");
            }

        } while (back_option_stu);
    }
    public static void courseManagement() {
        System.out.println("Course Management");
        boolean back_option_course = true;
        do {
            System.out.print("1. Add Course\n" +
                    "2. Update Course\n" +
                    "3. Delete Course\n" +
                    "4. Back\n" +
                    "Choice: ");
            int courseChoice = sc.nextInt();
            switch (courseChoice) {
                case 1:
                    //Add Course
                    //Courses courses=new Courses();
                    sc.nextLine();
                    System.out.println("Enter Course Name");
                    String courseName = sc.nextLine();
                    courses.setName(courseName);
                    System.out.println("Enter Course Code");
                    String courseCode = sc.nextLine();
                    courses.setCourseCode(courseCode);
                    CourseDbManager.addCourse(courses);
                    break;
                case 2:
                    //Update Course
                    // Courses coursesUpdate=new Courses();
                    System.out.println("Enter Course Id you want to Update.");
                    int courseUpdate = sc.nextInt();
                    if (CourseDbManager.checkCourses(courses, courseUpdate)) {
                        sc.nextLine();
                        System.out.println("Enter New Course Name:");
                        String newCourseName = sc.nextLine();
                        courses.setName(newCourseName);
                        CourseDbManager.updateCourse(courses, courseUpdate);
                    } else System.out.println("Course does not Exist");
                    break;
                case 3:
                    //Delete course
                    // Courses deleteCourse=new Courses();
                    System.out.println("Enter Course ID to Delete");
                    int dltCourseId = sc.nextInt();
                    if (CourseDbManager.checkCourses(courses, dltCourseId)) {
                        CourseDbManager.deleteCourse(dltCourseId);
                    } else System.out.println("Course Does not exist.");
                    break;
                case 4:
                    back_option_course = false;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (back_option_course);
    }

    public static void enrolledStudentsManagement() {
        System.out.println("Student Course Management");
        boolean back_option_enrolled = true;
        do {
            System.out.print("1. Assign Course \n" +
                    "2. Update Marks\n" +
                    "3. Get Student Courses\n" +
                    "4. Get All Student Courses\n" +
                    "5. Delete Assigned Course\n" +
                    "6. Back\n" +
                    "Choice:");
            int enrollChoice = sc.nextInt();
            switch (enrollChoice) {
                case 1:
                    //Add Enrolled
                    // EnrolledStudents enrolledStudents=new EnrolledStudents();
                    enrolledStudents.setStudent(new Student());
                    enrolledStudents.setCourses(new Courses());
                    System.out.print("Enter Student Id:");
                    int stuID = sc.nextInt();
                    if (StudentDbManager.checkStudentId(enrolledStudents.getStudent(), stuID)) {
                        System.out.print("Enter Course ID:");
                        int courseID = sc.nextInt();
                        if (CourseDbManager.checkCourses(enrolledStudents.getCourses(), courseID)) {
                            System.out.print("Enter Marks:");
                            int marks = sc.nextInt();
                            enrolledStudents.getStudent().setStudentId(stuID);
                            enrolledStudents.getCourses().setCourseId(courseID);
                            enrolledStudents.setObtMarks(marks);
                            EnrolledStudentsDbManager.assignCourse(enrolledStudents);

                        } else System.out.println("Course Does not Exist");
                    } else System.out.println("Student does not Exist");
                    break;
                case 2:
                    //Update Enrolled
                    //EnrolledStudents enrollUpdate=new EnrolledStudents();
                    System.out.print("Enter Id you want to update:");
                    int enrollID = sc.nextInt();
                    if (EnrolledStudentsDbManager.checkEnrolled(enrolledStudents, enrollID)) {
                        System.out.print("Enter New Marks:");
                        int newMarks = sc.nextInt();
                        enrolledStudents.setObtMarks(newMarks);
                        EnrolledStudentsDbManager.updateMarks(enrolledStudents, enrollID);
                    } else System.out.println("Enroll Id Does not Exist.");
                    break;
                case 3:
                    //Get Student Courses
                    //EnrolledStudents getEnrolled=new EnrolledStudents();
                    enrolledStudents.setStudent(new Student());
                    enrolledStudents.setCourses(new Courses());
                    System.out.print("Enter Student id:");
                    int getID = sc.nextInt();
                    if (StudentDbManager.checkStudentId(enrolledStudents.getStudent(), getID)) {
                        List < EnrolledStudents > get = EnrolledStudentsDbManager.getStudentCourses(getID);
                        for (EnrolledStudents es: get) {
                            System.out.println("Name:" + es.getStudent().getStudentName());
                            System.out.println("Course Name:" + es.getCourses().getName());
                            System.out.println("Marks:" + es.getObtMarks());
                        }
                    } else System.out.println("Student Does Not Exist");
                    break;
                case 4:
                    //Get All Students Courses
                    //EnrolledStudents getAllEnrolled=new EnrolledStudents();
                    enrolledStudents.setStudent(new Student());
                    enrolledStudents.setCourses(new Courses());
                    List < EnrolledStudents > get = EnrolledStudentsDbManager.getAllStudentCourses();
                    for (EnrolledStudents es: get) {
                        System.out.println("Name:" + es.getStudent().getStudentName());
                        System.out.println("Course Name:" + es.getCourses().getName());
                        System.out.println("Marks:" + es.getObtMarks());
                    }
                    break;
                case 5:
                    EnrolledStudents delEnroll = new EnrolledStudents();
                    System.out.println("Enter Enroll id to delete");
                    int delEnrollID = sc.nextInt();
                    if (EnrolledStudentsDbManager.checkEnrolled(delEnroll, delEnrollID)) {
                        EnrolledStudentsDbManager.deleteEnrolled(delEnrollID);
                    } else System.out.println("Enroll Id does not Exist.");
                    break;
                case 6:
                    back_option_enrolled = false;
                    break;

                default:
                    System.out.println("Invalid Choice!!!");
            }
        } while (back_option_enrolled);
    }
    public static void main(String[] args) {
        options();
    }
}