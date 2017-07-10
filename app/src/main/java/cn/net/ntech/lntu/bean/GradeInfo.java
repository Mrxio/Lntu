package cn.net.ntech.lntu.bean;

/**
 * Created by NTECHER on 2016/3/12.
 */
public class GradeInfo {
    private String kechengbianhao;
    private String kechengmingcheng;
    private String fenshu;
    private String xuefen;
    private String leixing;
    private String kaoshileixing;
    private String shijian;
    private String url;

    public GradeInfo(String kechengbianhao, String kechengmingcheng, String fenshu, String xuefen, String leixing, String kaoshileixing, String shijian,String url) {
        this.kechengbianhao = kechengbianhao;
        this.kechengmingcheng = kechengmingcheng;
        this.fenshu = fenshu;
        this.xuefen = xuefen;
        this.leixing = leixing;
        this.kaoshileixing = kaoshileixing;
        this.shijian = shijian;
        this.url = url;
    }

    public String getKechengbianhao() {
        return kechengbianhao;
    }

    public void setKechengbianhao(String kechengbianhao) {
        this.kechengbianhao = kechengbianhao;
    }

    public String getKechengmingcheng() {
        return kechengmingcheng;
    }

    public void setKechengmingcheng(String kechengmingcheng) {
        this.kechengmingcheng = kechengmingcheng;
    }

    public String getFenshu() {
        return fenshu;
    }

    public void setFenshu(String fenshu) {
        this.fenshu = fenshu;
    }

    public String getXuefen() {
        return xuefen;
    }

    public void setXuefen(String xuefen) {
        this.xuefen = xuefen;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getKaoshileixing() {
        return kaoshileixing;
    }

    public void setKaoshileixing(String kaoshileixing) {
        this.kaoshileixing = kaoshileixing;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
