package Models;

public class Department {
    private int dptId;
    private String Name;
    private String DptCode;

    public int getDptId() {
        return dptId;
    }

    public void setDptId(int dptId) {
        this.dptId = dptId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDptCode() {
        return DptCode;
    }

    public void setDptCode(String dptCode) {
        DptCode = dptCode;
    }
}
