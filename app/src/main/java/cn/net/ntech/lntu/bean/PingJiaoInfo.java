package cn.net.ntech.lntu.bean;

/**
 * Created by Android-J on 2016/6/10.
 */
public class PingJiaoInfo {
    private String techerName;
    private String className;
    private String url;
    private String state;

    public PingJiaoInfo(String className, String state, String techerName, String url) {
        this.className = className;
        this.state = state;
        this.techerName = techerName;
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTecherName() {
        return techerName;
    }

    public void setTecherName(String techerName) {
        this.techerName = techerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
