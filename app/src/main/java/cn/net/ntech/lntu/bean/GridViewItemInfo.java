package cn.net.ntech.lntu.bean;

/**
 * Created by Administrator on 2016/11/7.
 */

public class GridViewItemInfo {
    String text;
    int imgRes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public GridViewItemInfo(String text, int imgRes) {
        this.text = text;
        this.imgRes = imgRes;
    }
}
