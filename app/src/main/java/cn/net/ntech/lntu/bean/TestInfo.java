package cn.net.ntech.lntu.bean;

/**
 * Created by NTECHER on 2016/3/15.
 */
public class TestInfo {
    private String TestSubject;
    private String TestTime;
    private String TestPlace;

    public String getTestSubject() {
        return TestSubject;
    }

    public void setTestSubject(String testSubject) {
        TestSubject = testSubject;
    }

    public String getTestTime() {
        return TestTime;
    }

    public void setTestTime(String testTime) {
        TestTime = testTime;
    }

    public String getTestPlace() {
        return TestPlace;
    }

    public void setTestPlace(String testPlace) {
        TestPlace = testPlace;
    }

    public TestInfo(String testSubject, String testTime, String testPlace) {
        TestSubject = testSubject;
        TestTime = testTime;
        TestPlace = testPlace;
    }
}
