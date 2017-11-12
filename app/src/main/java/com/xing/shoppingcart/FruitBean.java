package com.xing.shoppingcart;

/**
 * Created by Administrator on 2017/11/12.
 */

public class FruitBean {

    private int resId;

    private String name;

    private String desc;

    public FruitBean() {
    }

    public FruitBean(int resId, String name, String desc) {
        this.resId = resId;
        this.name = name;
        this.desc = desc;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "FruitBean{" +
                "resId=" + resId +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
