package cn.net.ntech.lntu.bean;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GongGaoInfo {
    private String title;
    private String url;
    private String time;

    public GongGaoInfo(String title, String url, String time) {
        this.title = title;
        this.url = url;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
