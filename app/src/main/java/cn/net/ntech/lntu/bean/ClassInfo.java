package cn.net.ntech.lntu.bean;

/**
 * Created by NTECHER on 2016/3/21.
 */
public class ClassInfo {
    private String classname;
    private String classteacher;
    private String classgrade;
    private String classweeknum;
    private String classweek;
    private String classtime;
    private String classplace;

    public ClassInfo(String classname, String classteacher, String classgrade, String classweeknum, String classweek, String classtime, String classplace) {
        this.classname = classname;
        this.classteacher = classteacher;
        this.classgrade = classgrade;
        this.classweeknum = classweeknum;
        this.classweek = classweek;
        this.classtime = classtime;
        this.classplace = classplace;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassteacher() {
        return classteacher;
    }

    public void setClassteacher(String classteacher) {
        this.classteacher = classteacher;
    }

    public String getClassgrade() {
        return classgrade;
    }

    public void setClassgrade(String classgrade) {
        this.classgrade = classgrade;
    }

    public String getClassweeknum() {
        return classweeknum;
    }

    public void setClassweeknum(String classweeknum) {
        this.classweeknum = classweeknum;
    }

    public String getClassweek() {
        return classweek;
    }

    public void setClassweek(String classweek) {
        this.classweek = classweek;
    }

    public String getClasstime() {
        return classtime;
    }

    public void setClasstime(String classtime) {
        this.classtime = classtime;
    }

    public String getClassplace() {
        return classplace;
    }

    public void setClassplace(String classplace) {
        this.classplace = classplace;
    }
}
