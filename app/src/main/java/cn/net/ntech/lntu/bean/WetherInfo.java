package cn.net.ntech.lntu.bean;

/**
 * Created by Administrator on 2016/11/3.
 * 天气实体
 */

public class WetherInfo {
    private String wether;
    private String maxT;
    private String minT;

    public WetherInfo(String wether, String maxT, String minT) {
        this.wether = wether;
        this.maxT = maxT;
        this.minT = minT;
    }

    public String getWether() {
        return wether;
    }

    public void setWether(String wether) {
        this.wether = wether;
    }

    public String getMaxT() {
        return maxT;
    }

    public void setMaxT(String maxT) {
        this.maxT = maxT;
    }

    public String getMinT() {
        return minT;
    }

    public void setMinT(String minT) {
        this.minT = minT;
    }
}
