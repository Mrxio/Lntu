package cn.net.ntech.lntu.bean;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GuaKeInfo {
    private String classNum;
    private String className;
    private String classGrade;
    private String classGPA;
    private String classType;
    private String classTime;

    public GuaKeInfo(String classNum, String className, String classGrade, String classGPA, String classType, String classTime) {
        this.classTime = classTime;
        this.classNum = classNum;
        this.className = className;
        this.classGrade = classGrade;
        this.classGPA = classGPA;
        this.classType = classType;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getClassGPA() {
        return classGPA;
    }

    public void setClassGPA(String classGPA) {
        this.classGPA = classGPA;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }
}
